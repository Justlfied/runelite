package net.runelite.client.plugins.baldylite;

// Mandatory imports
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("baldylite")
public interface BaldyLiteConfig extends Config
{
    @ConfigItem
    (
        position = 1,
        keyName = "showHairStyle",
        name = "Show Hairstyle",
        description = "Shows if you're bald or not"
    )
    default boolean showHairStyle()
    {
        return true;
    }

    @ConfigItem
    (
        position = 2,
        keyName = "drawHairStyle",
        name = "    Draw baldness <hr>",
        description = "Draws an overlay telling you if youre bald or not"
    )
    default boolean drawHairStyle()
    {
        return true;
    }

    @ConfigItem
    (
        position = 3,
        keyName = "testAction",
        name = "Test Action",
        description = "Checkbox to Test Stuff"
    )
    default boolean testAction()
    {
        return false;
    }
}