package net.runelite.client.plugins.uwuify;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("uwuify")
public interface UwuifyConfig extends Config
{
    @ConfigSection
    (
        name = "UwUify Chatbox",
        description = "Toggle which messages in chatbox should get Uwuified",
        position = 99
    )
    String highlightSection = "section";

    @ConfigItem
    (
        position = 4,
        name = "UwUify NPC Dialog",
        keyName = "changeNpcDialog",
        description = "UwUify Chatbox Dialog of NPC's"
    )
    default boolean changeNpcDialog() {
        return true;
    }

    @ConfigItem
    (
        position = 5,
        name = "UwUIify Chatbox Messages",
        keyName = "changeChatboxMessage",
        description = "UwUify Chatbox Messages"
    )
    default boolean changeChatboxMessage() {
        return false;
    }

    @ConfigItem
    (
        position = 1,
        name = "UwUify Game Messages",
        keyName = "changeChatboxGameMessage",
        description = "UwUify Game Messages",
        section = "highlightSection"
    )
    default boolean changeChatboxGameMessage() {
        return false;
    }

    @ConfigItem
    (
        position = 2,
        name = "UwUify Private Messages",
        keyName = "changeChatboxPrivateMessage",
        description = "UwUify Private Messages",
        section = "highlightSection"
    )
    default boolean changeChatboxPrivateMessage() {
        return false;
    }

    @ConfigItem
    (
        position = 3,
        name = "UwUify Public Messages",
        keyName = "changeChatboxPublicMessage",
        description = "UwUify Public Messages",
        section = "highlightSection"
    )
    default boolean changeChatboxPublicMessage()
    {
        return false;
    }

    @ConfigItem(
        position = 6,
        name = "UwUify Clan Messages",
        keyName = "changeChatboxClanMessage",
        description = "UwUify Clan Messages",
        section = "highlightSection"
    )
    default boolean changeChatboxClanMessage()
    {
        return false;
    }
}
