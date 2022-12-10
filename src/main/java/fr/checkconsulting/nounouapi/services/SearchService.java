package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.FamilleDTO;
import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {

    @Value("${famille.application.url}")
    String familleUrl;

    private final NounouRepository nounouRepository;
    private final RestTemplate restTemplate;

    public SearchService(NounouRepository nounouRepository, RestTemplate restTemplate) {
        this.nounouRepository = nounouRepository;
        this.restTemplate = restTemplate;
    }

    public List<NounouDTO> getNounouByCriteria(String nom, String prenom, String ville, String debut, String fin, int jour) {

        if ("".equals(nom)) nom = null;
        if ("".equals(prenom)) prenom = null;
        if ("".equals(ville)) ville = null;
        if ("-1".equals(debut)) debut = null;
        if ("-1".equals(fin)) fin = null;

        Integer heureDebut = null;
        Integer heureFin = null;
        String d1 = debut;
        String d2 = fin;

        if (d1 != null) {
            heureDebut = Integer.parseInt(debut);
        }
        if (d2 != null) {
            heureFin = Integer.parseInt(fin);
        }

        List<Nounou> nounous = nounouRepository.getNounousByCriteria(nom, prenom, ville, heureDebut, heureFin, jour);

        List<NounouDTO> nounouDtos = new ArrayList<>();
        nounous.forEach(nounou -> {

            NounouDTO nounouDto = NounouDTO.builder()
                    .nom(nounou.getNom())
                    .prenom(nounou.getPrenom())
                    .adresse(String.join(" ", nounou.getRue(), nounou.getCodePostal(), nounou.getVille()))
                    .mail(nounou.getEmail())
                    .telephone(nounou.getNumeroTelephone())
                    .build();

            nounouDtos.add(nounouDto);
        });


        return nounouDtos;

    }

    public List<FamilleDTO> getFamilleByCriteria(String nom, String prenom, String ville) {
        log.info("Résultat *********** : " + nom + " -- " + prenom + " -- " + ville);
        String url = familleUrl + "/api/v1/search/famille?nom=" + nom + "&prenom=" + prenom + "&ville=" + ville;
        ResponseEntity<FamilleDTO[]> familles = restTemplate.getForEntity(url, FamilleDTO[].class);

        log.info("Résultat *********** : " + familles);

        return Arrays.stream(familles.getBody()).collect(Collectors.toList());

    }
}