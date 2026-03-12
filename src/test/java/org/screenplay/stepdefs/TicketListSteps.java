package org.screenplay.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.assertj.core.api.Assertions;
import org.screenplay.context.ScenarioContext;
import org.screenplay.questions.CurrentUrl;
import org.screenplay.questions.IsElementVisible;
import org.screenplay.questions.IsPageLoadedAt;
import org.screenplay.questions.IsTicketInList;
import org.screenplay.questions.TicketDetailTitle;
import org.screenplay.tasks.tickets.OpenTicketDetail;
import org.screenplay.interactions.WaitForUrlContaining;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;


public class TicketListSteps {

    @Then("debería ser redirigido a la lista de tickets")
    public void deberiaSerRedirigidoALaListaDeTickets() {
        
        org.openqa.selenium.WebDriver driver = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(25))
                .until(d -> {
                    String url = d.getCurrentUrl();
                    return url != null && url.matches(".*\\/tickets\\/?$");
                });
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("[WARN] Timeout esperando URL exacta /tickets");
        }
        
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                    .presenceOfElementLocated(org.openqa.selenium.By.cssSelector(".navbar__username")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("[WARN] Navbar username no visible aún, continuando...");
        }
        String currentUrl = CurrentUrl.ofTheBrowser().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(currentUrl)
                .as("La URL debería ser la lista de tickets (/tickets exactamente)")
                .matches(".*\\/tickets\\/?$");
    }

    @Then("el ticket {string} debería aparecer en la lista")
    public void elTicketDeberiaAparecerEnLaLista(String ticketTitle) {
        Boolean inList = IsTicketInList.withTitle(ticketTitle).answeredBy(theActorInTheSpotlight());
        if (!inList) {
           
            System.out.println("[WARN] Ticket '" + ticketTitle + "' no encontrado en primera pasada. Recargando...");
            BrowseTheWeb.as(theActorInTheSpotlight()).getDriver().navigate().refresh();
            inList = IsTicketInList.withTitle(ticketTitle).answeredBy(theActorInTheSpotlight());
        }
        Assertions.assertThat(inList)
                .as("El ticket '" + ticketTitle + "' debería estar visible en la lista")
                .isTrue();
    }

    @When("el usuario hace click en el ticket {string}")
    public void elUsuarioHaceClickEnElTicket(String ticketTitle) {
        when(theActorInTheSpotlight()).attemptsTo(
            OpenTicketDetail.withTitle(ticketTitle)
        );
    }

    @Then("debería ver el detalle del ticket")
    public void deberiaVerElDetalleDelTicket() {
        then(theActorInTheSpotlight()).attemptsTo(
            WaitForUrlContaining.theFragment("/tickets/")
        );
        Boolean detailVisible = IsElementVisible.withSelector(".ticket-detail-title").answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(detailVisible)
                .as("Debería estar visible el detalle del ticket")
                .isTrue();
    }

    @Then("el título del detalle debería contener {string}")
    public void elTituloDelDetalleDeberiaContener(String expectedTitle) {
        String actualTitle = TicketDetailTitle.fromDetailPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(actualTitle)
                .as("El título del detalle debería contener '" + expectedTitle + "'")
                .contains(expectedTitle);
    }


    @Then("el ticket {string} aparece en su lista de solicitudes")
    public void elTicketApareceEnSuListaDeSolicitudes(String ticketTitle) {
        elTicketDeberiaAparecerEnLaLista(ticketTitle);
    }

    @Then("puede consultar el detalle del ticket {string}")
    public void puedeConsultarElDetalleDelTicket(String ticketTitle) {
        elUsuarioHaceClickEnElTicket(ticketTitle);
        deberiaVerElDetalleDelTicket();
        elTituloDelDetalleDeberiaContener(ticketTitle);
    }
}
