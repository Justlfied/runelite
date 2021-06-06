package net.runelite.client.plugins.ravetilesV2;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.Map;

public class RaveTilesV2Overlay extends Overlay {
    private final Client client;
    private final RaveTilesV2Plugin plugin;

    @Inject
    private RaveTilesV2Overlay(Client client, RaveTilesV2Plugin plugin) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        for (Map.Entry<LocalPoint, Color> tiles : plugin.tiles.entrySet()) {
            Polygon poly = Perspective.getCanvasTilePoly(client, tiles.getKey());
            if (poly != null)
            {
                renderPoly(graphics, tiles.getValue(), poly);
            }
        }

        return null;
    }

    private void renderPoly(Graphics2D graphics, Color color, Shape polygon) {
        if (polygon != null) {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(2.0F));
            graphics.draw(polygon);
            graphics.setColor(color);
            graphics.fill(polygon);
        }
    }
}
