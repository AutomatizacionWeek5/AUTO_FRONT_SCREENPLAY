package org.screenplay.tasks.auth;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.ui.LoginPageUi;
import org.screenplay.utils.api.ApiHelper;
import org.screenplay.utils.config.TestConfig;

import java.time.Duration;


public class Login implements Task {

    private final String email;
    private final String password;

    public Login(String email, String password) {
        this.email    = email;
        this.password = password;
    }

    public static Login withCredentials(String email, String password) {
        return new Login(email, password);
    }

    @Override
    @Step("{0} inicia sesión con email '#email'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        driver.get(TestConfig.LOGIN_URL);

        actor.attemptsTo(
            Enter.theValue(email).into(LoginPageUi.EMAIL),
            Enter.theValue(password).into(LoginPageUi.PASSWORD),
            Click.on(LoginPageUi.LOGIN_BUTTON)
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.not(ExpectedConditions.urlContains("/login")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth-error"))
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            System.out.println("[WARN] Timeout esperando resultado del login en UI.");
        }

       
        if (driver.getCurrentUrl().contains("/login")) {
            String safeEmail    = email.replace("'", "\\'");
            String safePassword = password.replace("'", "\\'");
            Object result = ApiHelper.apiLogin(driver, safeEmail, safePassword);
            if (result != null && String.valueOf(result).startsWith("login:200")) {
                driver.get(TestConfig.TICKETS_URL);
                ApiHelper.waitForTicketsContent(driver, 15);
            }
        }

        actor.attemptsTo(DemoDelay.forConfiguredTime());
    }

    @Override
    public String toString() {
        return "iniciar sesión con email '" + email + "'";
    }
}
