# Serenity BDD – OpenCart Automation

Proyecto de automatización web con **Serenity BDD + Gradle + JUnit 4** sobre el sitio:

- http://opencart.abstracta.us/

El objetivo actual del proyecto es validar flujos básicos de una tienda OpenCart que sí permite interacción automatizada, incluyendo un escenario negativo intencional para evidenciar cómo Serenity BDD reporta una falla funcional.

## Qué hace ahora el proyecto

La suite automatiza tres escenarios principales:

1. **Resumen de compra MacBook**
   - abre la tienda
   - agrega `MacBook` al carrito desde productos destacados
   - espera la confirmación visible del carrito
   - abre el carrito desde el menú superior
   - valida que el producto exista en el carrito
   - lee los valores de `Sub-Total`, `Eco Tax (-2.00)`, `VAT (20%)` y `Total`

2. **Resumen de compra iPhone**
   - abre la tienda
  - ejecuta dos esperas intencionales de `10` segundos para degradar el tiempo total
   - agrega `iPhone` al carrito desde productos destacados
   - espera la confirmación visible del carrito
   - abre el carrito desde el menú superior
   - valida que el producto exista en el carrito
   - lee los valores de `Sub-Total`, `Eco Tax (-2.00)`, `VAT (20%)` y `Total`

3. **Intento fallido de compra de camioneta**
  - abre la tienda
  - busca `camioneta`
  - intenta agregarla al carrito
  - falla porque el catálogo actual del sitio no vende vehículos

Además, Serenity genera:

- reporte HTML de ejecución
- evidencia por pasos
- capturas automáticas
- detalle de resultados por escenario

---

## Estructura actual del proyecto

```text
Serenity-BDD/
├── build.gradle
├── settings.gradle
├── serenity.conf
└── src/
    └── test/
        ├── java/
      │   └── com/opencart/
        │       ├── features/
      │       │   ├── OpencartCamionetaNegativeTest.java
      │       │   ├── OpencartIphoneTest.java
      │       │   └── OpencartMacbookTest.java
        │       ├── pages/
        │       │   └── OpenCartHomePage.java
        │       └── steps/
        │           └── OpenCartSteps.java
        └── resources/
            └── logback-test.xml
```

### Responsabilidad de cada capa

- `OpencartMacbookTest`, `OpencartIphoneTest` y `OpencartCamionetaNegativeTest`: definen los escenarios de prueba
- `OpenCartSteps`: encapsula acciones y validaciones de negocio
- `OpenCartHomePage`: modela la UI, el carrito y los selectores del sitio
- `serenity.conf`: configura navegador, screenshots y timeouts
- `build.gradle`: dependencias, plugin, ejecución y apertura del reporte con Gradle

---

## Requisitos

Para ejecutar el proyecto se necesita:

| Requisito | Versión sugerida |
|---|---|
| Java JDK | 17 o superior |
| Google Chrome | versión reciente |
| Linux / Windows / macOS | cualquiera |

### Notas importantes

- El proyecto usa el **Gradle Wrapper** (`./gradlew`), por lo que no es obligatorio instalar Gradle manualmente.
- El proyecto usa **WebDriverManager**, así que no hace falta instalar `chromedriver` manualmente.
- La configuración actual usa Chrome en modo **headless**.

Si quieres ver el navegador en ejecución, edita [serenity.conf](serenity.conf) y elimina `--headless=new` de `chrome.switches`.

---

## Dependencias principales

El proyecto usa actualmente:

- `serenity-core` `5.3.3`
- `serenity-junit` `5.3.3`
- `junit` `4.13.2`
- `assertj-core` `3.27.7`
- `webdrivermanager` `6.1.0`
- `logback-classic` `1.5.18`

---

## Escenarios automatizados

### 1. Resumen de compra MacBook

Escenario implementado en la clase de pruebas:

- abrir la home de OpenCart
- agregar `MacBook` al carrito
- esperar la confirmación explícita del carrito
- abrir el carrito desde el menú superior
- verificar que `MacBook` esté presente en el carrito
- leer el resumen de compra

### 2. Resumen de compra iPhone

Escenario implementado en la clase de pruebas:

- abrir la home de OpenCart
- agregar pausas artificiales de `10` segundos para simular peor rendimiento
- agregar `iPhone` al carrito
- esperar la confirmación explícita del carrito
- abrir el carrito desde el menú superior
- verificar que `iPhone` esté presente en el carrito
- leer el resumen de compra

### 3. Intento fallido de compra de camioneta

Escenario implementado en la clase de pruebas:

- abrir la home de OpenCart
- buscar `camioneta`
- intentar agregar `camioneta` al carrito
- fallar de forma intencional porque el sitio es de tecnología y no vende vehículos

### Totales leídos en el carrito

En ambos escenarios se recuperan y reportan estos campos:

- `Sub-Total:`
- `Eco Tax (-2.00):`
- `VAT (20%):`
- `Total:`

### Pasos funcionales

Los pasos reportados por Serenity incluyen acciones como:

- abrir la home de la tienda
- agregar un producto destacado al carrito
- buscar un producto en el catálogo
- intentar agregar un producto buscado al carrito
- introducir una pausa artificial para comparar rendimiento entre escenarios
- esperar la confirmación del carrito
- abrir el shopping cart desde el menú superior
- validar que el producto exista en el carrito
- leer y registrar el resumen de compra

---

## Configuración relevante

La configuración principal está en [serenity.conf](serenity.conf):

- navegador `chrome`
- screenshots `AFTER_EACH_STEP`
- ejecución headless
- timeouts de espera implícita y espera por defecto

Esto permite ejecutar la suite sin abrir una ventana visual del navegador y conservar evidencia en el reporte.

---

## Pasos de ejecución

### 1. Dar permisos al wrapper, si hace falta

```bash
chmod +x gradlew
```

### 2. Ejecutar la suite completa

```bash
./gradlew clean test aggregate
```

### 3. Ejecutar solo los tests

```bash
./gradlew clean test
```

### 4. Generar solo el reporte

```bash
./gradlew aggregate
```

### 5. Abrir el reporte automáticamente

```bash
./gradlew openReport
```

Este comando genera el reporte si hace falta y luego intenta abrir [target/site/serenity/index.html](target/site/serenity/index.html) en el navegador predeterminado.

### 6. Ejecutar todo y abrir el reporte

```bash
./gradlew runTestsAndOpenReport
```

Este comando:

- limpia la ejecución anterior
- corre los tests
- genera el reporte Serenity
- abre el reporte automáticamente en el navegador

---

## Resultado esperado

Al ejecutar la suite completa, deberías ver una salida similar a esta:

```text
OpencartMacbookTest > resumenDeCompraMacBook PASSED
OpencartIphoneTest > resumenDeCompraIPhone PASSED
OpencartCamionetaNegativeTest > intentoFallidoDeCompraDeCamioneta FAILED

BUILD FAILED
```

Esto es esperado: la tercera prueba está diseñada para fallar y mostrar en Serenity cómo se registra un escenario negativo con evidencia paso a paso.

Además, el escenario de `iPhone` ahora incluye demoras artificiales para que en el reporte se vea con peor rendimiento que `MacBook`.

---

## Reporte de Serenity

Después de la ejecución, el reporte queda disponible en:

- [target/site/serenity/index.html](target/site/serenity/index.html)

El reporte incluye:

- estado de cada escenario
- detalle paso a paso
- capturas automáticas
- evidencia funcional de la ejecución
- resumen de compra registrado para `MacBook` e `iPhone`
- un escenario negativo intencional para `camioneta` marcado como fallido
- un escenario de `iPhone` con duración intencionalmente mayor para comparar tiempos

---

## Comandos útiles

```bash
./gradlew clean
./gradlew test
./gradlew aggregate
./gradlew openReport
./gradlew runTestsAndOpenReport
./gradlew clean test aggregate --no-daemon
```

---

## Posibles mejoras futuras

- parametrizar productos de búsqueda
- cubrir flujo de checkout
- agregar validaciones de categorías
- migrar el runner actual si se quiere evitar la advertencia de deprecación de `SerenityRunner`
