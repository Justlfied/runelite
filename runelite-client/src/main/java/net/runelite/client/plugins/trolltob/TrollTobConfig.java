package net.runelite.client.plugins.trolltob;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("trolltob")
public interface TrollTobConfig extends Config {
    @ConfigItem
    (
        position = 1,
        keyName = "bossAlwaysFullHealth",
        name = "Perma 100% hp bosses",
        description = "Sets the ToB default hp bar to always 100%"
    )
    default boolean bossAlwaysFullHealth() {
        return true;
    }
}
