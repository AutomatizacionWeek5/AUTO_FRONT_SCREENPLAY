package org.screenplay.tasks.tickets;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.annotations.Step;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.ui.CreateTicketUi;


public class FillTicketForm implements Task {

    private final String title;
    private final String description;

    public FillTicketForm(String title, String description) {
        this.title       = title;
        this.description = description;
    }

    public static FillTicketForm with(String title, String description) {
        return new FillTicketForm(title, description);
    }

    @Override
    @Step("{0} completa el formulario de ticket con título '#title'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Enter.theValue(title).into(CreateTicketUi.TITLE_FIELD),
            DemoDelay.forConfiguredTime(),
            Enter.theValue(description).into(CreateTicketUi.DESCRIPTION_FIELD),
            DemoDelay.forConfiguredTime()
        );
    }

    @Override
    public String toString() {
        return "completar formulario de ticket con título '" + title + "'";
    }
}
