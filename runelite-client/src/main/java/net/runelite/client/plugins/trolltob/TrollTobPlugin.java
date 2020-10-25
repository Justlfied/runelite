package net.runelite.client.plugins.trolltob;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Arrays;

@PluginDescriptor(
    name = "[J] Troll ToB",
    description = "Plugins to Troll ToB with for you and friends!",
    tags = {"fun"},
    enabledByDefault = false,
    loadWhenOutdated = true
)

public class TrollTobPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private TrollTobConfig config;

    @Provides
    TrollTobConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(TrollTobConfig.class);
    }

    @Subscribe
    public void onClientTick(ClientTick clientTick) {
        if(config.bossAlwaysFullHealth()) {
            Widget bossHpBar = client.getWidget(28, 36);
            Widget bossHpValue = client.getWidget(28, 37);

            if(bossHpBar != null && !bossHpBar.isHidden()) {
                bossHpBar.getChild(0).setTextColor(bossHpBar.getChild(1).getTextColor());
                bossHpValue.setText("100.0%");
            }
        }
    }

    private boolean isLocatedAtTob() {
        return client.getLocalPlayer().getWorldLocation().getRegionID() == 14642;
    }

    public int getLocation() {
        return client.getLocalPlayer().getWorldLocation().getRegionID();
    }

    public boolean isLocatedInTob() {
        return getLocation() == 12613 || // Maiden
               getLocation() == 12869 || // Maiden
               getLocation() == 13125 || // Bloat
               getLocation() == 13122 || // Nylo
               getLocation() == 13123 || // Sote
               getLocation() == 12612 || // Xarp
               getLocation() == 12611;   // Verzik
    }
}
