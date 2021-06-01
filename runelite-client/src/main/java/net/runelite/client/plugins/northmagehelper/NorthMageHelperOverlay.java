package net.runelite.client.plugins.northmagehelper;

import com.google.inject.Inject;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ColorUtil;

import java.awt.*;

public class NorthMageHelperOverlay extends Overlay {
    private final NorthMageHelperPlugin plugin;

    @Inject
    NorthMageHelperOverlay(NorthMageHelperPlugin plugin) {
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        for(NPC npc : plugin.highlightCrabs) {
            renderTargetOverlay(graphics, npc, new Color(0, 0, 255, 80));
        }

        return null;
    }

    private void renderTargetOverlay(Graphics2D graphics, NPC actor, Color color)
    {
        Shape objectClickbox = actor.getConvexHull();
        if (objectClickbox != null)
        {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(2));
            graphics.draw(objectClickbox);
            graphics.setColor(ColorUtil.colorWithAlpha(color, color.getAlpha() / 12));
            graphics.fill(objectClickbox);
        }
    }
}
