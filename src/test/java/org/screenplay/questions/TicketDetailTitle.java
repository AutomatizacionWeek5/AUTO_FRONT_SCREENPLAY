package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TicketDetailTitle implements Question<String> {

    private TicketDetailTitle() {
    }

    public static TicketDetailTitle fromDetailPage() {
        return new TicketDetailTitle();
    }

    @Override
    public String answeredBy(Actor actor) {
        var driver = BrowseTheWeb.as(actor).getDriver();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".ticket-detail-title")));
            List<org.openqa.selenium.WebElement> elements =
                driver.findElements(By.cssSelector(".ticket-detail-title"));
            return elements.isEmpty() ? "" : elements.get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return "el título del detalle del ticket";
    }
}
