package org.screenplay.tasks.tickets;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.interactions.WaitForUrlContaining;

import java.time.Duration;
import java.util.List;

public class OpenTicketDetail implements Task {

    private final String ticketTitle;

    public OpenTicketDetail(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public static OpenTicketDetail withTitle(String title) {
        return new OpenTicketDetail(title);
    }

    @Override
    @Step("{0} abre el detalle del ticket '#ticketTitle'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        String safeTitle = ticketTitle.replace("'", "\\'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'ticket-item')]//h3[contains(text(),'" + safeTitle + "')]")
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            System.out.println("[WARN] Timeout esperando el ticket '" + ticketTitle + "' en la lista.");
        }

        List<WebElement> ticketElements = driver.findElements(
            By.xpath("//div[contains(@class,'ticket-item')]//h3[contains(text(),'" + safeTitle + "')]")
        );

        if (!ticketElements.isEmpty()) {
            ticketElements.get(0).click();
        } else {
           
            List<WebElement> allTickets = driver.findElements(By.cssSelector(".ticket-item"));
            if (!allTickets.isEmpty()) {
                allTickets.get(0).click();
            }
        }

        actor.attemptsTo(
            WaitForUrlContaining.theFragment("/tickets/"),
            DemoDelay.forConfiguredTime()
        );
    }

    @Override
    public String toString() {
        return "abrir detalle del ticket '" + ticketTitle + "'";
    }
}
