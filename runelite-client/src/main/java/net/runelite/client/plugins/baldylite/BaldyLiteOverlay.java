package net.runelite.client.plugins.baldylite;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class BaldyLiteOverlay extends Overlay {

    private BaldyLitePlugin plugin;

    private PanelComponent panelComponent = new PanelComponent();

    @Inject
    public BaldyLiteOverlay(BaldyLitePlugin plugin) {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Hair Style")
                .right(plugin.isBald(plugin.getPlayerHairstyle()))
                .build());

        return panelComponent.render(graphics);
    }
}