package net.runelite.client.plugins.ravetiles;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PluginDescriptor(
    name = "[J] Rave Tiles",
    description = "Randomly colors random tiles every game tick",
    tags = {"justy", "rave", "cancer"}
)
public class RaveTilesPlugin extends Plugin {
    public Integer colorRed = 0;
    public Integer colorBlue = 0;
    public Integer colorGreen = 0;

    public Map<Tile, Color> tiles = new ConcurrentHashMap();

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private RaveTilesOverlay raveTilesOverlay;

    @Inject
    private ConfigManager configManager;

    @Inject
    private RaveTilesConfig raveTilesConfig;

    @Inject
    private Client client;

    @Inject
    private RaveTilesPlugin(Client client) {
        this.client = client;
    }

    public RaveTilesPlugin() {

    }

    protected void startUp() {
        this.overlayManager.add(this.raveTilesOverlay);
    }

    protected void shutDown() {
        this.overlayManager.remove(this.raveTilesOverlay);
    }

    @Provides
    RaveTilesConfig getConfig(ConfigManager configManager) { return (RaveTilesConfig) configManager.getConfig(RaveTilesConfig.class); }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        this.tiles.clear();

        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        int z = client.getPlane();

        for (int x = 0; x < Constants.SCENE_SIZE; ++x) {
            for (int y = 0; y < Constants.SCENE_SIZE; ++y) {
                Integer rand = (int) Math.floor(Math.random() * Math.floor(255));

                Tile tile = tiles[z][x][y];

                if (tile == null) {
                    continue;
                }

                if(rand < raveTilesConfig.chance()) {
                    this.tiles.put(tile, new Color(this.colorRed, this.colorGreen, this.colorBlue));
                    setColors();
                }
            }
        }
    }

    public void setColors() {
        this.colorBlue = (int)Math.floor(Math.random() * Math.floor(255));
        this.colorRed = (int)Math.floor(Math.random() * Math.floor(255));
        this.colorGreen = (int)Math.floor(Math.random() * Math.floor(255));
    }
}
