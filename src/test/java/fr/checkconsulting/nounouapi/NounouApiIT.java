package fr.checkconsulting.nounouapi;

import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = NounouApiApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class NounouApiIT {


    @LocalServerPort
    Integer port;

    @Autowired
    NounouRepository nounouRepository;


    @Test
    void test() {
        Assertions.assertEquals("5", "5");
    }
}
