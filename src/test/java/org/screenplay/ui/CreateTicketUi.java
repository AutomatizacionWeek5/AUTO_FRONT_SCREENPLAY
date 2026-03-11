package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CreateTicketUi {

    public static final Target PAGE_TITLE =
            Target.the("título de la página crear ticket").locatedBy(".create-ticket-title");

    public static final Target TITLE_FIELD =
            Target.the("campo título del ticket").locatedBy("#ticket-title");

    public static final Target DESCRIPTION_FIELD =
            Target.the("campo descripción del ticket").locatedBy("#ticket-description");

    public static final Target SUBMIT_BUTTON =
            Target.the("botón enviar ticket")
                  .located(By.cssSelector("button.form-button[type='submit']"));

    public static final Target ERROR_ALERT =
            Target.the("alerta de error en formulario de ticket").locatedBy(".error-alert");

    private CreateTicketUi() {
    }
}
