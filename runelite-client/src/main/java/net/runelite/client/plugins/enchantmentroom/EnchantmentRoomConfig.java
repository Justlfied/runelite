package net.runelite.client.plugins.enchantmentroom;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("enchantmentroom")
public interface EnchantmentRoomConfig extends Config {
    @ConfigItem(
        position = 1,
        name = "Notify on",
        keyName = "notifySetting",
        description = "Shape to notifiy on"
    )
    default EnchantmenRoomShapes notifySetting() {
        return EnchantmenRoomShapes.PENTAMID;
    }

}
