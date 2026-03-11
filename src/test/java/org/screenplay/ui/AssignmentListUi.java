package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AssignmentListUi {

    public static final Target PAGE_TITLE =
            Target.the("título de la página de asignaciones").locatedBy(".page-header__title");

    public static final Target ASSIGNMENT_CARDS =
            Target.the("tarjetas de asignación").locatedBy(".assignment-card");

    public static final Target ASSIGN_SELECT =
            Target.the("selector de agente").located(By.cssSelector("select.assign-select"));

    public static final Target ASSIGN_BUTTON =
            Target.the("botón asignar").located(By.cssSelector("button.assign-btn"));

    public static final Target EMPTY_STATE =
            Target.the("estado vacío de asignaciones").locatedBy(".empty-state");

    private AssignmentListUi() {
    }
}
