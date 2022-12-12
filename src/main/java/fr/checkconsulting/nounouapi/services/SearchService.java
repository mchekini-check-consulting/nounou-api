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

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SearchService {

    @Value("${famille.application.url}")
    String familleUrl;

    private final NounouRepository nounouRepository;
    private final RestTemplate restTemplate;
    private final DisponibiliteRepository disponibiliteRepository;


    public SearchService(NounouRepository nounouRepository, RestTemplate restTemplate, DisponibiliteRepository disponibiliteRepository) {
        this.nounouRepository = nounouRepository;
        this.restTemplate = restTemplate;

        this.disponibiliteRepository = disponibiliteRepository;
    }

    public List<NounouDTO> getNounouByCriteria(String nom, String prenom, String ville, String debut, String fin, int jour) {
        int d1 = 0;
        int f1 = 0;
        NounouDTO nounouFilter = new NounouDTO();

        if ("".equals(nom)) nom = null;
        if ("".equals(prenom)) prenom = null;
        if ("".equals(ville)) ville = null;
        if ("".equals(debut)) {
            debut = null;
        } else {
            d1 = LocalTime.parse(debut).getHour();
        }
        if ("".equals(fin)) {
            fin = null;
        } else {
            f1 = LocalTime.parse(fin).getHour();
        }

        Integer heureDebutMatin = null;
        Integer heureFinMatin = null;
        Integer heureDebutmidi = null;
        Integer heureFinMidi = null;
        Integer heureDebutSoir = null;
        Integer heureFinSoir = null;


        Integer[] interval_recherche = new Integer[24];
        interval_recherche = Collections.nCopies(24, 0).toArray(new Integer[0]);
        for (int indice = d1; indice <= f1; indice++) {
            interval_recherche[indice] = indice;
        }
        List<Nounou> nounous = nounouRepository.getNounousByCriteria(nom, prenom, ville, jour);
        List<Nounou> nounouList = nounouRepository.getNounousByCriteria(nom, prenom, ville, jour);
        List<List<Disponibilite>> disponibilites = new ArrayList<>();
        Integer[] periode = new Integer[24];
        periode = Collections.nCopies(24, 0).toArray(new Integer[0]);
        List<NounouDTO> nounouDtos = new ArrayList<>();
        List<NounouDTO> nounouDtosFilter = new ArrayList<>();
        List<NounouDTO> nounouResponse = new ArrayList<>();
        if (debut != null && fin != null) {
            for (Nounou nounou : nounous) {
                List<Disponibilite> disponibilitelist = null;
                disponibilitelist = disponibiliteRepository.findAllByNounouId(nounou);
                for (Disponibilite disponibilite : disponibilitelist) {
                    if (disponibilite.getJour() == jour) {
                        if (disponibilite.getDate_debut_matin() != null) {
                            heureDebutMatin = disponibilite.getDate_debut_matin().getHour();
                            heureFinMatin = disponibilite.getDate_fin_matin().getHour();
                            for (int k = heureDebutMatin; k <= heureFinMatin; k++) {
                                periode[k] = k;
                            }
                        }
                        if (disponibilite.getDate_debut_midi() != null) {
                            heureDebutmidi = disponibilite.getDate_debut_midi().getHour();
                            heureFinMidi = disponibilite.getDate_fin_midi().getHour();
                            for (int k = heureDebutmidi; k <= heureFinMidi; k++) {
                                periode[k] = k;
                            }
                        }
                        if (disponibilite.getDate_debut_soir() != null) {
                            heureDebutSoir = disponibilite.getDate_debut_soir().getHour();
                            heureFinSoir = disponibilite.getDate_fin_soir().getHour();
                            for (int k = heureDebutSoir; k <= heureFinSoir; k++) {
                                periode[k] = k;
                            }
                        }
                    }
                }
                if (new HashSet<>(Arrays.asList(periode)).containsAll(Arrays.asList(interval_recherche))) {

                    nounouFilter = NounouDTO.builder()
                            .nom(nounou.getNom())
                            .prenom(nounou.getPrenom())
                            .adresse(String.join(" ", nounou.getRue(), nounou.getCodePostal(), nounou.getVille()))
                            .mail(nounou.getEmail())
                            .telephone(nounou.getNumeroTelephone())
                            .build();

                    nounouDtosFilter.add(nounouFilter);
                }
                periode = Collections.nCopies(24, 0).toArray(new Integer[0]);
            }
        }
        if (debut != null && fin != null) {
            nounouResponse = nounouDtosFilter;
        } else {
            nounouList.forEach(nounou -> {

                NounouDTO nounouDto = NounouDTO.builder()
                        .nom(nounou.getNom())
                        .prenom(nounou.getPrenom())
                        .adresse(String.join(" ", nounou.getRue(), nounou.getCodePostal(), nounou.getVille()))
                        .mail(nounou.getEmail())
                        .telephone(nounou.getNumeroTelephone())
                        .build();

                nounouDtos.add(nounouDto);
            });


            nounouResponse = nounouDtos;

        }
        return nounouResponse;
    }

    public List<FamilleDTO> getFamilleByCriteria(String nom, String prenom, String ville) {
        log.info("Résultat *********** : " + nom + " -- " + prenom + " -- " + ville);
        String url = familleUrl + "/api/v1/search/famille?nom=" + nom + "&prenom=" + prenom + "&ville=" + ville;
        ResponseEntity<FamilleDTO[]> familles = restTemplate.getForEntity(url, FamilleDTO[].class);

        log.info("Résultat *********** : " + familles);

        return Arrays.stream(familles.getBody()).collect(Collectors.toList());

    }
}