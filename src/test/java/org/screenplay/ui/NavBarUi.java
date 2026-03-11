package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class NavBarUi {

    public static final Target NAVBAR =
            Target.the("barra de navegación")
                  .located(By.cssSelector(".navbar, nav.navbar"));

    public static final Target LOGO =
            Target.the("logo navbar").locatedBy("a.navbar__logo");

    public static final Target TICKETS_LINK =
            Target.the("enlace Tickets")
                  .located(By.cssSelector("a[href='/tickets']"));

    public static final Target CREATE_TICKET_LINK =
            Target.the("enlace Crear Ticket")
                  .located(By.cssSelector("a[href='/tickets/new']"));

    public static final Target NOTIFICATIONS_LINK =
            Target.the("enlace Notificaciones")
                  .located(By.cssSelector("a[href='/notifications']"));

    public static final Target ASSIGNMENTS_LINK =
            Target.the("enlace Asignaciones")
                  .located(By.cssSelector("a[href='/assignments']"));

    public static final Target NOTIFICATION_BADGE =
            Target.the("badge de notificaciones").locatedBy(".navbar__badge");

    public static final Target LOGOUT_BUTTON =
            Target.the("botón cerrar sesión").locatedBy("li.navbar__logout button");

    public static final Target HAMBURGER_BUTTON =
            Target.the("botón menú hamburguesa").locatedBy("button.navbar__hamburger");

    private NavBarUi() {
    }
}
