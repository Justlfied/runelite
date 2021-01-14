package net.runelite.client.plugins.discordraredropnotifier;

import lombok.SneakyThrows;
import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
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
    private static final String API_PATH_ITEMS = "items";

    private OkHttpClient httpClient = null;
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    @Inject
    private Client client;

    @Subscribe
    public void onNpcLootReceived(NpcLootReceived npcLootReceived) {
        // Function to check if the npc thats being killed is something that can drop loot

        npcLootReceived.getItems().forEach((itemStack) -> {
            Integer itemID = itemStack.getId();

            // Bones ItemID
            if(itemID == 526) {
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
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            String responseBody = response.body().string();

                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray dropsArray = jsonObject.getJSONArray("drops");

                            for(Integer i = 0; i < jsonObject.getJSONArray("drops").length(); i++) {
                                JSONObject object = dropsArray.getJSONObject(i);

                                if(object.getInt("id") == itemID) {
                                    DecimalFormat df = new DecimalFormat("#.000");
                                    String webhookRarityField = " 1/" + 1/object.getDouble("rarity") + " (" + df.format(object.getDouble("rarity"))  + "%)";
                                    String webhookDescription = "Just got " + itemStack.getQuantity() + "x "
                                        + object.getString("name")
                                        + " from lvl " + jsonObject.getInt("combat_level")
                                        + " " + jsonObject.getString("name");

                                    DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/791336920365006860/dXBe90zwHfMWDB9idrZywz7tuSSkpSeWDket0mQAFKZatQM13aNagfnRqmUCaVKuU1oN");
                                    webhook.setUsername("Gains");
                                    webhook.setTts(false);
                                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                        .setDescription(webhookDescription)
                                        .addField("Rarity", "```#" + webhookRarityField + "```", true)
                                        .addField("HA Value", "idfk", true)
                                        .addField("GE Value", "idfk either", true)
                                        .setAuthor(client.getLocalPlayer().getName(), "", ""));
                                    webhook.execute();
                                }
                            }
                            response.close();
                        }
                    }
                });
            }
        });
    }
}
