package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

/**
 * Pregunta: devuelve la URL actual del navegador.
 */
public class CurrentUrl implements Question<String> {

    private CurrentUrl() {
    }

    public static CurrentUrl ofTheBrowser() {
        return new CurrentUrl();
    }

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    }

    @Override
    public String toString() {
        return "la URL actual del navegador";
    }
}
