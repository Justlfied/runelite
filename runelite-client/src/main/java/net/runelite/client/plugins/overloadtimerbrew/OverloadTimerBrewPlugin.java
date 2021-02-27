package net.runelite.client.plugins.overloadtimerbrew;

import com.google.inject.Provides;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
    name = "[J] Overload Timer on Brew",
    description = "Overload Times displayed on Brew",
    tags = {"baldylite", "justy", "ovl", "raid"}
)
public class OverloadTimerBrewPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private OverloadTimerBrewOverlay overlay;

    public int overloadTimer = 0;
    public String overloadTimerTime = "";
    public Color overloadTimeColor = new Color(255, 0, 0);

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
    }

    @Provides
    OverloadTimerBrewConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(OverloadTimerBrewConfig.class);
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        String message = chatMessage.getMessage();

        if(message.startsWith("You drink some of your") && message.contains("overload")) {
            overloadTimer = 300;
        }
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if(overloadTimer != 0) {
            overloadTimer--;
        }

        overloadTimeColor = new Color(255, 0, 0);

        if(overloadTimer % 25 < 11) {
            overloadTimeColor = new Color(255, 127, 0);
        }

        if(overloadTimer % 25 < 6) {
            overloadTimeColor = new Color(0, 255, 0);
        }

        overloadTimerTime = setOvlTimerString(overloadTimer);
    }

    private String setOvlTimerString(int ticks) {
        String zeroPrependSeconds = "";

        int totalSeconds = (int) (ticks * 0.6);

        int minutes = (totalSeconds / 60) % 60;
        int seconds = totalSeconds % 60;

        if(seconds < 10) {
            zeroPrependSeconds = 0 + Integer.toString(seconds);
            return minutes + ":" + zeroPrependSeconds;
        }

        return minutes + ":" + seconds;
    }
}
