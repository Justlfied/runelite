package net.runelite.client.plugins.uwuify;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("uwuify")
public interface UwuifyConfig extends Config
{
    @ConfigItem
    (
        position = 4,
        name = "NPC Dialog",
        keyName = "changeNpcDialog",
        description = "UwUify Chatbox Dialog of NPC's"
    )
    default boolean changeNpcDialog() {
        return true;
    }

    @ConfigItem
    (
        position = 1,
        name = "Game Messages",
        keyName = "changeChatboxGameMessage",
        description = "UwUify Game Messages"
    )
    default boolean changeChatboxGameMessage() {
        return false;
    }

    @ConfigItem
    (
        position = 2,
        name = "Private Messages",
        keyName = "changeChatboxPrivateMessage",
        description = "UwUify Private Messages"
    )
    default boolean changeChatboxPrivateMessage() {
        return false;
    }

    @ConfigItem
    (
        position = 3,
        name = "Public Messages",
        keyName = "changeChatboxPublicMessage",
        description = "UwUify Public Messages"
    )
    default boolean changeChatboxPublicMessage()
    {
        return false;
    }

    @ConfigItem(
        position = 6,
        name = "Clan Messages",
        keyName = "changeChatboxClanMessage",
        description = "UwUify Clan Messages"
    )
    default boolean changeChatboxClanMessage()
    {
        return false;
    }
}
