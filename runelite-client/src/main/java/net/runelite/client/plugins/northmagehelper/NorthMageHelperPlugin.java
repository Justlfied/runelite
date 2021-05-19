package net.runelite.client.plugins.northmagehelper;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(
        name = "[J] North Mage Helper",
        description = "Highlights which crab to freeze in 5s on fast procs",
        tags = {"baldylite", "justy", "tob", "raid"}
)
public class NorthMageHelperPlugin extends Plugin {
    private static final Logger log = LoggerFactory.getLogger(NorthMageHelperPlugin.class);

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private NorthMageHelperOverlay overlay;

    @Inject
    private Client client;

    public int phaseTimer = 0;
    public int phaseCounter = 0;
    public boolean sameWave = false;
    public boolean timerStarted = false;
    public List<NPC> highlightCrabs = new ArrayList<>();

    /*
        70s Maiden = 8361
        50s Maiden = 8362
        Crabs = 8366
    */

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned) {
        int npcId = npcSpawned.getNpc().getId();

        if(npcId == 8366) {
            log.debug("Crabs Local X: " + npcSpawned.getActor().getLocalLocation().getX() + " | Crabs Local Y: " + npcSpawned.getActor().getLocalLocation().getY());
        }

        if(npcId == 8366 && phaseCounter == 0) {
            System.out.println("I am here");
            freezeList(npcSpawned, phaseTimer);
            timerStarted = true;
            sameWave = true;
            phaseCounter++;
        }

        if(npcId == 8366 && phaseTimer > 1 && phaseTimer < 19) {
            freezeList(npcSpawned, phaseTimer);
        }

        if(npcId == 8366 && phaseCounter == 1 && !sameWave) {
            timerStarted = false;
            phaseCounter++;
        }

    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        sameWave = false;
        if(timerStarted) {
            phaseTimer++;
        }
    }

    private void freezeList(NpcSpawned npc, int phaseTimer) {
        /*
           X: 5376 Back of the line
           Y: 4736 North Side of Room

           Crabs Local X: 6016 | Crabs Local Y: 8448
           Crabs Local X: 6528 | Crabs Local Y: 5632
           Crabs Local X: 6528 | Crabs Local Y: 8448
           Crabs Local X: 7552 | Crabs Local Y: 5632
           Crabs Local X: 7552 | Crabs Local Y: 8192
           Crabs Local X: 7552 | Crabs Local Y: 8448

           N1: 5888 5760
           N2: 6400 5760
           N3: 6912 5760
           N4: 7424 5760

           N1: 3840 4736
           N2: 4352 4736
           N3: 4863 4736
           N4: 5376 4736
        */

        int npcX = npc.getActor().getLocalLocation().getX();
        int npcY = npc.getActor().getLocalLocation().getY();

        System.out.println("Crabs Local X: " + npc.getActor().getLocalLocation().getX() + " | Crabs Local Y: " + npc.getActor().getLocalLocation().getY());

        if (npcY == 5760 || npcY == 4736) {
            switch(phaseTimer) {
                case 13:
                    if(npcX == 6400 || npcX == 7424 || npcX == 4352 || npcX == 5376) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                case 14:
                case 15:
                    if(npcX == 6400 || npcX == 6912 || npcX == 7424 || npcX == 4352 || npcX == 4863 || npcX == 5376) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                case 16:
                case 17:
                case 18:
                    if(npcX == 5888 || npcX == 6912 || npcX == 7424 || npcX == 3840 || npcX == 4863 || npcX == 5376) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                default:
                    System.out.println("Here Aswell Aswell!");
                    highlightCrabs.add(npc.getNpc());
            }
        }
    }

    private float phaseTimerToSeconds(int phaseTimer) {
        return (float) (phaseTimer * 0.6);
    }
}
