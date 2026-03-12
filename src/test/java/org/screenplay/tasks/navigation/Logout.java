package org.screenplay.tasks.navigation;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.interactions.WaitForUrlContaining;
import org.screenplay.ui.NavBarUi;

public class Logout implements Task {

    public Logout() {
    }

    public static Logout fromTheApplication() {
        return new Logout();
    }

    @Override
    @Step("{0} cierra sesión en la aplicación")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(NavBarUi.LOGOUT_BUTTON),
            WaitForUrlContaining.theFragment("/login"),
            DemoDelay.forConfiguredTime()
        );
    }

    @Override
    public String toString() {
        return "cerrar sesión";
    }
}
