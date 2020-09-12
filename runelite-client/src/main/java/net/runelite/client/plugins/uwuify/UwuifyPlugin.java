package net.runelite.client.plugins.uwuify;


import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import javax.inject.Inject;

@PluginDescriptor(
    name = "[J] UwUify",
    description = "UwUifier",
    tags = {"fun"},
    loadWhenOutdated = true,
    enabledByDefault = false
)

public class UwuifyPlugin extends Plugin {

    @Inject
    private Client client;

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        Widget npcDialog = client.getWidget(WidgetInfo.DIALOG_NPC_TEXT);

        // Check if dialog exists on screen
        if(npcDialog == null) {
            return;
        }

        String npcDialogText = Text.removeTags(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT).getText());

        npcDialog.setText(uwuify(npcDialogText));
    }

    public String uwuify(String npcDialog) {
        String newDialog1 = npcDialog.replace("l", "w");
        String newDialog2 = newDialog1.replace("I", "me");
        String newDialog3 = newDialog2.replace("you", "u");
        String newDialog4 = newDialog3.replace("r", "w");
        String newDialog5 = newDialog4.replace("the", "da");
        String newDialog6 = newDialog5.replace("this", "dis");

        return newDialog6;
    }

}
