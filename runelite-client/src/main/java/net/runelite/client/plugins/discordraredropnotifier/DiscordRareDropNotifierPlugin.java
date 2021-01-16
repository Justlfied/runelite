package net.runelite.client.plugins.discordraredropnotifier;

import com.google.inject.Provides;
import lombok.SneakyThrows;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import javax.inject.Inject;

import net.runelite.client.plugins.socket.org.json.JSONArray;
import net.runelite.client.plugins.socket.org.json.JSONObject;
import okhttp3.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

@PluginDescriptor(
    name = "[J] Discord Rare Drop Notifier",
    description = "Working Discord Loot Notifier for BlueLite",
    tags = {"loot", "pvm", "justy"}
)
public class DiscordRareDropNotifierPlugin extends Plugin {

    private static final String API_ROOT = "api.osrsbox.com";
    private static final String API_PATH_NPCS = "monsters";

    private OkHttpClient httpClient = null;

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private DiscordRareDropNotifierConfig config;

    @Provides
    DiscordRareDropNotifierConfig config(ConfigManager configManager) {
        return configManager.getConfig(DiscordRareDropNotifierConfig.class);
    }

    @Subscribe
    public void onNpcLootReceived(NpcLootReceived npcLootReceived) {
        if(config.webhookUrl() == null) {
            return;
        }

        npcLootReceived.getItems().forEach((itemStack) -> {
            Integer itemID = itemStack.getId();

            // Bones ItemID
            if(isRareDrop(itemID)) {
                Integer npcID = npcLootReceived.getNpc().getId();

                HttpUrl url = new HttpUrl.Builder().scheme("https").host(API_ROOT).addPathSegment(API_PATH_NPCS)
                    .addPathSegment("" + npcID).build();

                Request request = new Request.Builder().url(url).build();

                if(request == null) {
                    return;
                }

                httpClient = new OkHttpClient();

                httpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }

                    @SneakyThrows
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()) {
                            String responseBody = response.body().string();

                            net.runelite.client.plugins.socket.org.json.JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray dropsArray = jsonObject.getJSONArray("drops");

                            for(Integer i = 0; i < jsonObject.getJSONArray("drops").length(); i++) {
                                JSONObject object = dropsArray.getJSONObject(i);

                                if(object.getInt("id") == itemID) {
                                    DecimalFormat df = new DecimalFormat("#.000");
                                    String webhookRarityField = " 1/" + 1/object.getDouble("rarity") + " (" + df.format(100 * object.getDouble("rarity"))  + "%)";
                                    String webhookDescription = "Just got " + itemStack.getQuantity() + "x "
                                        + object.getString("name")
                                        + " from lvl " + jsonObject.getInt("combat_level")
                                        + " " + jsonObject.getString("name");

                                    clientThread.invokeLater(() -> {
                                        try {
                                            final int gePrice = itemManager.getItemPrice(itemID);
                                            final int haPrice = itemManager.getItemComposition(itemID).getHaPrice();

                                            DiscordWebhook webhook = new DiscordWebhook(config.webhookUrl());
                                            webhook.setUsername("Justy");
                                            webhook.setTts(false);
                                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                                .setDescription(webhookDescription)
                                                .addField("Rarity", "```#" + webhookRarityField + "```", true)
                                                .addField("HA Value", "```" + haPrice + "```", true)
                                                .addField("GE Value", "```" + gePrice + "```", true)
                                                .setAuthor(client.getLocalPlayer().getName(), "", ""));
                                            webhook.execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    return;
                                }
                            }
                            response.close();
                        }
                    }
                });
            }
        });
    }

    private Boolean isRareDrop(Integer itemID) {
        for(NotifiyItems items : NotifiyItems.values()) {
            return items.getId() == itemID;
        }

        return false;
    }
}
