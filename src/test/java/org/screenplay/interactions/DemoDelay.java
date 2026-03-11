package org.screenplay.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.annotations.Step;
import org.screenplay.utils.config.TestConfig;

/**
 * Interacción que introduce una pausa configurable (demo.delay) para hacer
 * visible la ejecución en el navegador durante las demostraciones.
 */
public class DemoDelay implements Interaction {

    public DemoDelay() {
    }

    public static DemoDelay forConfiguredTime() {
        return new DemoDelay();
    }

    @Override
    @Step("{0} espera el demo delay configurado")
    public <T extends Actor> void performAs(T actor) {
        int delay = TestConfig.DEMO_DELAY;
        if (delay > 0) {
            try {
                Thread.sleep((long) delay * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return "esperar demo delay (" + TestConfig.DEMO_DELAY + "s)";
    }
}
