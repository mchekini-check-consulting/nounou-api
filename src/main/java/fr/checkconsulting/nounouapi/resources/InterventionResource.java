package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.dto.InterventionDTO;
import fr.checkconsulting.nounouapi.security.CommonData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
class Infos {
    public String jour;
    public String matin;
    public String midi;
    public String soir;
}

@Data
class InfosInt {
    public String nom;
    public String prenom;
    public String emailNounou;
    public String periode;
    public String etat;
    public String dateIntervention;
    List<Infos> detailIntervention;
}

@RestController
@RequestMapping("api/v1/intervention")
public class InterventionResource {
    private static final Logger LOG = LoggerFactory.getLogger(InterventionDTO.class);
    private final RestTemplate restTemplate;
    @Value("${famille.application.url}")
    String familleUrl;

    public InterventionResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/get-all-interventions")
    public List<InfosInt> getAllInterventionsNounou() {
        //String email = CommonData.getEmail();
        String email = "essaid.brahiti@gmail.com";
        String url = familleUrl + "/api/v1/intervention/get-all-interventions?emailNounou=" + email;
        ResponseEntity<InfosInt[]> res = restTemplate.getForEntity(url, InfosInt[].class);
        return Arrays.stream(res.getBody()).collect(Collectors.toList());
    }

    @PutMapping("/reject")
    public List<InfosInt> rejectInterventionsNounou(@RequestBody InterventionDTO intervention) {
        //String email = CommonData.getEmail();
        LOG.info("Intervention = {}", intervention.getEmailFamille());
        String email = "essaid.brahiti@gmail.com";
        String url = familleUrl + "/api/v1/intervention/reject?emailFamille=" + intervention.getEmailFamille() + "&emailNounou=" + email;
        ResponseEntity<InfosInt[]> res = restTemplate.getForEntity(url, InfosInt[].class);
        return Arrays.stream(res.getBody()).collect(Collectors.toList());
    }

    @PutMapping("/confirm")
    public List<InfosInt> confirmInterventionsNounou(@RequestBody InterventionDTO intervention) {
        //String email = CommonData.getEmail();
        LOG.info("Intervention = {}", intervention.getEmailFamille());
        String email = "essaid.brahiti@gmail.com";
        String url = familleUrl + "/api/v1/intervention/confirm?emailFamille=" + intervention.getEmailFamille() + "&emailNounou=" + email;
        ResponseEntity<InfosInt[]> res = restTemplate.getForEntity(url, InfosInt[].class);
        return Arrays.stream(res.getBody()).collect(Collectors.toList());
    }
}
