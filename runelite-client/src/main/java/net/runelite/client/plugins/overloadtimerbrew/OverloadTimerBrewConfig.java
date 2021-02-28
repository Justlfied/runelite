package net.runelite.client.plugins.overloadtimerbrew;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("overloadtimerbrew")
public interface OverloadTimerBrewConfig extends Config {
    @ConfigItem(
        position = 1,
        keyName = "tickCounter",
        name = "Tick Counter",
        description = "Shows ticks instead of semi real time"
    )
    default boolean tickCounter() {
        return false;
    };

    @ConfigItem(
        position = 2,
        keyName = "tickOverlay",
        name = "Tickcounter Overlay",
        description = "Shows very aggresive looking overlay of ticks until brew"
    )
    default boolean tickOverlay() {
        return false;
    }
}
