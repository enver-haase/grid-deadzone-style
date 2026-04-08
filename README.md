# Grid Deadzone Style

A Vaadin add-on that fixes the visual "dead zone" in the Vaadin Grid component.

## The Problem

The Vaadin Grid has a rendering gap to the right of the last column. Row borders, row stripes, and selection highlights do not extend across this area, leaving an inconsistent appearance. This is a known upstream issue: [vaadin/web-components#7413](https://github.com/vaadin/web-components/issues/7413).

This add-on patches the visual gap via auto-injected CSS for both the Lumo and Aura themes, in light and dark mode.

## Usage

Add the dependency to your project. The fix is applied automatically — no configuration required. The add-on registers itself via Java SPI (`VaadinServiceInitListener`) and injects the CSS into every UI.

## Running the Demo

```bash
mvn
```

The default Maven goal is `spring-boot:test-run`. This compiles the add-on and launches the demo application at `http://localhost:8080`. The demo shows four grids with different variant combinations (row stripes, column borders), and includes a theme switcher (Lumo / Aura) and a color mode switcher (light / dark).

## Building for Vaadin Directory Upload

To produce the ZIP file for upload to the [Vaadin Directory](https://vaadin.com/directory):

```bash
mvn clean install -Pdirectory
```

This generates `target/grid-deadzone-style-1.0.zip`, which contains:

- The compiled JAR
- Sources JAR
- Javadoc JAR
- `LICENSE` (under `META-INF/`)
- `MANIFEST.MF`

Upload that ZIP directly to the Vaadin Directory.

## License

Apache License 2.0
