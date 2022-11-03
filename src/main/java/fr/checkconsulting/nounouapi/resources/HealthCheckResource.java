package fr.checkconsulting.nounouapi.resources;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/nounou")
public class HealthCheckResource {



    @GetMapping("health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("application is UP updated");
    }
}
