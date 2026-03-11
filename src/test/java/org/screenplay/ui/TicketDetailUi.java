package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

public class TicketDetailUi {

    public static final Target TICKET_NUMBER =
            Target.the("número del ticket").locatedBy(".ticket-detail-number");

    public static final Target TICKET_TITLE =
            Target.the("título del detalle del ticket").locatedBy(".ticket-detail-title");

    public static final Target TICKET_STATUS =
            Target.the("estado del ticket").locatedBy(".ticket-detail-status");

    public static final Target PRIORITY_BADGE =
            Target.the("badge de prioridad").locatedBy(".priority-badge");

    public static final Target TICKET_DESCRIPTION =
            Target.the("descripción del ticket").locatedBy(".ticket-detail-description");

    public static final Target TICKET_META =
            Target.the("metadatos del ticket").locatedBy(".ticket-detail-meta span");

    public static final Target RESPONSES_TITLE =
            Target.the("título de respuestas").locatedBy(".responses-title");

    public static final Target RESPONSES_EMPTY =
            Target.the("respuestas vacías").locatedBy(".responses-empty");

    public static final Target RESPONSE_ITEMS =
            Target.the("items de respuesta").locatedBy("[data-testid='response-item']");

    public static final Target ADMIN_RESPONSE_TEXTAREA =
            Target.the("textarea de respuesta admin").locatedBy("textarea");

    public static final Target ADMIN_RESPONSE_SUBMIT =
            Target.the("botón enviar respuesta admin").locatedBy("button[type='submit']");

    private TicketDetailUi() {
    }
}
