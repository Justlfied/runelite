package net.runelite.client.plugins.tobhelper;

import com.google.inject.Inject;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

public class ToBModeDecider {
    private TobHelperNpcEnum tobHelperNpcEnum;
    private TobHelperModeEnum tobHelperModeEnum;
    
    @Inject
    private final TobHelperPlugin plugin;
    
    private ToBModeDecider(TobHelperPlugin plugin) {
        this.plugin = plugin;   
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npc) {
        System.out.println(npc.getNpc().getName());
        int npcId = npc.getNpc().getId();
        String npcKey = TobHelperNpcEnum.getEnumKeyFromId(npcId);
        if(npcKey != null && !npcKey.equals("end")) {
            this.plugin.setMode(npcKey);
        }
    }
}
