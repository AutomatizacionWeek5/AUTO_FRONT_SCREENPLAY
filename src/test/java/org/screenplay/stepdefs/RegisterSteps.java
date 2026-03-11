package org.screenplay.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.assertj.core.api.Assertions;
import org.screenplay.context.ScenarioContext;
import org.screenplay.questions.IsAuthErrorVisible;
import org.screenplay.tasks.auth.Register;
import org.screenplay.ui.RegisterPageUi;

import java.util.List;
import java.util.Map;

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

    @When("completa el formulario de registro con:")
    public void completaElFormularioDeRegistro(List<Map<String, String>> dataTable) {
        Map<String, String> data = dataTable.get(0);
        String username = data.get("username");
        String email    = data.get("email");
        String password = data.get("password");

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
}
