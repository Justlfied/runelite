package net.runelite.client.plugins.ravetilesV2;

import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PluginDescriptor(
        name = "[J] Rave TilesV2",
        description = "Randomly colors random tiles every game tick",
        tags = {"justy", "rave", "cancer"}
)
public class RaveTilesV2Plugin extends Plugin {
    public Integer colorRed = 0;
    public Integer colorBlue = 0;
    public Integer colorGreen = 0;

    public Map<LocalPoint, Color> tiles = new ConcurrentHashMap();
    public int radius;
    private int tileSize = 64;

    @Inject
    private ConfigManager configManager;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private RaveTilesV2Overlay overlay;

    @Inject
    private RaveTilesV2Config config;

    @Inject
    private Client client;

    @Inject
    private RaveTilesV2Plugin(Client client) {
        this.client = client;
    }

    public RaveTilesV2Plugin() {

    }

    protected void startUp() {
        this.overlayManager.add(this.overlay);
    }

    protected void shutDown() {
        this.overlayManager.remove(this.overlay);
    }

    @Provides
    RaveTilesV2Config getConfig(ConfigManager configManager) {
        return (RaveTilesV2Config) configManager.getConfig(RaveTilesV2Config.class);
    }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        this.tiles.clear();
        radius = config.radius();

        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        int z = client.getPlane();
        LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();

        for (int x = ((localLocation.getSceneX()) - config.radius()); x < ((localLocation.getSceneX()) + config.radius() + 1); x++) {
            for (int y = ((localLocation.getSceneY()) - config.radius()); y < ((localLocation.getSceneY()) + config.radius() + 1); y++) {
                Integer rand = (int) Math.floor(Math.random() * Math.floor(100));

                Tile tile = tiles[z][x][y];

                if(tile == null) {
                    continue;
                }

                if(rand < config.chance()) {
                    this.tiles.put(new LocalPoint((x * 128) + 63, (y * 128) + 63), new Color(this.colorRed, this.colorGreen, this.colorBlue, (config.opacity() ? (int) Math.floor(Math.random() * Math.floor(255)) : 255)));
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
