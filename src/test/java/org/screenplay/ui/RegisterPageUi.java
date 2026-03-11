package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RegisterPageUi {

    public static final Target USERNAME =
            Target.the("campo nombre de usuario").locatedBy("#username");

    public static final Target EMAIL =
            Target.the("campo email de registro").locatedBy("#email");

    public static final Target PASSWORD =
            Target.the("campo contraseña de registro").locatedBy("#password");

    public static final Target CONFIRM_PASSWORD =
            Target.the("campo confirmar contraseña").locatedBy("#confirmPassword");

    public static final Target REGISTER_BUTTON =
            Target.the("botón Crear cuenta")
                  .located(By.cssSelector("button.btn-primary[type='submit']"));

    public static final Target LOGIN_LINK =
            Target.the("enlace Iniciar sesión")
                  .located(By.cssSelector("a.btn-secondary[href='/login']"));

    public static final Target ERROR_MESSAGE =
            Target.the("mensaje de error de registro").locatedBy(".auth-error");

    public static final Target PAGE_TITLE =
            Target.the("título de la página de registro").locatedBy(".auth-title");

    private RegisterPageUi() {
    }
}
