package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

/**
 * Pregunta: ¿está visible el mensaje de error de autenticación en la UI?
 */
public class IsAuthErrorVisible implements Question<Boolean> {

    private IsAuthErrorVisible() {
    }

    public static IsAuthErrorVisible onCurrentPage() {
        return new IsAuthErrorVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return !BrowseTheWeb.as(actor).getDriver()
                .findElements(By.cssSelector(".auth-error"))
                .isEmpty();
    }

    @Override
    public String toString() {
        return "si el error de autenticación está visible";
    }
}
