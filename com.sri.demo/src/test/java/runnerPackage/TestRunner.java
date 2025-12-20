package runnerPackage;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
	features = "src/test/resources/features",
	glue = {"stepdef"},
	plugin = {"pretty","html:target/cucumber-reports","json:target/cucumber.json"},
	monochrome = true,
	publish = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
}