package org.screenplay.tasks.auth;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.ui.RegisterPageUi;
import org.screenplay.utils.config.TestConfig;

import java.time.Duration;

/**
 * Tarea: registrar un nuevo usuario en la aplicación.
 * Responsabilidad única: completar el formulario de registro y enviarlo.
 */
public class Register implements Task {

    private final String username;
    private final String email;
    private final String password;
    private final String confirmPassword;

    public Register(String username, String email, String password, String confirmPassword) {
        this.username        = username;
        this.email           = email;
        this.password        = password;
        this.confirmPassword = confirmPassword;
    }

    public static Register withData(String username, String email, String password) {
        return new Register(username, email, password, password);
    }

    public static Register withData(String username, String email, String password, String confirmPassword) {
        return new Register(username, email, password, confirmPassword);
    }

    @Override
    @Step("{0} se registra con username '#username' y email '#email'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        driver.get(TestConfig.REGISTER_URL);

        actor.attemptsTo(
            Enter.theValue(username).into(RegisterPageUi.USERNAME),
            Enter.theValue(email).into(RegisterPageUi.EMAIL),
            Enter.theValue(password).into(RegisterPageUi.PASSWORD),
            Enter.theValue(confirmPassword).into(RegisterPageUi.CONFIRM_PASSWORD),
            Click.on(RegisterPageUi.REGISTER_BUTTON)
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/tickets"),
                ExpectedConditions.visibilityOfElementLocated(
                    org.openqa.selenium.By.cssSelector(".auth-error"))
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            System.out.println("[WARN] Timeout esperando resultado del registro.");
        }

        actor.attemptsTo(DemoDelay.forConfiguredTime());
    }

    @Override
    public String toString() {
        return "registrarse como '" + username + "' con email '" + email + "'";
    }
}
