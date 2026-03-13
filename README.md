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
        │   ├── registro.feature          # HU-1: Registro (Scenario Outline)
        │   ├── validaciones.feature      # Edge cases: registro y login inválidos (Scenario Outline)
        │   ├── flujo_e2e.feature         # HU-5: Flujo completo E2E (Scenario Outline)
        │   └── gestion.feature           # HU-6/7: Asignaciones, notificaciones y logout
        └── serenity.conf                     # Configuración de Serenity
```

---

## Escenarios de prueba

| Feature | Tag(s) | Tipo | Iteraciones |
|---------|--------|------|-------------|
| `registro.feature` | `@registro @happy-path` | Scenario Outline | 2 (anyi398, maria398) |
| `validaciones.feature` | `@registro @edge-case` | Scenario Outline | 2 (passwords no coinciden) |
| `validaciones.feature` | `@login @edge-case` | Scenario Outline | 2 (credenciales inválidas) |
| `flujo_e2e.feature` | `@flujo-e2e @smoke` | Scenario Outline | 2 (e2eflow2026, e2eflow2027) |
| `gestion.feature` | `@asignaciones @admin` | Scenario | 1 |
| `gestion.feature` | `@notificaciones` | Scenario | 1 |
| `gestion.feature` | `@logout` | Scenario | 1 |

---

## Comandos de ejecución

> Todos los comandos se ejecutan desde la raíz del proyecto `AUTO_FRONT_SCREENPLAY/`
> **Nota PowerShell:** el carácter `@` es especial. El argumento `-D` siempre entre comillas dobles: `"-Dpropiedad=valor"`.

---

### 1. Solo registro (happy path)

Ejecuta el `Scenario Outline` de `registro.feature` — crea usuarios nuevos vía UI (2 iteraciones).

```powershell
.\gradlew test "-Dcucumber.filter.tags=@registro and @happy-path" --no-daemon
```

---

### 2. Solo validaciones (edge cases)

Ejecuta los dos `Scenario Outline` de `validaciones.feature`: contraseñas no coinciden + credenciales incorrectas (2 iteraciones cada uno).

```powershell
# Todas las validaciones juntas
.\gradlew test "-Dcucumber.filter.tags=@edge-case" --no-daemon

# Solo validaciones de registro
.\gradlew test "-Dcucumber.filter.tags=@registro and @edge-case" --no-daemon

# Solo validaciones de login
.\gradlew test "-Dcucumber.filter.tags=@login and @edge-case" --no-daemon
```

---

### 3. Solo flujo E2E

Ejecuta el `Scenario Outline` de `flujo_e2e.feature`: login → crear ticket → verificar detalle (2 iteraciones).

```powershell
.\gradlew test "-Dcucumber.filter.tags=@flujo-e2e" --no-daemon

# Alias smoke (mismo escenario)
.\gradlew test "-Dcucumber.filter.tags=@smoke" --no-daemon
```

---

### 4. Solo gestión (asignaciones + notificaciones + logout juntos)

Ejecuta los tres escenarios de `gestion.feature` en una sola pasada.

```powershell
.\gradlew test "-Dcucumber.filter.tags=@gestion" --no-daemon
```

Si necesitas ejecutar cada uno por separado:

```powershell
# Solo asignaciones
.\gradlew test "-Dcucumber.filter.tags=@asignaciones" --no-daemon

# Solo notificaciones
.\gradlew test "-Dcucumber.filter.tags=@notificaciones" --no-daemon

# Solo logout
.\gradlew test "-Dcucumber.filter.tags=@logout" --no-daemon
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

### 5. Todo excepto edge cases

Ejecuta registro, flujo E2E y gestión — omite los `Scenario Outline` de validaciones.

```powershell
.\gradlew test "-Dcucumber.filter.tags=not @edge-case" --no-daemon
```

---

### 6. Suite completa

```powershell
.\gradlew test --no-daemon
```

---

### Opciones adicionales

```powershell
# Sin retraso entre pasos (ejecución más rápida)
.\gradlew test "-Ddemo.delay=0" --no-daemon

# Cambiar URL base de la aplicación
.\gradlew test "-Dwebdriver.base.url=http://mi-servidor:3000" --no-daemon

# Combinar opciones: flujo E2E sin delay
.\gradlew test "-Dcucumber.filter.tags=@smoke" "-Ddemo.delay=0" --no-daemon
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

| Usuario | Email | Contraseña | Rol | Creado por |
|---------|-------|------------|-----|------------|
| `admin` | `admin@sofkau.com` | `Admin@SofkaU_2026!` | ADMIN | Sistema |
| `e2eflow2026` | `e2eflow2026@test.sofka.com` | `TestPass@2026` | USER | API automática |
| `e2eflow2027` | `e2eflow2027@test.sofka.com` | `TestPass@2026` | USER | API automática |
| `anyi398` | `userAnyi@test.sofka.com` | `anyi2Tess@2027` | USER | UI (registro) |
| `maria398` | `userMaria@test.sofka.com` | `Maria2Tess@2027` | USER | UI (registro) |

> `e2eflow2026` y `e2eflow2027` se crean automáticamente vía API si no existen (`EnsureUserExists`).
> `anyi398` y `maria398` se crean vía UI — el `@registro @happy-path` fallará si ya existen en BD.
