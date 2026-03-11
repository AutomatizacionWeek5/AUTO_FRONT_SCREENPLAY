package org.screenplay.actors;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

/**
 * Fábrica de actores del Sistema de Tickets.
 * Encapsula la creación de actores con sus habilidades.
 */
public final class TheUser {

    private TheUser() {
    }

    /**
     * Crea un actor con nombre genérico "Usuario" que puede navegar con el driver dado.
     */
    public static Actor named(String name, WebDriver driver) {
        return Actor.named(name).whoCan(BrowseTheWeb.with(driver));
    }

    /**
     * Crea un actor "Usuario" estándar capaz de navegar.
     */
    public static Actor called(String name, WebDriver driver) {
        return named(name, driver);
    }
}
