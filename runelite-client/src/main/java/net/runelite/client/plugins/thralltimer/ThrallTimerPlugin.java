package net.runelite.client.plugins.thralltimer;

import com.google.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.itemstats.stats.Stats;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "[J] Thrall Timer",
        description = "Quick Thrall Timer Plugin"
)
public class ThrallTimerPlugin extends Plugin {
    public int thrallTimer = 0;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ThrallTimerOverlay overlay;

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

    @Subscribe
    private void onChatMessage(ChatMessage message) {
        if(this.client.getLocalPlayer().getName().toLowerCase().contains("dipyo")) {
            return;
        }

        String chatMessage = message.getMessage();

        if(chatMessage.toLowerCase().contains("your greater skeletal thrall returns to the grave.") ||
           chatMessage.toLowerCase().contains("your greater ghostly thrall returns to the grave.") ||
           chatMessage.toLowerCase().contains("your greater zombified thrall returns to the grave."))
        {
            thrallTimer = 0;
        }

        if(chatMessage.toLowerCase().contains("you resurrect a greater skeletal thrall.") ||
           chatMessage.toLowerCase().contains("you resurrect a greater ghostly thrall.") ||
           chatMessage.toLowerCase().contains("you resurrect a greater zombified thrall.") )
        {
            thrallTimer = Stats.MAGIC.getValue(client);
        }
    }

    @Subscribe
    private void onGameTick(GameTick gameTick) {
        if(this.thrallTimer > 0) {
            thrallTimer--;
        }
    }

    private boolean summonThrall(String chatMessage){
        return chatMessage.toLowerCase().contains("you resurrect a greater skeletal thrall.") ||
                chatMessage.toLowerCase().contains("you resurrect a greater ghostly thrall.") ||
                chatMessage.toLowerCase().contains("you resurrect a greater zombified the thrall.") ||
                chatMessage.toLowerCase().contains("yeet");
    }

    private boolean despawnThrall(String chatMessage) {
        return chatMessage.toLowerCase().contains("your greater skeletal thrall returns to the grave.") ||
                chatMessage.toLowerCase().contains("your greater ghostly thrall returns to the grave.") ||
                chatMessage.toLowerCase().contains("your greater zombified thrall returns to the grave.") ||
                chatMessage.toLowerCase().contains("skrrt");
    }
}
