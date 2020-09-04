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
                    keyName = "booleanConfig",
                    name = "BaldyLite",
                    description = "Highlight Bald People"
            )
    default boolean booleanConfig()
    {
        return true;
    }

    @ConfigItem
            (
                    position = 2,
                    keyName = "testAction",
                    name = "Test Action",
                    description = "Checkbox to Test Stuff"
            )
    default boolean testAction()
    {
        return false;
    }
}