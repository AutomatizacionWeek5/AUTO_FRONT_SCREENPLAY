package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class TicketListUi {

    public static final Target PAGE_TITLE =
            Target.the("título de la lista de tickets").locatedBy(".page-header__title");

    public static final Target PAGE_SUBTITLE =
            Target.the("subtítulo de la lista de tickets").locatedBy(".page-header__subtitle");

    public static final Target TICKETS_GRID =
            Target.the("grilla de tickets").locatedBy(".tickets-grid");

    public static final Target TICKET_ITEMS =
            Target.the("items de ticket").locatedBy(".ticket-item");

    public static final Target CREATE_TICKET_LINK =
            Target.the("enlace crear ticket en lista")
                  .located(By.cssSelector("a[href='/tickets/new']"));

    public static final Target EMPTY_STATE =
            Target.the("estado vacío de tickets").locatedBy(".empty-state");

    public static final Target TICKET_BY_TITLE =
            Target.the("ticket con título '{0}'")
                  .located(By.xpath("//div[contains(@class,'ticket-item')]//h3[contains(text(),'{0}')]"));

    private TicketListUi() {
    }
}
