package net.runelite.client.plugins.tobhelper;

import com.google.inject.Inject;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

public class ToBModeDecider {
    private TobHelperNpcEnum tobHelperNpcEnum;
    private TobHelperModeEnum tobHelperModeEnum;
    
    @Inject
    private TobHelperPlugin plugin;
    
    private ToBModeDecier(TobHelperPlugin plugin) {
        this.plugin = plugin;   
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npc) {
        int npcId = npc.getNpc().getId();
        String npcKey = TobHelperNpcEnum.getEnumKeyFromId(npcId);
        if(npcKey && npcKey != "end") {
            this.plugin.mode = npcKey;
            this.plugin.setMode()
        }
    }
}
