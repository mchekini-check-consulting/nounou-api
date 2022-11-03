package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.services.NounouService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/nounous")
public class NounouResource {


    @Autowired
    private ModelMapper modelMapper;

    private final NounouService nounouService;

    @Autowired
    public NounouResource(NounouService nounouService) {
        this.nounouService = nounouService;
    }


    @GetMapping
    public ResponseEntity<List<NounouDTO>> getAllNounous() {
        return ResponseEntity.ok(nounouService.getAllNounous());
    }


    @GetMapping("{email}")
    public ResponseEntity<NounouDTO> getNounouById(@PathVariable("email") String email) {
        return nounouService
                .getNounouByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


    @PutMapping("/{email}")
    public ResponseEntity<NounouDTO> updateNounou(@PathVariable String email, @RequestBody NounouDTO nounouDTO) {
        Nounou nounou = modelMapper.map(nounouDTO, Nounou.class);
        return nounouService.updateNounou(email, nounou)
                .map(temp -> modelMapper.map(temp, NounouDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
