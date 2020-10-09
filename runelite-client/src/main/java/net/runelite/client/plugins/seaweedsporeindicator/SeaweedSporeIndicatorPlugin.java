package net.runelite.client.plugins.seaweedsporeindicator;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
    name = "[J] Seaweed Spore Indicator",
    description = "Displays Seaweed Spores",
    tags = {"justy"},
    loadWhenOutdated = true
)
public class SeaweedSporeIndicatorPlugin extends Plugin {
    @Inject
    public Notifier notifiy;

    @Inject
    public Client client;

    @Subscribe
    public void onItemSpawned(ItemSpawned itemSpawned) {
        final TileItem item = itemSpawned.getItem();
        final Tile tile = itemSpawned.getTile();

        if(item.getId() == ItemID.SEAWEED_SPORE) {
            WorldPoint location = tile.getWorldLocation();
            client.setHintArrow(location);
            notifiy.notify("Seaweed Spore has spawned!");
        }
    }

    @Subscribe
    public void onItemDespawned(ItemDespawned itemDespawned) {
        final TileItem item = itemDespawned.getItem();

        if(item.getId() == ItemID.SEAWEED_SPORE) {
            client.clearHintArrow();
        }
    }

}
