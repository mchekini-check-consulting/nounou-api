package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.services.NounouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/nounou")
public class NounouResource {


    private final NounouService nounouService;

    @Autowired
    public NounouResource(NounouService nounouService) {
        this.nounouService = nounouService;
    }


    @GetMapping("get-all")
    public List<Nounou> getAllNounous() {
        return nounouService.getAllNounous();
    }

    @GetMapping("getById/{nounou-email}")
    public Nounou getNounouById(@PathVariable("nounou-email") String email) {
        return nounouService.getNounouByEmail(email);
    }

    @PutMapping("update")
    public void updateNounou(@RequestBody Nounou nounou) {
        nounouService.updateNounou(nounou);
    }
}
