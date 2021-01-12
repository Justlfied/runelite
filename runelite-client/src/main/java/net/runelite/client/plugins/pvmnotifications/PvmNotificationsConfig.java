package net.runelite.client.plugins.pvmnotifications;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("pvmnotifications")
public interface PvmNotificationsConfig extends Config {
    @ConfigItem(
        position = 1,
        name = "Dawn P1",
        description = "Grotesque Guardian Dawn P1",
        keyName = "dawnP1"
    )

    default boolean dawnP1() {
        return true;
    }

    @ConfigItem(
        position = 2,
        name = "Dawn P2",
        description = "Grotesque Guardian Dawn P2",
        keyName = "dawnP2"
    )

    default boolean dawnP2() {
        return true;
    }

    @ConfigItem(
        position = 3,
        name = "Cerberus Ghosts",
        description = "Cerberus Ghost spawns",
        keyName = "cerbGhost"
    )

    default boolean cerbGhost() {
        return true;
    }
}
