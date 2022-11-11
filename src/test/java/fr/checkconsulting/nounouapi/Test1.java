package fr.checkconsulting.nounouapi;

import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@CucumberContextConfiguration
@SpringBootTest(classes = NounouApiApplication.class, webEnvironment = RANDOM_PORT)
@Slf4j
@ActiveProfiles("test")
public class Test1 {


    @LocalServerPort
    Integer port;


    @Autowired
    NounouRepository nounouRepository;


    private int a;
    private int b;
    private int resultat;

    @Given("J'ai un nombre {int} et un nombre {int}")
    public void j_ai_un_nombre_et_un_nombre(Integer int1, Integer int2) {
        Nounou nounou = new Nounou();
        nounou.setEmail("test");
        nounouRepository.save(nounou);
        resultat = int1 + int2;
        log.info("mahdi");

    }

    @When("j'additionne les deux nombres a et b")
    public void j_additionne_les_deux_nombres_a_et_b() {

    }

    @Then("le resultat attendu est {int}")
    public void le_resultat_attendu_est(Integer int1) {
        Assertions.assertEquals(int1, resultat);
    }


}
