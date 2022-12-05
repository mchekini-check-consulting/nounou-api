package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.dto.DisponibiliteDTO;
import fr.checkconsulting.nounouapi.services.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/disponibilites")
public class DisponibiliteResource {
    private final DisponibiliteService disponibiliteService;

    @Autowired
    public DisponibiliteResource(DisponibiliteService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }

    @GetMapping("{nounouId}")
    public ResponseEntity<List<DisponibiliteDTO>> getAllDisponibilitesByNounouId(@PathVariable("nounouId") String nounouId) {
        return ResponseEntity.ok(disponibiliteService.getAllDisponibilitesByNounouId(nounouId));
    }

    @PostMapping
    public ResponseEntity<List<DisponibiliteDTO>> createNounouDisponibilites(@RequestBody List<DisponibiliteDTO> listDisponibiliteDTO) {
        return ResponseEntity.ok(disponibiliteService.createNounouDisponibilites(listDisponibiliteDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDisponibilitesByNounouId() {
        disponibiliteService.deleteDisponibilitesByNounouId();
        return ResponseEntity.noContent().build();
    }
}
