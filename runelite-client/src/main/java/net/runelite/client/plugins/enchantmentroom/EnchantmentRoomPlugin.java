package net.runelite.client.plugins.enchantmentroom;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
    name = "[J] Enchantment Room",
    description = "Notifies when bonus is happening",
    tags = {"justy"},
    loadWhenOutdated = true,
    enabledByDefault = true
)

public class EnchantmentRoomPlugin extends Plugin {
    @Inject
    public EnchantmentRoomConfig config;

    @Inject
    public Client client;

    @Inject
    public Notifier notify;

    @Provides
    EnchantmentRoomConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(EnchantmentRoomConfig.class);
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if (!inside())
        {
            return;
        }


        final Player local = client.getLocalPlayer();

        if (client.getGameState() != GameState.LOGGED_IN
            || local == null
            // If user has clicked in the last second then they're not idle so don't send idle notification
            || System.currentTimeMillis() - client.getMouseLastPressedMillis() < 1000
            || client.getKeyboardIdleTicks() < 10)
        {
            return;
        }

        // Hopefully always exists
        Boolean notifyShape = false;

        switch(config.notifySetting()) {
            case PENTAMID:
                if(!client.getWidget(195, 15).isHidden()) {
                    notifyShape = true;
                }
                break;

            case CUBE:
                if(!client.getWidget(195, 11).isHidden()) {
                    notifyShape = true;
                }
                break;

            case CYLINDER:
                if(!client.getWidget(195, 13).isHidden()) {
                    notifyShape = true;
                }
                break;

            case ICOSAHEDRON:
                if(!client.getWidget(195, 17).isHidden()) {
                    notifyShape = true;
                }
                break;
        }

        if(notifyShape == true) {
            notify.notify(config.notifySetting() + " is bonus right now!");
        }
    }

    public boolean inside()
    {
        Player player = client.getLocalPlayer();
        return player != null && player.getWorldLocation().getRegionID() == 13462
            && player.getWorldLocation().getPlane() == 0;
    }
}
