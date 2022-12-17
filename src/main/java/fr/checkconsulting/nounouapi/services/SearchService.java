package fr.checkconsulting.nounouapi.services;

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

        if (jour != -1) {
            nounous.removeIf(nounou -> disponibilites.stream().noneMatch(disponibilite -> disponibilite.getNounouId().equals(nounou.getEmail())));
        }
        if (heureDebut != null && heureFin != null) {
            Map<String, List<List<LocalTime>>> mappedDisponibilites = mapDisponibilites(disponibilites);
            List<String> emails = filterDisponibilitesByTimeInterval(mappedDisponibilites, LocalTime.parse(heureDebut), LocalTime.parse(heureFin));
            nounous.removeIf(nounou -> !emails.contains(nounou.getEmail()));
        }

        List<NounouDTO> nounouDtos = new ArrayList<>();
        nounous.forEach(nounou -> {

            NounouDTO nounouDto = NounouDTO.builder()
                    .nom(nounou.getNom())
                    .prenom(nounou.getPrenom())
                    .rue(nounou.getRue())
                    .codePostal(nounou.getCodePostal())
                    .ville(nounou.getVille())
                    .email(nounou.getEmail())
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

    private List<String> filterDisponibilitesByTimeInterval(Map<String, List<List<LocalTime>>> disponibilites, LocalTime heureDebut, LocalTime heureFin) {
        List<String> emails = new ArrayList<>();
        for (Map.Entry<String, List<List<LocalTime>>> disponibilite : disponibilites.entrySet()) {
            boolean validTimeInterval = false;
            for (List<LocalTime> intervals : disponibilite.getValue()) {
                for (int i = 0; i < intervals.size(); i += 2) {
                    if (!intervals.get(i).isAfter(heureDebut) && !heureFin.isAfter(intervals.get(i + 1))) {
                        validTimeInterval = true;
                        break;
                    }
                }
                if (validTimeInterval) {
                    break;
                }
            }
            if (validTimeInterval) {
                emails.add(disponibilite.getKey());
            }
        }
        return emails;
    }

    private Map<String, List<List<LocalTime>>> mapDisponibilites(List<Disponibilite> disponibilites) {
        Map<String, List<List<LocalTime>>> mappingDisponibilites = new HashMap<>();
        for (Disponibilite disponibilite : disponibilites) {
            List<LocalTime> intervals = new ArrayList<>();
            if (disponibilite.getDateDebutMatin() != null) intervals.add(disponibilite.getDateDebutMatin());
            if (disponibilite.getDateFinMatin() != null) intervals.add(disponibilite.getDateFinMatin());
            if (disponibilite.getDateDebutMidi() != null) intervals.add(disponibilite.getDateDebutMidi());
            if (disponibilite.getDateFinMidi() != null) intervals.add(disponibilite.getDateFinMidi());
            if (disponibilite.getDateDebutSoir() != null) intervals.add(disponibilite.getDateDebutSoir());
            if (disponibilite.getDateFinSoir() != null) intervals.add(disponibilite.getDateFinSoir());
            if (intervals.size() == 6 && intervals.get(1).equals(intervals.get(2))) {
                intervals.remove(1);
                intervals.remove(1);
            }
            if (intervals.size() == 4 && intervals.get(1).equals(intervals.get(2))) {
                intervals.remove(1);
                intervals.remove(1);
            }
            List<List<LocalTime>> intervalsList = new ArrayList<>();
            if (mappingDisponibilites.containsKey(disponibilite.getNounouId())) {
                intervalsList = mappingDisponibilites.get(disponibilite.getNounouId());
            }
            intervalsList.add(intervals);
            mappingDisponibilites.put(disponibilite.getNounouId(), intervalsList);
        }
        return mappingDisponibilites;
    }
}