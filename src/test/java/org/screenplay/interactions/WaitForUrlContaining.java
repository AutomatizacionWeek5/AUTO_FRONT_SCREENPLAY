package org.screenplay.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitForUrlContaining implements Interaction {

    private final String urlFragment;
    private final int timeoutSeconds;

    public WaitForUrlContaining(String urlFragment, int timeoutSeconds) {
        this.urlFragment    = urlFragment;
        this.timeoutSeconds = timeoutSeconds;
    }

    public static WaitForUrlContaining theFragment(String urlFragment) {
        return new WaitForUrlContaining(urlFragment, 25);
    }

    public static WaitForUrlContaining theFragment(String urlFragment, int timeoutSeconds) {
        return new WaitForUrlContaining(urlFragment, timeoutSeconds);
    }

    @Override
    @Step("{0} espera que la URL contenga '#urlFragment'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.urlContains(urlFragment));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("[WARN] Timeout esperando URL con: " + urlFragment);
        }
    }

    @Override
    public String toString() {
        return "esperar URL que contenga '" + urlFragment + "'";
    }
}
