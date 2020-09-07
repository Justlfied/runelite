package net.runelite.client.plugins.baldylite;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;

@PluginDescriptor(
        name = "[J] BaldyLite",
        description = "BaldyLite Custom Plugin",
        tags = {"config", "menu"},
        loadWhenOutdated = true,
        enabledByDefault = false
)

public class BaldyLitePlugin extends Plugin {
    @Inject
    private BaldyLiteConfig config;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private BaldyLiteOverlay baldyLiteOverlay;

    @Provides
    BaldyLiteConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(BaldyLiteConfig.class);
    }

    @Override
    public void startUp() {
        overlayManager.add(baldyLiteOverlay);
    }

    @Override
    public void shutDown() {
        overlayManager.remove(baldyLiteOverlay);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {

    }

    @Subscribe
    public void onClientTick(ClientTick clientTick) {

    }

    public int getPlayerHairstyle() {
        return client.getVarpValue(261);
    }

    public String isBald(int hairStyle) {
        if(hairStyle == 0) {
            return "Bald";
        } else {
            return "Not Bald";
        }
    }

    public int getCookingLevel() {
        return client.getRealSkillLevel(Skill.COOKING);
    }
}