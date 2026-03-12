package org.screenplay.tasks.navigation;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.annotations.Step;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.utils.config.TestConfig;


public class OpenApplication implements Task {

    public OpenApplication() {
    }

    public static OpenApplication inTheBrowser() {
        return new OpenApplication();
    }

    @Override
    @Step("{0} abre la aplicación del Sistema de Tickets")
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).getDriver().get(TestConfig.BASE_URL);
        actor.attemptsTo(DemoDelay.forConfiguredTime());
    }

    @Override
    public String toString() {
        return "abrir la aplicación en " + TestConfig.BASE_URL;
    }
}
