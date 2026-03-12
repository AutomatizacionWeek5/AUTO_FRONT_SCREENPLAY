package org.screenplay.stepdefs;

import io.cucumber.java.en.Given;
import org.screenplay.context.ScenarioContext;
import org.screenplay.tasks.setup.EnsureUserExists;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;


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

    @Given("el usuario {string} existe en el sistema con email {string} y contraseña {string}")
    public void elUsuarioExisteEnElSistema(String username, String email, String password) {
        elUsuarioExistePreviamenteEnElSistema(username, email, password);
    }
}
