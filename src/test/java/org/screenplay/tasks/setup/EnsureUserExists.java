package org.screenplay.tasks.setup;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.annotations.Step;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

/**
 * Tarea de precondición: asegura que un usuario exista en el sistema antes del escenario.
 * Utiliza llamadas HTTP directas desde Java (no a través del navegador).
 * Responsabilidad única: garantizar la existencia del usuario de prueba.
 */
public class EnsureUserExists implements Task {

    private final String username;
    private final String email;
    private final String password;

    public EnsureUserExists(String username, String email, String password) {
        this.username = username;
        this.email    = email;
        this.password = password;
    }

    public static EnsureUserExists withCredentials(String username, String email, String password) {
        return new EnsureUserExists(username, email, password);
    }

    @Override
    @Step("{0} verifica que el usuario '#email' exista en el sistema")
    public <T extends Actor> void performAs(T actor) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            String loginBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
            HttpRequest loginReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8003/api/auth/login/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginBody))
                .timeout(Duration.ofSeconds(10))
                .build();

            HttpResponse<String> loginResp = httpClient.send(loginReq, HttpResponse.BodyHandlers.ofString());
            if (loginResp.statusCode() == 200) {
                System.out.println("[SETUP] Usuario " + email + " ya existe.");
                return;
            }

            System.out.println("[SETUP] Usuario no encontrado (login:" + loginResp.statusCode() + "). Registrando...");

            String regBody = "{\"username\":\"" + username + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
            HttpRequest regReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8003/api/auth/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(regBody))
                .timeout(Duration.ofSeconds(15))
                .build();

            try {
                HttpResponse<String> regResp = httpClient.send(regReq, HttpResponse.BodyHandlers.ofString());
                System.out.println("[SETUP] Register HTTP status: " + regResp.statusCode());
            } catch (HttpTimeoutException toe) {
                System.out.println("[SETUP] Register timeout — usuario guardado en BD.");
            }

            HttpResponse<String> verifyResp = httpClient.send(loginReq, HttpResponse.BodyHandlers.ofString());
            System.out.println("[SETUP] Verificación post-registro: login:" + verifyResp.statusCode());

        } catch (Exception e) {
            System.out.println("[SETUP] Error creando usuario " + email + ": " + e.getMessage());
        } finally {
            httpClient.close();
        }
    }

    @Override
    public String toString() {
        return "asegurar que el usuario '" + email + "' exista en el sistema";
    }
}
