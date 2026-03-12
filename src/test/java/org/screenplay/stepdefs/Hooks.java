package org.screenplay.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.actors.OnStage;
import org.screenplay.context.ScenarioContext;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        ScenarioContext.reset();
    }

    @After
    public void tearDown(Scenario scenario) {
        OnStage.drawTheCurtain();
    }
}
