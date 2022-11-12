package fr.checkconsulting.nounouapi;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class AdditionTest {
    private int a;
    private int b;
    private int resultat;

    @Given("j'ai un entier {int} en entrée et un entier {int} en entrée")
    public void j_ai_un_entier_en_entrée_et_un_entier_en_entrée(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    @When("j'addtitionne les deux nombres en entrée")
    public void j_addtitionne_les_deux_nombres_en_entrée() {
        this.resultat = a + b;
    }

    @Then("le résultat est égal à {int}")
    public void le_résultat_est_égal_à(Integer resultat) {
        Assertions.assertEquals(resultat, this.resultat);
    }
}
