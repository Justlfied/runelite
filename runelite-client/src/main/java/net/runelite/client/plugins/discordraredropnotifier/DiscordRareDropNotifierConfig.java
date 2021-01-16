package net.runelite.client.plugins.discordraredropnotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("discordraredropnotifier")
public interface DiscordRareDropNotifierConfig extends Config {
    @ConfigItem(
        position = 1,
        name = "Webhook URL",
        keyName = "webhookUrl",
        description = "Webhook URL for discord channel"
    )
    default String webhookUrl() {
        return "";
    }
}
