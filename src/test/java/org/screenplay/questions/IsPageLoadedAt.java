package org.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

/**
 * Pregunta: ¿está cargada la página en la URL dada?
 * Verifica que la URL actual contenga el fragmento indicado.
 */
public class IsPageLoadedAt implements Question<Boolean> {

    private final String urlFragment;

    private IsPageLoadedAt(String urlFragment) {
        this.urlFragment = urlFragment;
    }

    public static IsPageLoadedAt containing(String urlFragment) {
        return new IsPageLoadedAt(urlFragment);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String currentUrl = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
        return currentUrl.contains(urlFragment);
    }

    @Override
    public String toString() {
        return "si la página cargada contiene '" + urlFragment + "' en la URL";
    }
}
