package net.runelite.client.plugins.baldylite;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.*;
import java.awt.Graphics2D;

public class BaldyLiteOverlay extends OverlayPanel {

    private final BaldyLitePlugin plugin;
    private final BaldyLiteConfig config;
    private final Client client;

    @Inject
    public BaldyLiteOverlay(BaldyLitePlugin plugin, BaldyLiteConfig config, Client client) {
        super(plugin);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        setPriority(OverlayPriority.HIGH);
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(!config.showHairStyle()) {
            return null;
        }

        this.renderPlayerOverlay(graphics, Color.RED);

        this.panelComponent.setPreferredSize(new Dimension(140, 0));

        this.panelComponent.getChildren().add(LineComponent.builder()
                .left("Hair Style")
                .right(plugin.isBald(plugin.getPlayerHairstyle()))
                .build());

        return super.render(graphics);
    }

    private void renderPlayerOverlay(Graphics2D graphics, Color color) {
        if(!config.drawHairStyle()) {
            return;
        }

        final Player localPlayer = client.getLocalPlayer();
        final int offset;

        System.out.println(localPlayer.getPlayerComposition());
        
        offset = localPlayer.getLogicalHeight() + 40;

        final String isBald = Text.sanitize(plugin.isBald(plugin.getPlayerHairstyle()));

        Point textLocation = localPlayer.getCanvasTextLocation(graphics, isBald, offset);

        OverlayUtil.renderTextLocation(graphics, textLocation, isBald, color);
    }
}