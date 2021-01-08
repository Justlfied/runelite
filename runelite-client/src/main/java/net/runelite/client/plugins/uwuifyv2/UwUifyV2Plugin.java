package net.runelite.client.plugins.uwuifyv2;

import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
    name = "[J] UwUify V2",
    description = "Improved UwUifier",
    tags = {"uwu", "chat", "fun"}
)
public class UwUifyV2Plugin extends Plugin {

    @Inject
    private ClientThread clientThread;

    @Inject
    private UwUifyV2Config config;

    @Inject
    private Client client;

    @Inject
    private ChatMessageManager chatMessageManager;

    @Provides
    UwUifyV2Config getConfig(ConfigManager configManager) {
        return configManager.getConfig(UwUifyV2Config.class);
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        String newChatMessage;
        if ((chatMessage.getType() == ChatMessageType.PUBLICCHAT && config.changeChatboxPublicMessage())
            || (chatMessage.getType() == ChatMessageType.GAMEMESSAGE && config.changeChatboxGameMessage())
            || ((chatMessage.getType() == ChatMessageType.PRIVATECHAT && config.changeChatboxPrivateMessage()) || (chatMessage.getType() == ChatMessageType.PRIVATECHATOUT && config.changeChatboxPrivateMessage()))
            || (chatMessage.getType() == ChatMessageType.FRIENDSCHAT && config.changeChatboxClanMessage())) {

            newChatMessage = buildUwuifyString(chatMessage);
        } else {
            return;
        }

        final MessageNode messageNode = chatMessage.getMessageNode();
        messageNode.setRuneLiteFormatMessage(newChatMessage);
        chatMessageManager.update(messageNode);
        client.refreshChat();
    }

    public String uwuify(String normalText) {
        String newDialog = normalText
            .replace("l", "w")
            .replace("I ", "me")
            .replace("you", "u")
            .replace("r", "w")
            .replace("the", "da")
            .replace("this", "dis");
        return newDialog;
    }

    public String buildUwuifyString(ChatMessage chatMessage) {
        String testChatMessage = chatMessage.getMessage().replace("<col=", "◄").replace("</col>", "►");
        String uwuChatMessage = uwuify(testChatMessage);

        String uwuifiedMessage = uwuChatMessage.replace("◄", "<col=").replace("►", "</col>");

        String newChatMessage = new ChatMessageBuilder()
            .append(uwuifiedMessage)
            .build();

        return newChatMessage.replace("<lt>", "<").replace("<gt>", ">");
    }
}
