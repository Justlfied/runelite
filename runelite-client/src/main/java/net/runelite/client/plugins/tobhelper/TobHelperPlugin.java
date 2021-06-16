package net.runelite.client.plugins.tobhelper;

import com.google.inject.Inject;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "[J] Tob Mode Decider",
        description = "Plugin that decides Tob Mode",
        enabledByDefault = true,
        tags = {"baldy"}
)
public class TobHelperPlugin extends Plugin {
    private String mode;
    private Boolean set;
    public TobHelperModeEnum modes;
    public TobHelperNpcEnum npcEnum;
    public ToBModeDecider decider;    
        
    @Inject
    private EventBus eventBus;

    private TobHelperPlugin(String mode, boolean set, TobHelperNpcEnum npcEnum) {
        this.npcEnum = TobHelperNpcEnum.END;
        this.mode = "HM";
        this.set = false;
    }

    public TobHelperPlugin() {

    }
        
    @Override
    public void onStart() {
        eventBus.register(decider.class);
    }
        
    @Override
    public void onShutdoown() {
        eventBus.unregister(decider.class);       
    }

    @Subscribe
    private void onChatMessage(ChatMessage message) {
        System.out.println("Message: " + message.getMessage());
        if(message.getMessage().equalsIgnoreCase("enumtest")) {
            System.out.println(TobHelperNpcEnum.valueOf(getMode() + "_BLOAT").npcId);
        }
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned npc) {
        int npcId = npc.getNpc().getId();
        System.out.println("Npc: " + npcId);
        System.out.println("Mode: " + TobHelperNpcEnum.getEnumKeyFromId(npcId));
    }

    public String getEnumValue(String enumKey) {
        return getMode() + enumKey;
    }

    private void setMode(String tobMode) {
        this.setMode();
        this.mode = tobMode;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode() {
        this.set = !this.set;
    }

    private boolean getSet() {
        return this.set;
    }
}
