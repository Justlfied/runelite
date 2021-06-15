package net.runelite.client.plugins.tobhelper;

import com.google.inject.Inject;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

public class ToBModeDecider {
    private TobHelperNpcEnum tobHelperNpcEnum;

    @Subscribe
    public void onNpcSpawned(NpcSpawned npc) {
        int npcId = npc.getNpc().getId();
        String test = tobHelperNpcEnum::getEnumKeyFromId();
        if(tobHelperNpcEnum::getEnumKeyFromId(npcId)) {

        }
    }
}
