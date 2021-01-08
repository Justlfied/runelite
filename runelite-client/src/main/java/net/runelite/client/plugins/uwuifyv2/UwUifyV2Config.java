package net.runelite.client.plugins.uwuifyv2;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("uwuifyv2")
public interface UwUifyV2Config extends Config {
    @ConfigItem
        (
            position = 1,
            name = "NPC Dialog",
            keyName = "changeNpcDialog",
            description = "UwUify Chatbox Dialog of NPC's"
        )
    default boolean changeNpcDialog() {
        return true;
    }

    @ConfigItem
        (
            position = 2,
            name = "Game Messages",
            keyName = "changeChatboxGameMessage",
            description = "UwUify Game Messages"
        )
    default boolean changeChatboxGameMessage() {
        return false;
    }

    @ConfigItem
        (
            position = 3,
            name = "Private Messages",
            keyName = "changeChatboxPrivateMessage",
            description = "UwUify Private Messages"
        )
    default boolean changeChatboxPrivateMessage() {
        return false;
    }

    @ConfigItem
        (
            position = 4,
            name = "Public Messages",
            keyName = "changeChatboxPublicMessage",
            description = "UwUify Public Messages"
        )
    default boolean changeChatboxPublicMessage()
    {
        return false;
    }

    @ConfigItem(
        position = 5,
        name = "Clan Messages",
        keyName = "changeChatboxClanMessage",
        description = "UwUify Clan Messages"
    )
    default boolean changeChatboxClanMessage()
    {
        return false;
    }

    @ConfigItem(
        position = 6,
        name = "Test",
        keyName = "test",
        description = "test"
    )
    default boolean test() {
        return false;
    }

    @ConfigItem(
        position = 7,
        name = "Test Color",
        keyName = "testColor",
        description = "The color the textbox chat will hopefully be!"
    )
    default Color testColor() {
        return Color.RED;
    }
}
