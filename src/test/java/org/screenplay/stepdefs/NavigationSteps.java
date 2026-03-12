package org.screenplay.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.screenplay.questions.CurrentUrl;
import org.screenplay.questions.IsElementVisible;
import org.screenplay.questions.IsNavBarVisible;
import org.screenplay.questions.IsPageLoadedAt;
import org.screenplay.tasks.auth.Login;
import org.screenplay.tasks.navigation.Logout;
import org.screenplay.tasks.navigation.NavigateTo;
import org.screenplay.tasks.navigation.OpenApplication;
import org.screenplay.utils.config.TestConfig;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.*;

public class NavigationSteps {

    @Given("el usuario navega a la aplicación")
    public void elUsuarioNavegaALaAplicacion() {
        givenThat(theActorCalled("Usuario")).attemptsTo(
            OpenApplication.inTheBrowser()
        );
    }

    @When("el usuario navega a {string}")
    public void elUsuarioNavegaA(String destination) {
        when(theActorInTheSpotlight()).attemptsTo(
            NavigateTo.theSection(destination)
        );
    }

    @When("el administrador navega a {string}")
    public void elAdministradorNavegaA(String destination) {
        when(theActorInTheSpotlight()).attemptsTo(
            NavigateTo.theSection(destination)
        );
    }

    @When("el usuario cierra sesión")
    public void elUsuarioCierraSesion() {
        when(theActorInTheSpotlight()).attemptsTo(
            Logout.fromTheApplication()
        );
    }

    @Then("debería ser redirigido a la página de login")
    public void deberiaSerRedirigidoALaPaginaDeLogin() {
        String currentUrl = CurrentUrl.ofTheBrowser().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(currentUrl)
                .as("La URL debería contener '/login'")
                .contains("/login");
    }

    @Then("la barra de navegación debería estar visible")
    public void laBarraDeNavegacionDeberiaEstarVisible() {
        Boolean navBarVisible = IsNavBarVisible.onCurrentPage().answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(navBarVisible)
                .as("La barra de navegación debería ser visible después del login")
                .isTrue();
    }

    @Then("la página de asignaciones debería estar cargada")
    public void laPaginaDeAsignacionesDeberiaEstarCargada() {
        Boolean pageLoaded = IsPageLoadedAt.containing("/assignments").answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(pageLoaded)
                .as("La página de asignaciones debería estar cargada")
                .isTrue();
    }

    @Then("la página de notificaciones debería estar cargada")
    public void laPaginaDeNotificacionesDeberiaEstarCargada() {
        Boolean pageLoaded = IsPageLoadedAt.containing("/notifications").answeredBy(theActorInTheSpotlight());
        Assertions.assertThat(pageLoaded)
                .as("La página de notificaciones debería estar cargada")
                .isTrue();
    }

  
    @Given("el administrador está autenticado en el sistema")
    public void elAdministradorEstaAutenticadoEnElSistema() {
        givenThat(theActorCalled("Usuario")).attemptsTo(
            Login.withCredentials(TestConfig.ADMIN_EMAIL, TestConfig.ADMIN_PASSWORD)
        );
    }

    @Given("el usuario está autenticado en el sistema")
    public void elUsuarioEstaAutenticadoEnElSistema() {
        elAdministradorEstaAutenticadoEnElSistema();
    }

    @When("consulta las asignaciones")
    public void consultaLasAsignaciones() {
        elUsuarioNavegaA("Asignaciones");
    }

    @Then("puede ver la lista de asignaciones")
    public void puedeVerLaListaDeAsignaciones() {
        laPaginaDeAsignacionesDeberiaEstarCargada();
    }

    @When("consulta sus notificaciones")
    public void consultaSusNotificaciones() {
        elUsuarioNavegaA("Notificaciones");
    }

    @Then("puede ver el panel de notificaciones")
    public void puedeVerElPanelDeNotificaciones() {
        laPaginaDeNotificacionesDeberiaEstarCargada();
    }

    @Then("la sesión queda cerrada y es redirigido al inicio de sesión")
    public void laSesionQuedaCerradaYEsRedirigido() {
        deberiaSerRedirigidoALaPaginaDeLogin();
    }
}
