package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.dto.DisponibiliteDTO;
import fr.checkconsulting.nounouapi.dto.FamilleDTO;
import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.services.DisponibiliteService;
import fr.checkconsulting.nounouapi.services.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
public class SearchResource {

    private final SearchService searchService;
    private final DisponibiliteService disponibiliteService;

    public SearchResource(SearchService searchService, DisponibiliteService disponibiliteService) {
        this.searchService = searchService;
        this.disponibiliteService = disponibiliteService;
    }


    @GetMapping("nounou")
    public List<NounouDTO> getNounouByCriteria(@RequestParam("nom") String nom,
                                               @RequestParam("prenom") String prenom,
                                               @RequestParam("ville") String ville,
                                               @RequestParam("jour") int jour,
                                               @RequestParam("heureDebut") String heureDebut,
                                               @RequestParam("heureFin") String heureFin) {
        return searchService.getNounouByCriteria(nom, prenom, ville, jour, heureDebut, heureFin);
    }

    @GetMapping("dispo-nounou/{email}")
    public ResponseEntity<List<DisponibiliteDTO>> getDispoNounou(@PathVariable("email") String email) {
        return ResponseEntity.ok(disponibiliteService.getAllDisponibilitesByNounouId(email));
    }

    @GetMapping("famille")
    public List<FamilleDTO> getFamilleByCriteria(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("ville") String ville,
            @RequestParam("jour") int jour,
            @RequestParam("heureDebut") String heureDebut,
            @RequestParam("heureFin") String heureFin
    ) {
        return searchService.getFamilleByCriteria(nom, prenom, ville, jour, heureDebut, heureFin);
    }
}
