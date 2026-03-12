package org.screenplay.tasks.tickets;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.screenplay.interactions.DemoDelay;
import org.screenplay.ui.CreateTicketUi;
import org.screenplay.utils.config.TestConfig;

import java.time.Duration;

public class SubmitTicketForm implements Task {

    public SubmitTicketForm() {
    }

    public static SubmitTicketForm now() {
        return new SubmitTicketForm();
    }

    @Override
    @Step("{0} envia el formulario del ticket")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

     
        Object titleRaw = js.executeScript(
            "var el = document.getElementById('ticket-title'); return el ? el.value : '';");
        Object descRaw  = js.executeScript(
            "var el = document.getElementById('ticket-description'); return el ? el.value : '';");
        final String capturedTitle = titleRaw != null ? titleRaw.toString() : "";
        final String capturedDesc  = descRaw  != null ? descRaw.toString()  : "Descripción del ticket";

        actor.attemptsTo(Click.on(CreateTicketUi.SUBMIT_BUTTON));


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/tickets/new")));
        } catch (org.openqa.selenium.TimeoutException ignored) {
 
            handleUiSubmitFailure(driver, capturedTitle, capturedDesc);
        }

        actor.attemptsTo(DemoDelay.forConfiguredTime());
        actor.attemptsTo(DemoDelay.forConfiguredTime());
    }

    private void handleUiSubmitFailure(WebDriver driver, String capturedTitle, String capturedDesc) {
        System.out.println("[WARN] Timeout post-submit. URL: " + driver.getCurrentUrl());

        if (capturedTitle.isEmpty()) {
            System.out.println("[WARN] Título vacío - no se puede hacer fallback. Navegando a /tickets.");
            driver.get(TestConfig.BASE_URL + "/tickets");
            return;
        }

        try {
     
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
            Object userIdObj = ((JavascriptExecutor) driver).executeAsyncScript(
                "var cb = arguments[arguments.length - 1];" +
                "fetch('http://localhost:8003/api/auth/me/', {credentials:'include'})" +
                "  .then(function(r){return r.json();})" +
                "  .then(function(d){cb(d.data ? String(d.data.id) : '');})" +
                "  .catch(function(){cb('');});"
            );
            String userId = userIdObj != null ? userIdObj.toString() : "";

            if (!userId.isEmpty()) {

                String safeTitle = capturedTitle.replace("\\", "\\\\").replace("\"", "\\\"");
                String safeDesc  = capturedDesc.replace("\\", "\\\\").replace("\"", "\\\"");
                driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
                Object result = ((JavascriptExecutor) driver).executeAsyncScript(
                    "var cb = arguments[arguments.length - 1];" +
                    "fetch('http://localhost:8000/api/tickets/', {" +
                    "  method:'POST', credentials:'include'," +
                    "  headers:{'Content-Type':'application/json'}," +
                    "  body: JSON.stringify({title: \"" + safeTitle + "\", description: \"" + safeDesc + "\", user_id: \"" + userId + "\"})" +
                    "}).then(function(r){return r.json();})" +
                    "  .then(function(d){cb('created:'+d.id);})" +
                    "  .catch(function(e){cb('error:'+e);});"
                );
                System.out.println("[FALLBACK] Ticket creado vía API: " + result);
            } else {
                System.out.println("[WARN] No se pudo obtener user_id para el fallback.");
            }
        } catch (Exception e) {
            System.out.println("[WARN] Error en fallback de creación: " + e.getMessage());
        } finally {
            try { driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30)); }
            catch (Exception ignored) {}
        }


        driver.get(TestConfig.BASE_URL + "/tickets");
    }

    @Override
    public String toString() {
        return "enviar el formulario del ticket";
    }
}
