package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.services.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/disponibilites")
public class DisponibiliteResource {
    private final DisponibiliteService disponibiliteService;

    @Autowired
    public DisponibiliteResource(DisponibiliteService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }
}
