package fr.checkconsulting.nounouapi;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = NounouApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UpdateNounouTest {
    @LocalServerPort
    private String port;
    @Autowired
    NounouRepository nounouRepository;
    @Autowired
    TestRestTemplate testRestTemplate;
    private String updateNounouUrl;
    private String email;
    private NounouDTO nounouDTO;

    @Given("Une nounou avec le nom={string}, le prenom={string}, email={string} persistée dans la base de données")
    public void une_nounou_avec_le_nom_le_prenom_email_persistée_dans_la_base_de_données(String lastname, String firstname, String email) {
        this.email = email;
        Nounou nounou = Nounou.builder()
                .email(email)
                .nom(lastname)
                .prenom(firstname)
                .build();
        nounouRepository.save(nounou);
    }

    @When("j'appelle l'api de modification {string} d'une nounou")
    public void j_appelle_l_api_de_modification_d_une_nounou(String updateNounouUrl) {
        this.updateNounouUrl = updateNounouUrl;
    }

    @When("avec les paramètres: nom={string}, prenom={string} et email={string}")
    public void avec_les_paramètres_nom_prenom_et_email(String newLastname, String newFirstname, String newEmail) {
        NounouDTO body = NounouDTO.builder()
                .nom(newLastname)
                .prenom(newFirstname)
                .mail(newEmail)
                .build();
        testRestTemplate.put("http://localhost:" + port + updateNounouUrl + email, body);
    }

    @When("et j'appelle l'api de consultation d'une nounou {string} avec l'email {string}")
    public void et_j_appelle_l_api_de_consultation_d_une_nounou_avec_l_email(String string, String string2) {
        this.nounouDTO = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/nounous/" + email, NounouDTO.class);
    }

    @Then("je dois vérifier que nom={string}, prenom={string} et email={string}")
    public void je_dois_vérifier_que_nom_prenom_et_email(String newLastname, String newFirstname, String email) {
        Assertions.assertEquals(newLastname, nounouDTO.getNom());
        Assertions.assertEquals(newFirstname, nounouDTO.getPrenom());
        Assertions.assertEquals(email, nounouDTO.getMail());
    }
}
