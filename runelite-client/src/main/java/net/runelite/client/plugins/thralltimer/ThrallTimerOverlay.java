package net.runelite.client.plugins.thralltimer;

import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;

public class ThrallTimerOverlay extends OverlayPanel {
    private final ThrallTimerPlugin plugin;

    @Inject
    private ThrallTimerOverlay(ThrallTimerPlugin plugin) {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGHEST);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(this.plugin.thrallTimer == 0) {
            return null;
        }

        panelComponent.setBackgroundColor(this.plugin.thrallTimer > 10 ? new Color(70, 61, 50, 156) : new Color(255, 0, 0, 80));
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(LineComponent.builder().left("Thrall Ticks: ").right(Integer.toString(plugin.thrallTimer)).build());

        return super.render(graphics);
    }

}
