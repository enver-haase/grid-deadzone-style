package org.vaadin.addons.grid.style.repair;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class GridDeadzoneStyleInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent ->
                uiEvent.getUI().getPage().addStyleSheet(
                        "grid-deadzone-style/grid-fix.css"));
    }
}
