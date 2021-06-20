package net.runelite.client.plugins.tobdecider;

import com.google.inject.Inject;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.tobhelper.TobHelperModeEnum;
import net.runelite.client.plugins.tobhelper.TobHelperNpcEnum;
import net.runelite.client.plugins.tobhelper.TobHelperPlugin;

@PluginDescriptor(
        name = "[J] Tob Mode Decider",
        description = "Plugin that decides Tob Mode",
        enabledByDefault = true,
        tags = {"baldy"}
)
public class ToBDeciderPlugin extends Plugin {
    private TobHelperNpcEnum tobHelperNpcEnum;
    private TobHelperModeEnum tobHelperModeEnum;
    private TobHelperPlugin plugin;

    public ToBDeciderPlugin(){}

    @Subscribe
    public void onNpcSpawned(NpcSpawned npc) {
        System.out.println(npc.getNpc().getName());
        int npcId = npc.getNpc().getId();
        String npcKey = TobHelperNpcEnum.getEnumKeyFromId(npcId);
        System.out.println(npcKey);
        if(npcKey != null && !npcKey.equals("end")) {
            System.out.println(this.plugin);
            this.plugin.setMode(npcKey);
        }
    }
}
