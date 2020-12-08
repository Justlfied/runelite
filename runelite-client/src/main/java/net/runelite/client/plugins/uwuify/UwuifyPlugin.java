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
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // Exceptions
        if(ChatMessageType.FRIENDSCHATNOTIFICATION == chatMessage.getType()
            || chatMessage.getMessage().toLowerCase().contains("stalled wave") // xz_tob stalled wave
            || chatMessage.getMessage().toLowerCase().contains("<col=b4281e")  // socket leave
            || chatMessage.getMessage().toLowerCase().contains("<col=008000")  // socket join
            || chatMessage.getMessage().toLowerCase().contains("<col=222222")) // Sotetseg maze splits
        {
            return;
        }

        String newChatMessage = "";

        // Check is chat message contains red text or EF1020 (ToB splits color)
        if(chatMessage.getMessage().toLowerCase().contains("<col=ff0000>") || chatMessage.getMessage().toLowerCase().contains("<col=ef1020")) {
            newChatMessage = getColoredTextType(chatMessage.getMessage());

            final MessageNode messageNode = chatMessage.getMessageNode();
            messageNode.setRuneLiteFormatMessage(newChatMessage);
            chatMessageManager.update(messageNode);
            client.refreshChat();

            return;
        }

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

    public String getColoredTextType(String chatMessage) {
        if(chatMessage.contains("kill count:")) {
            //killcountUwUify(chatMessage);
        } else if(chatMessage.contains("Wave '")) {
            return tobRoomUwUify(chatMessage);
        }

        return "";
    }

    public String tobRoomUwUify(String chatMessage) {
        String noTagsChatMessage = chatMessage.replaceAll("<col=ff0000>", "").replaceAll("</col>", "");

        String pattern = "(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(noTagsChatMessage);

        String[] regexGroup = new String[10];
        Integer regexGroupIndex = 0;

        // Loop through matches an assigned them to a variable
        while(m.find()) {
            regexGroupIndex++;
            regexGroup[regexGroupIndex] = m.group();
        }

        // Cut the room finished string into pieces
        String beforeTime = StringUtils.substringBefore(noTagsChatMessage, regexGroup[1]);
        String uwuifiedBeforeTime = uwuify(beforeTime);

        // Make new chat message
        String newChatMessage = uwuifiedBeforeTime +
            "<col=ff0000>" +
            regexGroup[1] + "</col>" +
            " Totaw: " +
            "<col=ff0000>" + regexGroup[2] + "</col>.";

        return newChatMessage;
    }
}
