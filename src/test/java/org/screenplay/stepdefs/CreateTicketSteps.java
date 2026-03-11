package org.screenplay.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.screenplay.context.ScenarioContext;
import org.screenplay.tasks.tickets.FillTicketForm;
import org.screenplay.tasks.tickets.SubmitTicketForm;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step Definitions para la creación de tickets.
 */
public class CreateTicketSteps {

    @When("completa el formulario de ticket con título {string} y descripción {string}")
    public void completaElFormularioDeTicket(String title, String description) {
        ScenarioContext.get().setTicketTitle(title);
        ScenarioContext.get().setTicketDescription(description);

        when(theActorInTheSpotlight()).attemptsTo(
            FillTicketForm.with(title, description)
        );
    }

    @When("envía el formulario del ticket")
    public void enviaElFormularioDelTicket() {
        when(theActorInTheSpotlight()).attemptsTo(
            SubmitTicketForm.now()
        );
    }
}
