package fr.checkconsulting.nounouapi;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/reports/cucumber.html",
                "json:target/reports/cucumber.json",
                "junit:target/reports/cucumber.xml"},
        features = "src/test/resources/features")
public class CucumberIntegrationTest {
}
