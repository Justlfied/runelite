package net.runelite.client.plugins.northmagehelper;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.widgets.Widget;
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
    public boolean sameWave = false;
    public boolean timerStarted = false;
    public List<NPC> highlightCrabs = new ArrayList<>();
    public boolean maidenP2 = false;
    public int n1X = 0;
    public int n1Y = 0;
    private boolean checkedX, checkedY;
    public List<NPC> checkNPCCoords = new ArrayList<>();

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

        if(npcId == 8366 && !maidenP2) {
            checkNPCCoords.add(npcSpawned.getNpc());
            timerStarted = true;
        }

        if(npcId == 8366 && maidenP2) {
            freezeList(npcSpawned, phaseTimer);
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned npcDespawned) {
        if(npcDespawned.getNpc().getId() == 8365) {
            highlightCrabs.clear();
            phaseTimer = 0;
            timerStarted = false;
        }
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        Widget bossHpValue = client.getWidget(28, 37);

        if(bossHpValue != null && !bossHpValue.isHidden()) {
            if(Float.parseFloat(bossHpValue.getText().substring(0, bossHpValue.getText().length() - 1)) < 60 && Float.parseFloat(bossHpValue.getText().substring(0, bossHpValue.getText().length() - 1)) > 40) {
                maidenP2 = true;
            } else {
                maidenP2 = false;
            }
        }

        if(checkNPCCoords.size() > 0 && !checkedX && !checkedY) {
            setN1X(checkNPCCoords);
            setN1Y(checkNPCCoords);
        }

        sameWave = false;
        if(timerStarted) {
            phaseTimer++;
        }
    }

    private void setN1X(List<NPC> crabs) {
        for (NPC crab : crabs) {
            if(n1X == 0) {
                n1X = crab.getLocalLocation().getX();
            }

            if(crab.getLocalLocation().getX() < n1X) {
                n1X = crab.getLocalLocation().getX();
            }
        }
        checkedX = true;
    }

    private void setN1Y(List<NPC> crabs) {
        for (NPC crab : crabs) {
            if(n1Y == 0) {
                n1Y = crab.getLocalLocation().getY();
            }

            if(crab.getLocalLocation().getY() > n1Y) {
                n1Y = crab.getLocalLocation().getY();
            }
        }
        checkedY = true;
    }

    private void freezeList(NpcSpawned npc, int phaseTimer) {
        System.out.println(phaseTimer + " PhaseTimer");
        int n2x = n1X + 512;
        int n3x = n1X + (512 * 2);
        int n4x = n1X + (512 * 3);
        int scuffedY = n1Y - 128;

        int npcX = npc.getActor().getLocalLocation().getX();
        int npcY = npc.getActor().getLocalLocation().getY();

        if (npcY == n1Y || npcY == scuffedY) {
//            if(phaseTimer == 13) {
//                if(npcX == n2x || npcX == n4x) {
//                    highlightCrabs.add(npc.getNpc());
//                }
//            } else if(phaseTimer < 16) {
//                if(npcX == n2x || npcX == n3x || npcX == n4x) {
//                    highlightCrabs.add(npc.getNpc());
//                }
//            } else if(phaseTimer < 19) {
//                if(npcX == n1X || npcX == n3x ||  npcX == n4x) {
//                    highlightCrabs.add(npc.getNpc());
//                }
//            } else {
//                highlightCrabs.add(npc.getNpc());
//            }

            switch(phaseTimer) {
                case 13:
                    if(npcX == n2x || npcX == n4x) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                case 14:
                case 15:
                    if(npcX == n2x || npcX == n3x || npcX == n4x) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                case 16:
                case 17:
                case 18:
                    if(npcX == n1X || npcX == n3x || npcX == n4x) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    break;
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                    if(npcX == n1X || npcX == n3x || npcX == n4x) {
                        highlightCrabs.add(npc.getNpc());
                    }
                    System.out.println("NpcX: " + npcX + " | N1x: " + n1X + " | N2x: " + n2x + " | N3x: " + n3x + " | N4x: " + n4x);
                    break;
                default:
                    highlightCrabs.add(npc.getNpc());
            }
        }
    }

    private float phaseTimerToSeconds(int phaseTimer) {
        return (float) (phaseTimer * 0.6);
    }
}
