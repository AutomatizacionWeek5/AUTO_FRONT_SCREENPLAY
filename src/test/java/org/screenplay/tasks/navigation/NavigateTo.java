package org.screenplay.tasks.navigation;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.interactions.WaitForUrlContaining;
import org.screenplay.ui.NavBarUi;

/**
 * Tarea: navegar a una sección de la aplicación usando la barra de navegación.
 * Responsabilidad única: hacer clic en el enlace de navbar correspondiente.
 */
public class NavigateTo implements Task {

    private final String destination;

    public NavigateTo(String destination) {
        this.destination = destination;
    }

    public static NavigateTo theSection(String destination) {
        return new NavigateTo(destination);
    }

    @Override
    @Step("{0} navega a la sección '#destination'")
    public <T extends Actor> void performAs(T actor) {
        switch (destination) {
            case "Crear Ticket":
                actor.attemptsTo(
                    Click.on(NavBarUi.CREATE_TICKET_LINK),
                    WaitForUrlContaining.theFragment("/tickets/new"),
                    DemoDelay.forConfiguredTime()
                );
                break;
            case "Tickets":
                actor.attemptsTo(
                    Click.on(NavBarUi.TICKETS_LINK),
                    WaitForUrlContaining.theFragment("/tickets"),
                    DemoDelay.forConfiguredTime()
                );
                break;
            case "Asignaciones":
                actor.attemptsTo(
                    Click.on(NavBarUi.ASSIGNMENTS_LINK),
                    WaitForUrlContaining.theFragment("/assignments"),
                    DemoDelay.forConfiguredTime()
                );
                break;
            case "Notificaciones":
                actor.attemptsTo(
                    Click.on(NavBarUi.NOTIFICATIONS_LINK),
                    WaitForUrlContaining.theFragment("/notifications"),
                    DemoDelay.forConfiguredTime()
                );
                break;
            default:
                throw new IllegalArgumentException("Destino de navegación no reconocido: " + destination);
        }
    }

    @Override
    public String toString() {
        return "navegar a " + destination;
    }
}
