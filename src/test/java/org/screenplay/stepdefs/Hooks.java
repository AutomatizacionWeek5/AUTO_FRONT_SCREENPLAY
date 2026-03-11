package org.screenplay.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.actors.OnStage;
import org.screenplay.context.ScenarioContext;

/**
 * Hooks de Cucumber: configura y limpia el escenario de Screenplay.
 * OnlineCast asigna BrowseTheWeb al actor usando el driver gestionado por Serenity,
 * sin necesidad de inyectarlo manualmente (evita NPE por inicialización lazy).
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        ScenarioContext.reset();

        System.out.println("=================================================");
        System.out.println("Iniciando escenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("=================================================");
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("=================================================");
        System.out.println("Escenario finalizado: " + scenario.getName());
        System.out.println("Estado: " + (scenario.isFailed() ? "FALLIDO" : "EXITOSO"));
        System.out.println("=================================================");
        OnStage.drawTheCurtain();
    }
}
