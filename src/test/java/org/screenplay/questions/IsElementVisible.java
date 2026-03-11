package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Pregunta: ¿está visible algún elemento con el selector CSS dado?
 */
public class IsElementVisible implements Question<Boolean> {

    private final String cssSelector;

    private IsElementVisible(String cssSelector) {
        this.cssSelector = cssSelector;
    }

    public static IsElementVisible withSelector(String cssSelector) {
        return new IsElementVisible(cssSelector);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<WebElement> elements = BrowseTheWeb.as(actor).getDriver()
                .findElements(By.cssSelector(cssSelector));
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    @Override
    public String toString() {
        return "si el elemento '" + cssSelector + "' está visible";
    }
}
