package net.runelite.client.plugins.tobmodedecider;

import com.google.inject.Inject;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "[J] Tob Mode Decider",
        description = "Plugin that decides Tob Mode",
        enabledByDefault = true,
        hidden = true
)
public class ToBModeDeciderPlugin extends Plugin {
    private String mode;

    @Inject
    private ToBModeDeciderEnum modes;

    private ToBModeDeciderPlugin(String mode) {
        this.mode = mode;
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned npc) {

    }

    private void setMode(String tobMode) {
        this.mode = tobMode;
    }

    public String getMode() {
        return this.mode;
    }
}
