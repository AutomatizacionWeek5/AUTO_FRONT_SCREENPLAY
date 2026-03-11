package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class NotificationListUi {

    public static final Target PAGE_TITLE =
            Target.the("título de la página de notificaciones").locatedBy(".list-header h1");

    public static final Target NOTIFICATION_ITEMS =
            Target.the("items de notificación").locatedBy(".notification-item");

    public static final Target EMPTY_STATE =
            Target.the("estado vacío de notificaciones").locatedBy(".empty-state");

    public static final Target CLEAR_BUTTON =
            Target.the("botón limpiar notificaciones").locatedBy(".btn-clear");

    private NotificationListUi() {
    }
}
