package net.runelite.client.plugins.tobhelper;

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
    private TobHelperModeEnum modes;
    private TobHelperNpcEnum npcEnum;
    public int npcID;

    private TobHelperPlugin(String mode, boolean set) {
        this.mode = "HM";
        this.set = false;
    }

    public TobHelperPlugin() {

    }

    @Subscribe
    private void onChatMessage(ChatMessage message) {
        System.out.println("Message: " + message.getMessage());
        if(message.getMessage().equalsIgnoreCase("enumtest")) {
            System.out.println(TobHelperNpcEnum.valueOf(getMode() + "_BLOAT").npcId);
        }
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