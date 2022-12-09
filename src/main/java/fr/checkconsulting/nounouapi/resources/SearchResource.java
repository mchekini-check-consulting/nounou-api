package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.dto.DisponibiliteDTO;
import fr.checkconsulting.nounouapi.dto.FamilleDTO;
import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.repository.DisponibiliteRepository;
import fr.checkconsulting.nounouapi.services.DisponibiliteService;
import fr.checkconsulting.nounouapi.services.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<NounouDTO> getNounouByCriteria(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom, @RequestParam("ville") String ville) {
        return searchService.getNounouByCriteria(nom, prenom, ville);
    }

    @GetMapping("dispo-nounou")
    public List<DisponibiliteDTO> getDispoNounou(@RequestParam("email") String email) {
        return disponibiliteService.getAllDisponibilitesByNounouId(email);
    }

    @GetMapping("famille")
    public List<FamilleDTO> getFamilleByCriteria(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom, @RequestParam("ville") String ville) {
        return searchService.getFamilleByCriteria(nom, prenom, ville);
    }
}
