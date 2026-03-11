# Serenity BDD – OpenCart Automation

Ejercicio de ejemplo para mostrar una automatización web básica con **Serenity BDD + Gradle + JUnit 4** sobre OpenCart.

Su propósito es evidenciar, de forma simple, la estructura del proyecto, la configuración en [serenity.conf](serenity.conf), la ejecución por Gradle y el **Reporte Vivo** de Serenity con capturas.

## Qué hace ahora el proyecto

La suite ejecuta tres escenarios de ejemplo:

1. **MacBook**  
  valida la adición del producto al carrito y consulta el resumen de compra.

2. **iPhone**  
  valida el flujo de carrito e incorpora pausas intencionales para reflejar una mayor duración en el reporte.

3. **Camioneta**  
  ejecuta una búsqueda de un producto inexistente y falla de forma controlada para evidenciar el reporte de error en Serenity.

## Pasos de ejecución

Si hace falta, dar permisos al wrapper:

```bash
chmod +x gradlew
```

Ejecutar la suite y generar el reporte:

```bash
./gradlew clean test aggregate
```

Abrir el reporte:

```bash
./gradlew openReport
```

Ejecutar todo y abrir el reporte al final:

```bash
./gradlew runTestsAndOpenReport
```

## Configuración relevante

La configuración principal está centralizada en [serenity.conf](serenity.conf):

- navegador `chrome`
- ejecución `headless`
- capturas `AFTER_EACH_STEP`
- tiempos de espera básicos

Si se requiere ejecución visible, se puede retirar `--headless=new` de [serenity.conf](serenity.conf).

## Requisitos

Para ejecutar el proyecto se requiere:

- Java 17 o superior
- Google Chrome
- Linux, Windows o macOS

Consideraciones:

- el proyecto usa `./gradlew`
- no requiere instalación manual de Gradle
- WebDriverManager gestiona el driver automáticamente

## Reporte de Serenity

Después de la ejecución, el reporte queda disponible en:

- [target/site/serenity/index.html](target/site/serenity/index.html)

El reporte incluye:

- estado de cada escenario
- detalle paso a paso
- capturas automáticas
- evidencia del escenario fallido

## Sobre las HU

En este ejercicio **no se modelan HU formales**.

Los tests actuales se mantienen como **escenarios de ejemplo**, ya que el objetivo principal es demostrar la estructura del proyecto y el comportamiento del reporte de Serenity.

## Estructura actual del proyecto

```text
Serenity-BDD/
├── build.gradle
├── settings.gradle
├── serenity.conf
└── src/
   └── test/
      ├── java/com/opencart/
      │   ├── features/
      │   ├── pages/
      │   └── steps/
      └── resources/
        └── logback-test.xml
```

Resumen:

- `features`: clases de prueba
- `steps`: pasos de negocio
- `pages`: interacción con la interfaz
- `serenity.conf`: configuración de Serenity
- `build.gradle`: dependencias y tareas de ejecución
