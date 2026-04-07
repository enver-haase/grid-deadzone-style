package com.github.enverhaase.gridrowborders;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.List;

@Route("")
public class GridRowBordersView extends VerticalLayout {

    private record Person(String first, String last, String email) {}

    private static final List<Person> SAMPLE_DATA = List.of(
            new Person("Alice",   "Anderson", "alice@example.com"),
            new Person("Bob",     "Brown",    "bob@example.com"),
            new Person("Carol",   "Clark",    "carol@example.com"),
            new Person("Dave",    "Davis",    "dave@example.com"),
            new Person("Eve",     "Evans",    "eve@example.com"),
            new Person("Frank",   "Fisher",   "frank@example.com")
    );

    private Registration themeRegistration;
    private Registration fixRegistration;

    public GridRowBordersView() {
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        add(new H1("Grid Row Borders — Add-on Demo"));
        add(buildControls());
        add(buildGridPanel());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        attachEvent.getUI().getPage().setColorScheme(ColorScheme.Value.LIGHT);
    }

    private HorizontalLayout buildControls() {
        Select<String> themeSelect = new Select<>();
        themeSelect.setLabel("Theme");
        themeSelect.setItems("lumo", "aura", "unstyled");
        themeSelect.setItemLabelGenerator(t -> switch (t) {
            case "lumo"     -> "Lumo";
            case "aura"     -> "Aura";
            case "unstyled" -> "None";
            default         -> t;
        });
        themeSelect.setValue("unstyled");
        themeSelect.addValueChangeListener(e -> {
            String themeUrl = switch (e.getValue()) {
                case "aura" -> "aura/aura.css";
                case "lumo" -> Lumo.STYLESHEET;
                default     -> null;
            };
            String fixUrl = switch (e.getValue()) {
                case "aura" -> "grid-deadzone-style/aura-grid-fix.css";
                case "lumo" -> "grid-deadzone-style/lumo-grid-fix.css";
                default     -> "grid-deadzone-style/unstyled-grid-fix.css";
            };
            applyTheme(e.getSource().getUI().orElse(UI.getCurrent()), themeUrl, fixUrl);
        });

        Select<String> modeSelect = new Select<>();
        modeSelect.setLabel("Mode");
        modeSelect.setItems("light", "dark");
        modeSelect.setItemLabelGenerator(m -> "dark".equals(m) ? "Dark" : "Light");
        modeSelect.setValue("light");
        modeSelect.addValueChangeListener(e -> {
            UI ui = e.getSource().getUI().orElse(UI.getCurrent());
            ui.getPage().setColorScheme(
                    "dark".equals(e.getValue()) ? ColorScheme.Value.DARK : ColorScheme.Value.LIGHT);
        });

        HorizontalLayout controls = new HorizontalLayout(themeSelect, modeSelect);
        controls.setAlignItems(Alignment.END);
        return controls;
    }

    private void applyTheme(UI ui, String themeUrl, String fixUrl) {
        if (themeRegistration != null) {
            themeRegistration.remove();
        }
        if (fixRegistration != null) {
            fixRegistration.remove();
        }
        themeRegistration = themeUrl != null ? ui.getPage().addStyleSheet(themeUrl) : null;
        fixRegistration   = fixUrl   != null ? ui.getPage().addStyleSheet(fixUrl)   : null;
    }

    private Div buildGridPanel() {
        Div panel = new Div();
        panel.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "1fr 1fr")
                .set("gap", "1.5rem")
                .set("width", "100%");

        panel.add(cell("ROW_STRIPES + COLUMN_BORDERS", makeGrid(true,  true)));
        panel.add(cell("COLUMN_BORDERS",               makeGrid(false, true)));
        panel.add(cell("ROW_STRIPES",                  makeGrid(true,  false)));
        panel.add(cell("Plain",                        makeGrid(false, false)));

        return panel;
    }

    private static Div cell(String title, Grid<Person> grid) {
        Div cell = new Div(new H3(title), grid);
        cell.getStyle().set("overflow", "hidden");
        return cell;
    }

    private static Grid<Person> makeGrid(boolean rowStripes, boolean colBorders) {
        Grid<Person> grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::first).setHeader("First");
        grid.addColumn(Person::last).setHeader("Last");
        grid.addColumn(Person::email).setHeader("Email").setResizable(true);
        grid.setItems(SAMPLE_DATA);
        grid.setAllRowsVisible(true);
        grid.addClassName("rows");

        if (rowStripes) grid.addThemeVariants(GridVariant.ROW_STRIPES);
        if (colBorders) grid.addThemeVariants(GridVariant.COLUMN_BORDERS);

        return grid;
    }
}
