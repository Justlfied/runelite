package net.runelite.client.plugins.overloadtimerbrew;

import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import java.awt.*;

public class OverloadTimerBrewOverlay extends WidgetItemOverlay {
    private final OverloadTimerBrewPlugin plugin;
    private final OverloadTimerBrewConfig config;

    @Inject
    public OverloadTimerBrewOverlay(OverloadTimerBrewPlugin plugin, OverloadTimerBrewConfig config) {
        showOnInventory();
        this.plugin = plugin;
        this.config = config;
    }

    @Inject
    private Client client;

    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem) {
        if(plugin.overloadTimer == 0) {
            return;
        }

        if (client.getVar(Varbits.IN_RAID) != 1)
        {
            return;
        }

        // Sara brews and Raid brews+ IDs
        if(itemId == 6685 ||
            itemId == 6687 ||
            itemId == 6689 ||
            itemId == 6691 ||
            itemId == 20984 ||
            itemId == 20983 ||
            itemId == 20982 ||
            itemId == 20981 ) {

            graphics.setFont(FontManager.getRunescapeSmallFont());

            final TextComponent textComponent = new TextComponent();
            textComponent.setPosition(new Point(widgetItem.getCanvasBounds().x - 1, widgetItem.getCanvasBounds().y + widgetItem.getCanvasBounds().height - 1));
            textComponent.setColor(plugin.overloadTimeColor);

            if(config.tickCounter()) {
                textComponent.setText(Integer.toString(plugin.overloadTimer));
            } else {
                textComponent.setText(plugin.overloadTimerTime);
            }

            textComponent.render(graphics);
        }
    }
}
