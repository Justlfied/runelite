package net.runelite.client.plugins.ravetiles;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ravetiles")
public interface RaveTilesConfig extends Config {
    @ConfigItem(
        position = 0,
        keyName = "chance",
        name = "Chance of tile to color",
        description = "[NUMBER] in 255 chance of a tile to be colored"
    )
    default int chance() {
        return 30;
    }
}
