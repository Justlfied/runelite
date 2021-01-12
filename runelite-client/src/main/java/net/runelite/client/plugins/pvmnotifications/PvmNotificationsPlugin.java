package net.runelite.client.plugins.pvmnotifications;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
    name = "[J] PvM Notifications",
    description = "Different notifications for certain bosses",
    tags = {"justy", "baldy", "pvm", "notification"},
    loadWhenOutdated = true
)
public class PvmNotificationsPlugin extends Plugin {
    @Inject
    public Notifier notify;

    @Inject
    public Client client;

    @Inject
    public PvmNotificationsConfig config;

    private int npcID;

    @Provides
    PvmNotificationsConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(PvmNotificationsConfig.class);
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned npcDespawned) {

    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned) {
        npcID = npcSpawned.getNpc().getId();
        
        if((npcID == NpcID.SUMMONED_SOUL
            || npcID == NpcID.SUMMONED_SOUL_5868
            || npcID == NpcID.SUMMONED_SOUL_5869)
            && config.cerbGhost()) {
            notify.notify("Ghosts have spawned!");
        }
    }

    @Subscribe
    public void onNpcChanged(NpcChanged npcChanged) {
        npcID = npcChanged.getNpc().getId();
        
        if(npcChanged.getNpc().getId() == NpcID.DAWN_7853 && config.dawnP1()) {
            notify.notify("Dawn P1 has despawned!");
        }

        if(npcChanged.getNpc().getId() == NpcID.DAWN_7885 && config.dawnP2()) {
            notify.notify("Dawn P2 has despawned!");
        }
    }

    @Subscribe
    public void onProjectileMoved(ProjectileMoved projectileMoved) {
        Projectile projectile = projectileMoved.getProjectile();
        Integer projectileID = projectile.getId();
    }
}
