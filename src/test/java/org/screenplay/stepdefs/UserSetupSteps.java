package org.screenplay.stepdefs;

import io.cucumber.java.en.Given;
import org.screenplay.context.ScenarioContext;
import org.screenplay.tasks.setup.EnsureUserExists;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step Definitions para preparación de usuarios de prueba.
 * Garantiza la existencia del usuario en el sistema antes del escenario.
 */
public class UserSetupSteps {

    @Given("el usuario {string} con email {string} y contraseña {string} existe previamente en el sistema")
    public void elUsuarioExistePreviamenteEnElSistema(String username, String email, String password) {
        ScenarioContext.get().setUsername(username);
        ScenarioContext.get().setEmail(email);
        ScenarioContext.get().setPassword(password);

        givenThat(theActorCalled("Usuario")).attemptsTo(
            EnsureUserExists.withCredentials(username, email, password)
        );
    }
}
