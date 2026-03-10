# Serenity BDD – Google Search Example

Un ejemplo sencillo de **Serenity BDD** con Gradle que muestra:

- La estructura del `build.gradle`
- La configuración de `serenity.conf`
- El **Reporte Vivo** (_Living Documentation_) con capturas de pantalla automáticas
- Un test básico de 3 pasos: **Abrir Google → Buscar → Verificar resultados**

---

## Estructura del proyecto

```
serenity-bdd-google-example/
├── build.gradle                          ← Dependencias y plugin de Serenity
├── settings.gradle                       ← Nombre del proyecto Gradle
├── serenity.conf                         ← Configuración de Serenity y WebDriver
└── src/
    └── test/
        ├── java/
        │   └── com/example/
        │       ├── pages/
        │       │   └── GooglePage.java   ← Page Object (home + resultados)
        │       ├── steps/
        │       │   └── GoogleSteps.java  ← Librería de pasos (@Step)
        │       └── features/
        │           └── GoogleSearchTest.java  ← Test JUnit con SerenityRunner
        └── resources/
            └── logback-test.xml          ← Configuración de logging
```

---

## Estructura del `build.gradle`

```groovy
plugins {
    id 'java'
    id 'net.serenity-bdd.serenity-gradle-plugin' version '3.6.12'  // Plugin oficial
}

dependencies {
    testImplementation "net.serenity-bdd:serenity-core:3.6.12"     // Core de Serenity
    testImplementation "net.serenity-bdd:serenity-junit:3.6.12"    // Integración JUnit 4
    testImplementation "junit:junit:4.13.2"                         // Runner JUnit
    testImplementation "org.assertj:assertj-core:3.27.7"           // Aserciones legibles
    testImplementation "io.github.bonigarcia:webdrivermanager:6.1.0" // Auto-descarga ChromeDriver
}
```

El plugin `serenity-gradle-plugin` registra la tarea `aggregate` que genera el
reporte HTML en `build/site/serenity/`.

---

## Configuración de `serenity.conf`

```hocon
serenity {
    project.name    = "Serenity BDD – Google Search Example"
    take.screenshots = AFTER_EACH_STEP   // Captura tras cada paso
}

webdriver {
    driver = chrome
    chrome.switches = "--headless=new,--no-sandbox,..."
}

timeouts {
    implicit.wait    = 5    // segundos
    default.wait.for = 10   // segundos
}
```

> Para ver el navegador durante la ejecución, quita `--headless=new` de `chrome.switches`.

---

## Los 3 pasos del test básico

```java
@Test
@Title("Search for 'Serenity BDD' and verify results are displayed")
public void searchForSerenityBDD() {

    // Paso 1 – Abrir Google
    googleSteps.opensGoogleHomePage();

    // Paso 2 – Buscar un término
    googleSteps.searchesFor("Serenity BDD");

    // Paso 3 – Verificar que aparecen resultados
    googleSteps.verifiesResultsAreDisplayed();
}
```

Los pasos están definidos en `GoogleSteps` con la anotación `@Step`:

```java
@Step("opens Google home page")
public void opensGoogleHomePage() { ... }

@Step("searches for '{0}'")
public void searchesFor(String searchTerm) { ... }

@Step("verifies search results are displayed")
public void verifiesResultsAreDisplayed() { ... }
```

Cada `@Step` aparece nombrado en el reporte HTML con su captura de pantalla.

---

## Cómo ejecutar

### Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| Java (JDK) | 11 |
| Gradle | 7 (o usa el wrapper incluido) |
| Google Chrome | cualquier versión reciente |

> `WebDriverManager` descarga automáticamente el `chromedriver` correcto,
> por lo que **no** necesitas instalarlo manualmente.

### Comandos

```bash
# 1. Compilar y ejecutar los tests
./gradlew clean test

# 2. Generar el reporte HTML (Living Documentation)
./gradlew aggregate

# Atajo: test + reporte en un solo comando
./gradlew clean test aggregate
```

### Ver el Reporte Vivo

Tras ejecutar `aggregate`, abre en el navegador:

```
build/site/serenity/index.html
```

El reporte incluye:
- Estado de cada test (✅ passed / ❌ failed)
- Detalle de cada paso con su **captura de pantalla**
- Estadísticas generales del proyecto

---

## Resultado esperado

```
> Task :test
com.example.features.GoogleSearchTest > searchForSerenityBDD PASSED
com.example.features.GoogleSearchTest > searchForJavaAutomationTesting PASSED

BUILD SUCCESSFUL in Xs
```

El directorio `build/site/serenity/` contendrá el reporte HTML interactivo
con las capturas de pantalla de cada paso.
