package net.runelite.client.plugins.nylowavetimer;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;

import java.awt.*;

public class NyloWaveTimerOverlay extends Overlay {
    private NyloWaveTimerPlugin plugin;
    private Client client;

    @Inject
    private NyloWaveTimerOverlay(NyloWaveTimerPlugin plugin, Client client) {
        this.plugin = plugin;
        this.client = client;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if(WorldPoint.fromLocalInstance(client, this.client.getLocalPlayer().getLocalLocation()).getRegionID() == 13122) {
            for(LocalPoint textLocation : plugin.textLocations) {
                System.out.println(textLocation);
                System.out.println(Perspective.getCanvasTextLocation(this.client, graphics, textLocation, Integer.toString(plugin.waveTimer), 0));
                Point canvasTextLocation = Perspective.getCanvasTextLocation(this.client, graphics, textLocation, Integer.toString(plugin.waveTimer), 0);
                OverlayUtil.renderTextLocation(graphics, canvasTextLocation, Integer.toString(plugin.waveTimer), Color.BLUE);
            }
        }

        return null;
    }
}
