package net.runelite.client.plugins.nylowavetimer;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Objects;

@PluginDescriptor(
        name = "[J] Nylo Waves Timer",
        description = "Timer for when next wave starts"
)
public class NyloWaveTimerPlugin extends Plugin {
    public int waveTimer = 0;
    public boolean waveStarted = false;
    public LocalPoint[] textLocations = new LocalPoint[]{};

    // timer ends at 0 so -1 on the ticks
    private final int[] waveDelay = new int[] {3, 3, 3, 3, 3, 15, 3, 11, 3, 15, 7, 7, 7, 7, 7, 3, 11, 7, 11, 15, 7, 11, 7, 3, 7, 3};
    private int currentWave = 0;
    private boolean locSet = false;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private NyloWaveTimerOverlay overlay;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

    @Subscribe(priority = 1f)
    private void onGameTick(GameTick gameTick) {
        if(!locSet && Objects.requireNonNull(this.client.getLocalPlayer()).getName() != null) {
            textLocations = new LocalPoint[]{
                    LocalPoint.fromWorld(this.client, WorldPoint.fromRegion(13122, 17, 24, 0)),
                    LocalPoint.fromWorld(this.client, WorldPoint.fromRegion(13122, 32, 9, 0)),
                    LocalPoint.fromWorld(this.client, WorldPoint.fromRegion(13122, 46, 25, 0))};

            locSet = true;
        }
        System.out.println(WorldPoint.fromLocalInstance(client, this.client.getLocalPlayer().getLocalLocation()).getRegionID());
        if(waveTimer == 0 && waveStarted) {
            waveTimer = 3;
            return;
        }

        if(waveStarted) {
            waveTimer--;
        }
    }

    public LocalPoint testPoint = null;

    // 2240
    // 8768

    @Subscribe
    private void onChatMessage(ChatMessage message) {
        System.out.println(message.getMessage());
        if(message.getMessage().toLowerCase().contains("test")) {
            testPoint = new LocalPoint(8896, 2240);
            waveStarted = true;
        }
    }
}
