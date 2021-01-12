package net.runelite.client.plugins.socket.plugins.grubcounter;

import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.raidsscouter.Raids1Util;
import net.runelite.client.plugins.socket.org.json.JSONObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Map;

public class GrubCounterOverlay extends Overlay {

    private GrubCounterPlugin grubCounter;
    private PanelComponent panelComponent = new PanelComponent();
    private GrubCounterPlugin plugin;

    @Inject
    private Client client;

    @Inject
    public GrubCounterOverlay(GrubCounterPlugin grubCounter) {
        super(grubCounter);
        this.plugin = grubCounter;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Map<String, JSONObject> members = this.plugin.getMembers();
        Map<String, Integer> total = this.plugin.getTotal();
        panelComponent.getChildren().clear();

        WorldPoint wp = client.getLocalPlayer().getWorldLocation();
        int x = wp.getX() - client.getBaseX();
        int y = wp.getY() - client.getBaseY();
        int type = Raids1Util.getroom_type(client.getInstanceTemplateChunks()[client.getPlane()][x / 8][y / 8]);

        // If room isnt thieving dont show overlay
        if(type != 13) {
            return null;
        }

        if(members.isEmpty()) {
            return null;
        }

        panelComponent.getChildren().add(TitleComponent.builder().text("Total Grubs: " + total).build());

        members.forEach((player, count) -> {
            addLine(player, count);
        });

        return this.panelComponent.render(graphics);
    }

    private void addLine(String player, JSONObject chestsData) {

        panelComponent.getChildren().add(LineComponent.builder()
            .left(player) // Player Name
            .right(chestsData.getInt("chestWithGrubs") + "/" + chestsData.getInt("chestOpened")) // Chests info
            .build());
    }
}
