package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Pregunta: ¿está el ticket con el título dado en la lista de tickets?
 * Usa WebDriverWait para esperar a que los tickets carguen desde la API.
 */
public class IsTicketInList implements Question<Boolean> {

    private final String ticketTitle;

    private IsTicketInList(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public static IsTicketInList withTitle(String title) {
        return new IsTicketInList(title);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(20))
                .until(d -> {
                    java.util.List<org.openqa.selenium.WebElement> items =
                        d.findElements(By.cssSelector(".ticket-item"));
                    for (org.openqa.selenium.WebElement item : items) {
                        try {
                            org.openqa.selenium.WebElement titleEl =
                                item.findElement(By.cssSelector(".ticket-title"));
                            if (titleEl.getText().contains(ticketTitle)) {
                                return true;
                            }
                        } catch (Exception ignored) {}
                    }
                    return false;
                });
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "si el ticket '" + ticketTitle + "' está en la lista";
    }
}
