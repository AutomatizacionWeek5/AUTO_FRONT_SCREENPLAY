package org.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPageUi {

    public static final Target EMAIL =
            Target.the("campo email").locatedBy("#email");

    public static final Target PASSWORD =
            Target.the("campo contraseña").locatedBy("#password");

    public static final Target LOGIN_BUTTON =
            Target.the("botón Iniciar sesión")
                  .located(By.cssSelector("button.btn-primary[type='submit']"));

    public static final Target REGISTER_LINK =
            Target.the("enlace Registrarse")
                  .located(By.cssSelector("a.btn-secondary[href='/register']"));

    public static final Target ERROR_MESSAGE =
            Target.the("mensaje de error de autenticación").locatedBy(".auth-error");

    public static final Target PAGE_TITLE =
            Target.the("título de la página de login").locatedBy(".auth-title");

    private LoginPageUi() {
    }
}
