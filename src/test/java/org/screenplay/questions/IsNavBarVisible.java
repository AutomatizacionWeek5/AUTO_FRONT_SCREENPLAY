package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

/**
 * Pregunta: ¿está visible la barra de navegación?
 */
public class IsNavBarVisible implements Question<Boolean> {

    private IsNavBarVisible() {
    }

    public static IsNavBarVisible onCurrentPage() {
        return new IsNavBarVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !BrowseTheWeb.as(actor).getDriver()
                .findElements(By.cssSelector(".navbar, nav.navbar"))
                .isEmpty();
    }

    @Override
    public String toString() {
        return "si la barra de navegación está visible";
    }
}
