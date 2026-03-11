package org.screenplay.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.screenplay.context.ScenarioContext;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.questions.IsAuthErrorVisible;
import org.screenplay.tasks.auth.Login;
import org.screenplay.ui.LoginPageUi;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step Definitions para escenarios de autenticación (login).
 * Delega toda la lógica al actor y sus Tasks/Interactions.
 */
public class LoginSteps {

    @Given("un usuario con email {string} y contraseña {string} existe en el sistema")
    public void unUsuarioExisteEnElSistema(String email, String password) {
        ScenarioContext.get().setEmail(email);
        ScenarioContext.get().setPassword(password);
    }

    @Given("el usuario está autenticado con email {string} y contraseña {string}")
    public void elUsuarioEstaAutenticado(String email, String password) {
        givenThat(theActorCalled("Usuario")).attemptsTo(
            Login.withCredentials(email, password)
        );
    }

    @When("el usuario navega a la página de login")
    public void elUsuarioNavegaALaPaginaDeLogin() {
        when(theActorInTheSpotlight()).attemptsTo(
            net.serenitybdd.screenplay.actions.Open.url(
                org.screenplay.utils.config.TestConfig.LOGIN_URL)
        );
    }

    @When("el usuario introduce el email {string}")
    public void elUsuarioIntroduceElEmail(String email) {
        ScenarioContext.get().setEmail(email);
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(email).into(LoginPageUi.EMAIL)
        );
    }

    @When("el usuario introduce la contraseña {string}")
    public void elUsuarioIntroduceLaContrasena(String password) {
        ScenarioContext.get().setPassword(password);
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(password).into(LoginPageUi.PASSWORD)
        );
    }

    @When("el usuario hace click en {string}")
    public void elUsuarioHaceClickEn(String buttonText) {
        if ("Iniciar sesión".equals(buttonText)) {
            when(theActorInTheSpotlight()).attemptsTo(
                Click.on(LoginPageUi.LOGIN_BUTTON),
                org.screenplay.interactions.DemoDelay.forConfiguredTime()
            );
        }
    }

    @When("el usuario ingresa las credenciales almacenadas")
    public void elUsuarioIngresaLasCredencialesAlmacenadas() {
        String email    = ScenarioContext.get().getEmail();
        String password = ScenarioContext.get().getPassword();
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(email).into(LoginPageUi.EMAIL),
            Enter.theValue(password).into(LoginPageUi.PASSWORD)
        );
    }

    @When("hace click en el botón de login")
    public void haceClickEnElBotonDeLogin() {
        when(theActorInTheSpotlight()).attemptsTo(Click.on(LoginPageUi.LOGIN_BUTTON));

        WebDriver driver = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.not(ExpectedConditions.urlContains("/login")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth-error"))
            ));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            System.out.println("[WARN] Timeout esperando resultado del click en botón de login.");
        }
        when(theActorInTheSpotlight()).attemptsTo(DemoDelay.forConfiguredTime());
    }

    @When("completa el formulario de login con:")
    public void completaElFormularioDeLoginCon(List<Map<String, String>> dataTable) {
        Map<String, String> data = dataTable.get(0);
        String email    = data.get("email");
        String password = data.get("password");
        when(theActorInTheSpotlight()).attemptsTo(
            Login.withCredentials(email, password)
        );
    }

    @Then("debería ver un mensaje de error de autenticación")
    public void deberiaVerUnMensajeDeErrorDeAutenticacion() {
        Boolean errorVisible = IsAuthErrorVisible.onCurrentPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(errorVisible)
                .as("El mensaje de error de autenticación debería ser visible")
                .isTrue();
    }
}
