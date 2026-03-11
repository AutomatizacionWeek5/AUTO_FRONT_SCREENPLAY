# AUTO_FRONT_SCREENPLAY — Serenity BDD + Screenplay Pattern

Proyecto de pruebas E2E para el **Sistema de Tickets**, implementado con el patrón **Screenplay** sobre Serenity BDD 4.1.20, Cucumber 7.15.0 y Selenium 4.18.1.

---

## Stack tecnológico

| Componente             | Versión   |
|------------------------|-----------|
| Java                   | 17        |
| Gradle                 | 8.14      |
| Serenity BDD           | 4.1.20    |
| Cucumber               | 7.15.0    |
| JUnit                  | 4.13.2    |
| Selenium               | 4.18.1    |
| WebDriverManager       | 5.8.0     |
| AssertJ                | 3.25.3    |

---

## Prerrequisitos

1. **Java 17** instalado y configurado en `JAVA_HOME`
2. **Chrome** instalado (WebDriverManager descarga el driver automáticamente)
3. **Infraestructura levantada** — todos los servicios deben estar corriendo:

```powershell
cd ..\infra
podman-compose up -d
```

Los servicios requeridos son:
- Frontend: `http://localhost:3000`
- Auth service: `http://localhost:8003`
- Ticket service: `http://localhost:8000`
- Notification service: `http://localhost:8001`
- Assignment service: `http://localhost:8002`

---

## Estructura del proyecto

```
src/
└── test/
    ├── java/org/screenplay/
    │   ├── actors/          # Configuración de actores
    │   ├── context/         # ScenarioContext (ThreadLocal)
    │   ├── interactions/    # Interacciones reutilizables (DemoDelay, WaitFor...)
    │   ├── questions/       # Questions (IsTicketInList, CurrentUrl, ...)
    │   ├── runner/          # TicketSystemTestRunner (JUnit + Cucumber)
    │   ├── stepdefs/        # Step Definitions por dominio
    │   ├── tasks/           # Tasks organizadas por dominio
    │   │   ├── auth/        # Login, Register
    │   │   ├── navigation/  # NavigateTo, Logout, OpenApplication
    │   │   ├── setup/       # EnsureUserExists (precondición API)
    │   │   └── tickets/     # FillTicketForm, SubmitTicketForm, OpenTicketDetail
    │   └── ui/              # Locators (NavBarUi, LoginPageUi, CreateTicketUi...)
    └── resources/
        ├── features/
        │   └── sistema_tickets_e2e.feature   # 8 escenarios E2E
        └── serenity.conf                     # Configuración de Serenity
```

---

## Escenarios de prueba

| # | Tag(s)                      | Escenario                                             |
|---|-----------------------------|-------------------------------------------------------|
| 1 | `@registro @happy-path`     | Registro exitoso de un nuevo usuario                  |
| 2 | `@registro @validacion`     | Registro con contraseñas que no coinciden             |
| 3 | `@login @happy-path`        | Login exitoso con usuario registrado                  |
| 4 | `@login @validacion`        | Login con credenciales incorrectas                    |
| 5 | `@flujo-e2e @smoke`         | Flujo E2E completo (login → crear ticket → verificar) |
| 6 | `@asignaciones @admin`      | Administrador accede a la vista de asignaciones       |
| 7 | `@notificaciones`           | Usuario autenticado accede al panel de notificaciones |
| 8 | `@logout`                   | Cierre de sesión exitoso                              |

---

## Comandos de ejecución

> Todos los comandos se ejecutan desde la raíz del proyecto `AUTO_FRONT_SCREENPLAY/`

### Suite completa

```powershell
.\gradlew test --no-daemon
```

### Por escenario individual

#### 1 — Registro exitoso de un nuevo usuario
```powershell
.\gradlew test "-Dcucumber.filter.tags=@registro and @happy-path" --no-daemon
```
> ⚠️ Este escenario falla en la segunda ejecución si el usuario `maria398` ya existe en la BD (condición de datos conocida, igual que en POM Factory).

#### 2 — Registro con contraseñas que no coinciden
```powershell
.\gradlew test "-Dcucumber.filter.tags=@registro and @validacion" --no-daemon
```

#### 3 — Login exitoso con usuario registrado
```powershell
.\gradlew test "-Dcucumber.filter.tags=@login and @happy-path" --no-daemon
```

#### 4 — Login con credenciales incorrectas
```powershell
.\gradlew test "-Dcucumber.filter.tags=@login and @validacion" --no-daemon
```

#### 5 — Flujo E2E completo (smoke)
```powershell
.\gradlew test "-Dcucumber.filter.tags=@smoke" --no-daemon
```

#### 6 — Administrador accede a asignaciones
```powershell
.\gradlew test "-Dcucumber.filter.tags=@asignaciones" --no-daemon
```

#### 7 — Panel de notificaciones
```powershell
.\gradlew test "-Dcucumber.filter.tags=@notificaciones" --no-daemon
```

#### 8 — Cierre de sesión
```powershell
.\gradlew test "-Dcucumber.filter.tags=@logout" --no-daemon
```

### Grupos de escenarios

```powershell
# Solo escenarios de registro
.\gradlew test "-Dcucumber.filter.tags=@registro" --no-daemon

# Solo escenarios de login
.\gradlew test "-Dcucumber.filter.tags=@login" --no-daemon

# Escenarios del flujo completo E2E (feature completa)
.\gradlew test "-Dcucumber.filter.tags=@flujo-completo" --no-daemon
```

---

## Generar reporte HTML de Serenity

```powershell
.\gradlew aggregate --no-daemon
```

El reporte se genera en:
```
target/site/serenity/index.html
```

---

## Configuración

### `serenity.conf` (`src/test/resources/serenity.conf`)

```hocon
webdriver {
  driver = chrome
  base.url = "http://localhost:3000"
}
```

### `demo.delay` (pausa entre pasos, en segundos)

```powershell
.\gradlew test "-Dcucumber.filter.tags=@smoke" "-Ddemo.delay=2" --no-daemon
```

El valor por defecto es `1` segundo. Para ejecución más rápida sin pausa:

```powershell
.\gradlew test "-Dcucumber.filter.tags=@smoke" "-Ddemo.delay=0" --no-daemon
```

---

## Notas de datos de prueba

| Usuario           | Email                          | Contraseña            | Rol   |
|-------------------|--------------------------------|-----------------------|-------|
| `admin`           | `admin@sofkau.com`             | `Admin@SofkaU_2026!`  | ADMIN |
| `e2eflow2026`     | `e2eflow2026@test.sofka.com`   | `TestPass@2026`       | USER  |
| `maria398`        | `userMaria@test.sofka.com`     | `Maria2Tess@2027`     | USER  |

> El usuario `e2eflow2026` se crea automáticamente via API si no existe (`EnsureUserExists`).
> El usuario `maria398` se crea via UI — el escenario `@registro @happy-path` fallará si ya existe en la BD.
