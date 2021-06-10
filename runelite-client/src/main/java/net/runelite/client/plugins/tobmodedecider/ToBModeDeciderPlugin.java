package net.runelite.client.plugins.tobmodedecider;

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
public class ToBModeDeciderPlugin extends Plugin {
    private String mode;
    private ToBModeDeciderEnum modes;
    private ToBModeDeciderNpcEnum npc;

    private ToBModeDeciderPlugin(String mode, ToBModeDeciderNpcEnum npc) {
        this.mode = "HM";
        this.npc = npc;
    }

    public ToBModeDeciderPlugin() {

    }

    @Subscribe
    private void onChatMessage(ChatMessage message) {
        System.out.println("Message: " + message.getMessage());
        if(message.getMessage().equalsIgnoreCase("enumtest")) {
            setMode("HM");
            System.out.println("Message: " + message.getMessage());
            System.out.println(ToBModeDeciderNpcEnum.valueOf("HM_BLOAT"));
            System.out.println(ToBModeDeciderNpcEnum.valueOf("HM" + "_BLOAT"));
        }
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned npc) {

    }

    public String getEnumValue(String enumKey) {
        return getMode() + enumKey;
    }
        
    public Enum getEnumValue(Enum enumType String enumKey) {
        return enumType.valueOf(getMode() + enumKey);
    }

    private void setMode(String tobMode) {
        this.mode = tobMode;
    }

    public String getMode() {
        return this.mode;
    }
}
