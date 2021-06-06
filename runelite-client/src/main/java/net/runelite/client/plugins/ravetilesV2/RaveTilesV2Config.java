package net.runelite.client.plugins.ravetilesV2;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ravetilesV2")
public interface RaveTilesV2Config extends Config {
    @ConfigItem(
            position = 0,
            keyName = "radius",
            name = "Radius",
            description = "Radius of tiles to rave"
    )
    default int radius() {
        return 2;
    }

    @ConfigItem(
            position = 1,
            keyName = "chance",
            name = "Chance of tile to color",
            description = "[NUMBER] in 100 chance of a tile to be colored"
    )
    default int chance() {
        return 30;
    }

    @ConfigItem(
            position = 2,
            keyName = "opacity",
            name = "Random Opacity",
            description = "Randomizes Tile Opacity"
    )
    default boolean opacity() {
        return true;
    }
}
