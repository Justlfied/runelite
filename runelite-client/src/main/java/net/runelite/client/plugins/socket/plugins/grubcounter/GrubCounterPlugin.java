package net.runelite.client.plugins.socket.plugins.grubcounter;

import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.raidsscouter.Raids1Util;
import net.runelite.client.plugins.socket.org.json.JSONObject;
import net.runelite.client.plugins.socket.packet.SocketBroadcastPacket;
import net.runelite.client.plugins.socket.packet.SocketReceivePacket;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.eventbus.EventBus;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@PluginDescriptor(
    name = "[J] Socket Grub Counter",
    description = "Grub Counter for socket",
    tags = {"cox", "deo", "justy"}
)

public class GrubCounterPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private EventBus eventBus;

    @Inject
    private ClientThread clientThread;

    @Inject
    private GrubCounterOverlay grubCounterOverlay;

    @Inject
    private OverlayManager overlayManager;

    private int last_grubs;
    private int num_grubs;
    private int chestOpened;
    private int chestWithGrubs;

    public Map<String, JSONObject> members = new ConcurrentHashMap();
    public Map<String, Integer> total = new ConcurrentHashMap();

    // Solely used for debugging purposes
    private int count = 0;

    @Override
    public void startUp() {
        overlayManager.add(grubCounterOverlay);
    }

    @Override
    public void shutDown() {
        overlayManager.remove(grubCounterOverlay);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configItem) {
        this.members.clear();
        this.total.clear();

    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {

        // Player is not in raid
        if(client.getVar(Varbits.IN_RAID) == 0) {
            this.members.clear();
            return;
        }

        WorldPoint wp = client.getLocalPlayer().getWorldLocation();
        int x = wp.getX() - client.getBaseX();
        int y = wp.getY() - client.getBaseY();
        int type = Raids1Util.getroom_type(client.getInstanceTemplateChunks()[client.getPlane()][x / 8][y / 8]);

        // Debug every 10 ticks (6 seconds)
        if(count % 10 == 0 ) {
            System.out.println("Socket Members: " + members);
            System.out.println("Socket Total: " + total);
        }

        // If room isnt thieving
        if(type != 13) {
            this.members.clear();
            this.total.clear();
            return;
        }

        count++;

        // ITC = Instance Template Chunks
        // Up/Down Chunk Border = +8/-8 ITC
        // Right/Left Chunk Border = +16384/-16384 ITC
        // de0 a god
        // ITC & 0x3FF3FE0 always ends up with a correct zonecode
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned gameObject) {
        GameObject object = gameObject.getGameObject();

        if (object.getId() != 29745 && object.getId() != 29743 && object.getId() != 29744) {
            return;
        }

        Point point = gameObject.getTile().getSceneLocation();
        int x = point.getX();
        int y = point.getY();

        boolean opened = false;
        boolean grub = false;
        if (object.getId() == 29744 || object.getId() == 29745) {
            opened = true;
        }
        if (object.getId() == 29745) {
            grub = true;
        }

        if(opened) {
            int angle = object.getOrientation().getAngle() >> 9;
            int px = x + ((angle == 1) ? -1 : ((angle == 3) ? 1 : 0));
            int py = y + ((angle == 0) ? -1 : ((angle == 2) ? 1 : 0));

                WorldPoint wp = client.getLocalPlayer().getWorldLocation();
                int plx = wp.getX() - this.client.getBaseX();
                int ply = wp.getY() - this.client.getBaseY();

                if (plx == px && ply == py) {
                    if (grub) {
                        addGrubs();
                    } else {
                        addEmpty();
                    }

                    /*

                    Check chest and player coordinates
                    if (plx == px && ply == py) {

                      If chest got gruns and player == localPlayer
                      if (grub && pl == this.client.getLocalPlayer()) {
                        add_grubs_local(); break;

                      If chest has only grubs
                      }  if (grub) {
                        add_grubs_other(pl); break;
                      }

                      Otherwise add empty
                      add_empty(pl);
                      break;
                    }
                     */
                }
        }


    }

    private void addGrubs() {
        int grubs = this.client.getItemContainer(InventoryID.INVENTORY)
            .count(20885);
        int delta = grubs - this.last_grubs;
        this.num_grubs += delta;
        this.last_grubs = grubs;
        chestOpened++;
        chestWithGrubs++;

        if(delta == 0) {
            return;
        }

        JSONObject data = new JSONObject();
        data.put("player", client.getLocalPlayer().getName());
        data.put("grubsReceived", delta);
        data.put("chestOpened", chestOpened);
        data.put("chestWithGrubs", chestWithGrubs);
        JSONObject payload = new JSONObject();
        payload.put("bat-counter", data);
        eventBus.post(new SocketBroadcastPacket(payload));

        return;
    }

    private void addEmpty() {
        chestOpened++;

        System.out.println("[SOCKET DATA] Playername: " + client.getLocalPlayer().getName() + " | Grubs Received: " + 0 + " | Chests Opened: " + chestOpened + " | Chests With Grubs: " + chestWithGrubs);

        JSONObject data = new JSONObject();
        data.put("player", client.getLocalPlayer().getName());
        data.put("grubsReceived", 0);
        data.put("chestOpened", chestOpened);
        data.put("chestWithGrubs", chestWithGrubs);
        JSONObject payload = new JSONObject();
        payload.put("bat-counter", data);
        eventBus.post(new SocketBroadcastPacket(payload));

        return;
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged e) {
        if (e.getContainerId() == 93) {
            this.last_grubs = e.getItemContainer().count(20885);
        }
    }

    @Subscribe
    public void onSocketReceivePacket(SocketReceivePacket packet) {
        JSONObject payload = packet.getPayload();

        if(!payload.has("bat-counter")) {
            return;
        }

        JSONObject data = payload.getJSONObject("bat-counter");

        System.out.println("Payload: " + payload);

        clientThread.invoke(() -> {
            updateBatCounterMember(data.getString("player"), data.getInt("chestOpened"), data.getInt("chestWithGrubs"), data.getInt("grubsReceived"));
        });
    }

    public void updateBatCounterMember(String player, Integer chestOpened, Integer chestWithGrubs, Integer grubsReceived) {
        JSONObject grubs = new JSONObject();
        grubs.put("chestOpened", chestOpened);
        grubs.put("chestWithGrubs", chestWithGrubs);
        members.put(player, grubs);
        total.put("total", (Integer)total.getOrDefault("total", 0) + grubsReceived);
    }

    Map<String, JSONObject> getMembers() {
        return members;
    }
    Map<String, Integer> getTotal() {
        return total;
    }
}
