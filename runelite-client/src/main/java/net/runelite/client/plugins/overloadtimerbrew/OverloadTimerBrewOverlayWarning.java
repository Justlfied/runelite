package net.runelite.client.plugins.overloadtimerbrew;

import net.runelite.api.Client;
import net.runelite.api.Varbits;
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

    @Inject
    private Client client;

    @Override
    public Dimension render(Graphics2D graphics) {
        if(!config.tickOverlay()) {
            return null;
        }

        if(plugin.overloadTimer == 0) {
            return null;
        }

        if (client.getVar(Varbits.IN_RAID) != 1)
        {
            return null;
        }

        panelComponent.getChildren().clear();
        panelComponent.setBackgroundColor(plugin.overloadTimeColor);

        if(config.cycleCounter()) {
            panelComponent.getChildren().add(LineComponent.builder().left("Remaining Cycles: ").right(Integer.toString(plugin.remainingCycles)).build());
            panelComponent.getChildren().add(LineComponent.builder().left("Tick until brew: ").right(Integer.toString(plugin.overloadTimer % 25)).build());
        } else {
            panelComponent.getChildren().add(LineComponent.builder().left("Ticks until brew: ").right(Integer.toString(plugin.overloadTimer)).build());
        }

        if(plugin.overloadTimer % 25 < 6 && plugin.overloadTimer != 300) {
            panelComponent.getChildren().add(LineComponent.builder().left("OPTIMAL TIME TO BREW!!!").build());
        }

        return super.render(graphics);
    }
}
