package fr.checkconsulting.nounouapi.services;

import com.google.common.collect.Iterables;
import fr.checkconsulting.nounouapi.dto.FamilleDTO;
import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Disponibilite;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.DisponibiliteRepository;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {

    @Value("${famille.application.url}")
    String familleUrl;

    private final NounouRepository nounouRepository;
    private final DisponibiliteRepository disponibiliteRepository;
    private final RestTemplate restTemplate;

    public SearchService(NounouRepository nounouRepository, DisponibiliteRepository disponibiliteRepository, RestTemplate restTemplate) {
        this.nounouRepository = nounouRepository;
        this.disponibiliteRepository = disponibiliteRepository;
        this.restTemplate = restTemplate;
    }

    public List<NounouDTO> getNounouByCriteria(String nom, String prenom, String ville, int jour, String heureDebut, String heureFin) {

        if ("".equals(nom)) nom = null;
        if ("".equals(prenom)) prenom = null;
        if ("".equals(ville)) ville = null;
        if ("".equals(heureDebut)) heureDebut = null;
        if ("".equals(heureFin)) heureFin = null;

        List<Nounou> nounous = nounouRepository.getNounousByCriteria(nom, prenom, ville);
        List<Disponibilite> disponibilites = disponibiliteRepository.findAllByJour(jour);
        if (disponibilites.isEmpty()) {
            Iterables.removeAll(nounous, nounous);
        }
        else {
            Map<String, List<LocalTime>> mappedDisponibilites = mapDisponibilites(disponibilites);
            if (heureDebut != null && heureFin != null) {
                List<String> emails = filterDisponibilitesByTimeInterval(mappedDisponibilites, LocalTime.parse(heureDebut), LocalTime.parse(heureFin));
                nounous.removeIf(nounou -> !emails.contains(nounou.getEmail()));
            } else {
                nounous.removeIf(nounou -> disponibilites.stream().noneMatch(disponibilite -> disponibilite.getNounouId().equals(nounou.getEmail())));
            }
        }

        List<NounouDTO> nounouDtos = new ArrayList<>();
        nounous.forEach(nounou -> {

            NounouDTO nounouDto = NounouDTO.builder()
                    .nom(nounou.getNom())
                    .prenom(nounou.getPrenom())
                    .rue(nounou.getRue())
                    .codePostal(nounou.getCodePostal())
                    .ville(nounou.getVille())
                    .mail(nounou.getEmail())
                    .numeroTelephone(nounou.getNumeroTelephone())
                    .build();

            nounouDtos.add(nounouDto);
        });


        return nounouDtos;

    }

    public List<FamilleDTO> getFamilleByCriteria(String nom, String prenom, String ville, int jour, String heureDebut, String heureFin) {
        log.info("Résultat *********** : " + nom + " -- " + prenom + " -- " + ville);
        String url = familleUrl + "/api/v1/search/famille?nom=" + nom + "&prenom=" + prenom + "&ville=" + ville + "&jour=" + jour + "&heureDebut=" + heureDebut + "&heureFin=" + heureFin;
        ResponseEntity<FamilleDTO[]> familles = restTemplate.getForEntity(url, FamilleDTO[].class);

        log.info("Résultat *********** : " + familles);

        return Arrays.stream(familles.getBody()).collect(Collectors.toList());

    }


    private List<String> filterDisponibilitesByTimeInterval(Map<String, List<LocalTime>> disponibilites, LocalTime heureDebut, LocalTime heureFin) {
        List<String> emails = new ArrayList<>();
        for (Map.Entry<String, List<LocalTime>> dispos : disponibilites.entrySet()) {
            boolean validTimeInterval = false;
            List<LocalTime> intervals = dispos.getValue();
            for (int i = 0; i < intervals.size(); i += 2) {
                if (!intervals.get(i).isAfter(heureDebut) && !heureFin.isAfter(intervals.get(i + 1))) {
                    validTimeInterval = true;
                    break;
                }
            }
            if (validTimeInterval) {
                emails.add(dispos.getKey());
            }
        }
        return emails;
    }

    private HashMap<String, List<LocalTime>> mapDisponibilites(List<Disponibilite> disponibilites) {
        HashMap<String, List<LocalTime>> transformedBesoins = new HashMap<>();
        for (Disponibilite disponibilite : disponibilites) {
            List<LocalTime> timeList = new ArrayList<>();
            if (disponibilite.getDateDebutMatin() != null) timeList.add(disponibilite.getDateDebutMatin());
            if (disponibilite.getDateFinMatin() != null) timeList.add(disponibilite.getDateFinMatin());
            if (disponibilite.getDateDebutMidi() != null) timeList.add(disponibilite.getDateDebutMidi());
            if (disponibilite.getDateFinMidi() != null) timeList.add(disponibilite.getDateFinMidi());
            if (disponibilite.getDateDebutSoir() != null) timeList.add(disponibilite.getDateDebutSoir());
            if (disponibilite.getDateFinSoir() != null) timeList.add(disponibilite.getDateFinSoir());
            if (timeList.size() == 6 && timeList.get(1).equals(timeList.get(2))) {
                timeList.remove(1);
                timeList.remove(1);
            }
            if (timeList.size() == 4 && timeList.get(1).equals(timeList.get(2))) {
                timeList.remove(1);
                timeList.remove(1);
            }
            transformedBesoins.put(disponibilite.getNounouId(), timeList);
        }
        return transformedBesoins;
    }

    private Disponibilite buildDisponibiliteCriteria(String jour, String heureDebut, String heureFin) {
        Disponibilite besoins = new Disponibilite();
        if ("".equals(jour)) {
            besoins.setJour(-1);
        } else {
            try {
                besoins.setJour(Integer.parseInt(jour));
            } catch (NumberFormatException e) {
                besoins.setJour(-1);
            }
        }

        if (!"".equals(heureDebut) && !"".equals(heureFin)) {
            LocalTime parsedHeureDebut = LocalTime.parse(heureDebut);
            LocalTime parsedHeureFin = LocalTime.parse(heureFin);
            LocalTime matin = LocalTime.parse("07:00:00");
            LocalTime midi = LocalTime.parse("12:00:00");
            LocalTime aprem = LocalTime.parse("16:00:00");
            LocalTime soir = LocalTime.parse("21:00:00");
            LocalTime minuit = LocalTime.parse("00:00:00");

            if (parsedHeureDebut.isBefore(matin)
                    || parsedHeureDebut.isAfter(soir)
                    || parsedHeureFin.isBefore(matin)
                    || parsedHeureFin.isAfter(soir)
            ) {
                besoins.setDateDebutMatin(minuit);
                besoins.setDateFinMatin(minuit);
                besoins.setDateDebutMidi(minuit);
                besoins.setDateFinMidi(minuit);
                besoins.setDateDebutSoir(minuit);
                besoins.setDateFinSoir(minuit);
            }
            if (!matin.isAfter(parsedHeureDebut) && parsedHeureDebut.isBefore(midi)) {
                besoins.setDateDebutMatin(parsedHeureDebut);
                if (!parsedHeureFin.isAfter(midi)) {
                    besoins.setDateFinMatin(parsedHeureFin);
                }
                if (midi.isBefore(parsedHeureFin) && !parsedHeureFin.isAfter(aprem)) {
                    besoins.setDateFinMatin(midi);
                    besoins.setDateDebutMidi(midi);
                    besoins.setDateFinMidi(parsedHeureFin);
                }
                if (aprem.isBefore(parsedHeureFin) && !parsedHeureFin.isAfter(soir)) {
                    besoins.setDateFinMatin(midi);
                    besoins.setDateDebutMidi(midi);
                    besoins.setDateFinMidi(aprem);
                    besoins.setDateDebutSoir(aprem);
                    besoins.setDateFinSoir(parsedHeureFin);
                }
            }
            if (!midi.isAfter(parsedHeureDebut) && parsedHeureDebut.isBefore(aprem)) {
                besoins.setDateDebutMidi(parsedHeureDebut);
                if (!parsedHeureFin.isAfter(aprem)) {
                    besoins.setDateFinMidi(parsedHeureFin);
                }
                if (aprem.isBefore(parsedHeureFin) && !parsedHeureFin.isAfter(soir)) {
                    besoins.setDateFinMidi(aprem);
                    besoins.setDateDebutSoir(aprem);
                    besoins.setDateFinSoir(parsedHeureFin);
                }
            }
            if (!aprem.isAfter(parsedHeureDebut) && parsedHeureDebut.isBefore(soir)) {
                besoins.setDateDebutSoir(parsedHeureDebut);
                if (!parsedHeureFin.isAfter(soir)) {
                    besoins.setDateFinSoir(parsedHeureFin);
                }
            }
        }

        return besoins;
    }
}