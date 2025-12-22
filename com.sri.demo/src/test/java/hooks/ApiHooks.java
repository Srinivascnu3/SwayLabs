package hooks;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApiHooks extends BaseTest {

    @Before
    public void setUp() {
        initRequest();
    }

    @After
    public void afterScenario(Scenario scenario)  {
        if (scenario.isFailed()) {
        	scenario.log("Scenario failed. Check API logs above.");

        }
        clear();
    }
}
