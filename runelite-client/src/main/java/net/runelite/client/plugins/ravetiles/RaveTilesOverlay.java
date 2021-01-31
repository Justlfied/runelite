package net.runelite.client.plugins.ravetiles;

import net.runelite.api.*;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

public class RaveTilesOverlay extends Overlay {
    private final Client client;
    private final RaveTilesPlugin plugin;

    @Inject
    private RaveTilesOverlay(Client client, RaveTilesPlugin plugin) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGHEST);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        int z = client.getPlane();

        for (int x = 0; x < Constants.SCENE_SIZE; ++x)
        {
            for (int y = 0; y < Constants.SCENE_SIZE; ++y)
            {
                Tile tile = tiles[z][x][y];

                if (tile == null)
                {
                    continue;
                }

                if(this.plugin.tiles.containsKey(tile)) {
                    GroundObject groundObject = tile.getGroundObject();
                    WallObject wallObject = tile.getWallObject();

                    if(groundObject != null) {
                        if (this.client.getLocalPlayer().getLocalLocation().distanceTo(groundObject.getLocalLocation()) <= 2400) {
                            if(z == groundObject.getPlane()) {
                                OverlayUtil.renderTileOverlay(graphics, groundObject, "", this.plugin.tiles.get(tile));
                            }
                        }
                    }

                    if(wallObject != null) {
                        if (this.client.getLocalPlayer().getLocalLocation().distanceTo(wallObject.getLocalLocation()) <= 2400) {
                            if(z == wallObject.getPlane()) {
                                OverlayUtil.renderTileOverlay(graphics, wallObject, "", this.plugin.tiles.get(tile));
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
