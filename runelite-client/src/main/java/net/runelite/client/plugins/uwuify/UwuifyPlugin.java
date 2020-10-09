package net.runelite.client.plugins.uwuify;

import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
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
    private UwuifyConfig config;

    @Inject
    private Client client;

    @Inject
    private ChatMessageManager chatMessageManager;

    @Provides
    UwuifyConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(UwuifyConfig.class);
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if(!config.changeNpcDialog()) {
            return;
        }

        Widget npcDialog = client.getWidget(WidgetInfo.DIALOG_NPC_TEXT);

        // Check if dialog exists on screen
        if(npcDialog == null) {
            return;
        }

        String npcDialogText = Text.removeTags(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT).getText());

        npcDialog.setText(uwuify(npcDialogText));
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        client.refreshChat();
    }


    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        System.out.println(chatMessage.getType());

        if(!config.changeChatboxMessage()) {
            return;
        }

        String newChatMessage = "";

        if(chatMessage.getType() == ChatMessageType.PUBLICCHAT && config.changeChatboxPublicMessage()) {
            newChatMessage = buildUwUifyString(chatMessage.getMessage());
        } else if(chatMessage.getType() == ChatMessageType.GAMEMESSAGE && config.changeChatboxGameMessage()) {
            newChatMessage = buildUwUifyString(chatMessage.getMessage());
        } else if((chatMessage.getType() == ChatMessageType.PRIVATECHAT && config.changeChatboxPrivateMessage()) || (chatMessage.getType() == ChatMessageType.PRIVATECHATOUT && config.changeChatboxPrivateMessage())) {
            newChatMessage = buildUwUifyString(chatMessage.getMessage());
        } else if(chatMessage.getType() == ChatMessageType.FRIENDSCHAT && config.changeChatboxClanMessage()) {
            newChatMessage = buildUwUifyString(chatMessage.getMessage());
        } else {
            return;
        }

        final MessageNode messageNode = chatMessage.getMessageNode();
        messageNode.setRuneLiteFormatMessage(newChatMessage);
        chatMessageManager.update(messageNode);
        client.refreshChat();
    }

    public String buildUwUifyString(String oldChatMessage) {
        String newChatMessage = new ChatMessageBuilder()
            .append(ChatColorType.NORMAL)
            .append(uwuify(oldChatMessage))
            .build();

        return newChatMessage;
    }

    public String uwuify(String normalText) {
        String newDialog = normalText.replace("l", "w")
                                        .replace("I ", "me")
                                        .replace("you", "u")
                                        .replace("r", "w")
                                        .replace("the", "da")
                                        .replace("this", "dis");
        return newDialog;
    }

}
