package it.mattiab22.mcitaliarewards.votes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.mattiab22.mcitaliarewards.Main;
import it.mattiab22.mcitaliarewards.database.MongoData;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TaskChecker extends Thread {

    private final MongoData mongoData;
    private final Main instance;
    private final String link;

    public TaskChecker(Main instance) {
        this.mongoData = instance.getMongoData();
        this.instance = instance;
        this.link = "https://api.minecraft-italia.it/v5/server-info/" + instance.getConfig().getString("server-info.name") + "/?key=" + instance.getConfig().getString("server-info.key");
    }

    @SneakyThrows
    @Override
    public void run() {
        while(!isInterrupted()) {
            SimpleDateFormat updateDate = new SimpleDateFormat("dd/MM/yyyy");
            updateDate.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
            String date = updateDate.format(new Date());
            if(!date.equalsIgnoreCase(instance.getConfig().getString("server-info.last-update"))) {
                URL url = new URL(link);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                JsonObject mcitalia = new JsonParser().parse(in).getAsJsonObject();
                JsonArray votes = mcitalia.get("userVotes").getAsJsonArray();
                for (JsonElement vote : votes) {
                    if (!vote.getAsJsonObject().get("minecraftNickname").isJsonNull()) {
                        String nickname = vote.getAsJsonObject().get("minecraftNickname").getAsString();
                        if (mongoData.isPresent(nickname)) {
                            mongoData.addReward(nickname);
                        } else {
                            mongoData.addNewPlayer(nickname, 1);
                        }
                    } else {
                        Bukkit.getLogger().log(Level.WARNING, "Found player who voted without connecting minecraft --> DISCORD USERNAME: " + vote.getAsJsonObject().get("discordUsername").getAsString());
                    }
                }
                instance.getConfig().set("server-info.last-update", date);
                instance.saveConfig();
                instance.reloadConfig();
            }
            Thread.sleep(getDelay());
        }
    }

    public long getDelay() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextDay = now.withHour(0).withMinute(10).withSecond(0);
        if (now.compareTo(nextDay) > 0) nextDay = nextDay.plusDays(1);
        long seconds = Duration.between(now, nextDay).getSeconds();
        return TimeUnit.SECONDS.toMillis(seconds);
    }
}
