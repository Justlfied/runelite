package net.runelite.client.plugins.overloadtimerbrew;

import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;

public class OverloadTimerBrewOverlayWarning extends OverlayPanel {

    private final OverloadTimerBrewPlugin plugin;
    private final OverloadTimerBrewConfig config;

    @Inject
    private OverloadTimerBrewOverlayWarning(OverloadTimerBrewPlugin plugin, OverloadTimerBrewConfig config) {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGHEST);
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(!config.tickOverlay()) {
            return null;
        }

        panelComponent.getChildren().clear();
        panelComponent.setBackgroundColor(plugin.overloadTimeColor);
        panelComponent.getChildren().add(LineComponent.builder().left("Ticks until brew: ").right(Integer.toString(plugin.overloadTimer)).build());

        if(plugin.overloadTimer % 25 < 6 && plugin.overloadTimer != 300) {
            panelComponent.getChildren().add(LineComponent.builder().left("OPTIMAL TIME TO BREW!!!").build());
        }

        return super.render(graphics);
    }
}
