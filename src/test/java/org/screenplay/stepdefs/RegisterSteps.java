package org.screenplay.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.assertj.core.api.Assertions;
import org.screenplay.context.ScenarioContext;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.questions.IsAuthErrorVisible;
import org.screenplay.questions.IsNavBarVisible;
import org.screenplay.tasks.auth.Register;
import org.screenplay.ui.RegisterPageUi;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step Definitions para escenarios de registro de usuario.
 */
public class RegisterSteps {

    @When("el usuario navega a la página de registro")
    public void elUsuarioNavegaALaPaginaDeRegistro() {
        when(theActorInTheSpotlight()).attemptsTo(
            net.serenitybdd.screenplay.actions.Open.url(
                org.screenplay.utils.config.TestConfig.REGISTER_URL)
        );
    }

    @When("completa el formulario de registro con username {string}, email {string} y contraseña {string}")
    public void completaElFormularioDeRegistro(String username, String email, String password) {
        ScenarioContext.get().setUsername(username);
        ScenarioContext.get().setEmail(email);
        ScenarioContext.get().setPassword(password);

        when(theActorInTheSpotlight()).attemptsTo(
            Register.withData(username, email, password)
        );
    }

    @When("introduce el nombre de usuario {string}")
    public void introduceElNombreDeUsuario(String username) {
        ScenarioContext.get().setUsername(username);
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(username).into(RegisterPageUi.USERNAME)
        );
    }

    @When("introduce el email {string}")
    public void introduceElEmail(String email) {
        ScenarioContext.get().setEmail(email);
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(email).into(RegisterPageUi.EMAIL)
        );
    }

    @When("introduce la contraseña {string}")
    public void introduceLaContrasena(String password) {
        ScenarioContext.get().setPassword(password);
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(password).into(RegisterPageUi.PASSWORD)
        );
    }

    @When("introduce la confirmación de contraseña {string}")
    public void introduceLaConfirmacionDeContrasena(String confirmPassword) {
        when(theActorInTheSpotlight()).attemptsTo(
            Enter.theValue(confirmPassword).into(RegisterPageUi.CONFIRM_PASSWORD)
        );
    }

    @When("hace click en {string}")
    public void haceClickEn(String buttonText) {
        if ("Crear cuenta".equals(buttonText)) {
            when(theActorInTheSpotlight()).attemptsTo(
                Click.on(RegisterPageUi.REGISTER_BUTTON),
                org.screenplay.interactions.DemoDelay.forConfiguredTime()
            );
        }
    }

    @Then("debería ver el error {string}")
    public void deberiaVerElError(String expectedError) {
        Boolean errorVisible = IsAuthErrorVisible.onCurrentPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(errorVisible)
                .as("Debería estar visible el mensaje de error: '" + expectedError + "'")
                .isTrue();
    }

    // -------------------------------------------------------------------------
    // BDD behavior-level steps
    // -------------------------------------------------------------------------

    @When("el usuario se registra con usuario {string}, email {string} y contraseña {string}")
    public void elUsuarioSeRegistra(String username, String email, String password) {
        givenThat(theActorCalled("Usuario")).attemptsTo(
            net.serenitybdd.screenplay.actions.Open.url(
                org.screenplay.utils.config.TestConfig.REGISTER_URL)
        );
        completaElFormularioDeRegistro(username, email, password);
    }

    @Then("el usuario queda autenticado en el sistema")
    public void elUsuarioQuedaAutenticadoEnElSistema() {
        org.openqa.selenium.WebDriver driver =
            BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(25))
            .until(d -> d.getCurrentUrl() != null && d.getCurrentUrl().contains("/tickets"));
        Boolean navBarVisible = IsNavBarVisible.onCurrentPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(navBarVisible)
                .as("El usuario debería estar autenticado (barra de navegación visible)")
                .isTrue();
        when(theActorInTheSpotlight()).attemptsTo(DemoDelay.forConfiguredTime());
    }

    @When("el usuario intenta registrarse con usuario {string}, email {string}, contraseña {string} y confirmación {string}")
    public void elUsuarioIntentaRegistrarse(String username, String email, String password, String confirmPassword) {
        givenThat(theActorCalled("Usuario")).attemptsTo(
            Register.withData(username, email, password, confirmPassword)
        );
    }

    @Then("el sistema rechaza el registro informando que el usuario ya existe")
    public void elSistemaRechazaElRegistroUsuarioExistente() {
        Boolean errorVisible = IsAuthErrorVisible.onCurrentPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(errorVisible)
                .as("El sistema debería mostrar un error cuando el usuario ya existe")
                .isTrue();
        when(theActorInTheSpotlight()).attemptsTo(DemoDelay.forConfiguredTime());
    }
}
