package net.runelite.client.plugins.tobhelper;

import com.google.inject.Inject;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.tobdecider.ToBDeciderPlugin;

@PluginDescriptor(
        name = "[J] Tob Helper",
        description = "ToB Helper Class",
        enabledByDefault = true,
        tags = {"baldy"}
)
public class TobHelperPlugin extends Plugin {
    private String mode;
    private Boolean set = false;
    public TobHelperModeEnum modes;
    public TobHelperNpcEnum npcEnum;
    public ToBDeciderPlugin decider;
        
    @Inject
    private EventBus eventBus;

    public TobHelperPlugin(String mode, boolean set, TobHelperNpcEnum npcEnum) {
        this.npcEnum = TobHelperNpcEnum.END;
        this.mode = "HM";
        this.set = false;
    }

    public TobHelperPlugin() {

    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned npc) {
        System.out.println(npc.getNpc().getName());
        if(!this.set) {
            int npcId = npc.getNpc().getId();
            String npcKey = TobHelperNpcEnum.getEnumKeyFromId(npcId);
            if (npcKey != null && !npcKey.equals("end")) {
                this.setMode(npcKey);
            }
        }
    }

    public String getEnumValue(String enumKey) {
        return getMode() + enumKey;
    }

    public void setMode(String tobMode) {
        this.mode = tobMode;
        this.set = true;
    }
    public String getMode() {
        return this.mode;
    }

    private boolean getSet() {
        return this.set;
    }
}
