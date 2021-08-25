package it.mattiab22.mcitaliarewards.database;

import com.mongodb.*;
import it.mattiab22.mcitaliarewards.Main;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;

public class MongoData {

    private @Getter DBCollection collection;
    private @Getter DB mongoDb;
    private @Getter MongoClient client;

    public MongoData(Main instance){
        initialize(instance);
    }

    @SneakyThrows
    public void close() {
        client.close();
    }

    public void initialize(Main instance){
        try {
            if(instance.getConfig().getBoolean("mongodb.authentication") == true) {
                client = new MongoClient(new ServerAddress(instance.getConfig().getString("mongodb.address"), instance.getConfig().getInt("mongodb.port")), Arrays.asList(MongoCredential.createCredential(instance.getConfig().getString("mongodb.username"), instance.getConfig().getString("mongodb.database"), instance.getConfig().getString("mongodb.password").toCharArray())));
            }else {
                client = new MongoClient(new ServerAddress(instance.getConfig().getString("mongodb.address"), instance.getConfig().getInt("mongodb.port")));
            }
        } catch (UnknownHostException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[MCItaliaRewards] Error while connecting to mongodb, check your server credentials.", e);
        }
        mongoDb = client.getDB(instance.getConfig().getString("mongodb.database"));
        collection = mongoDb.getCollection(instance.getConfig().getString("mongodb.collection"));
    }

    public boolean isPresent(String nickname) {
        DBObject object = collection.findOne(new BasicDBObject("name", nickname));
        if(object != null) return true;
        return false;
    }

    public int getRewards(String nickname) {
        DBObject object = collection.findOne(new BasicDBObject("name", nickname));
        if(object != null) {
            return (int) object.get("rewards");
        }else {
            return 0;
        }
    }

    public void addReward(String nickname) {
        DBObject object = collection.findOne(new BasicDBObject("name", nickname));
        if(object != null) {
            DBObject update = new BasicDBObject("name", nickname);
            update.put("rewards", ((int) object.get("rewards") + 1));
            collection.update(object, update);
        }
    }

    public void removeReward(String nickname) {
        DBObject object = collection.findOne(new BasicDBObject("name", nickname));
        if(object != null) {
            DBObject update = new BasicDBObject("name", nickname);
            update.put("rewards", ((int) object.get("rewards") - 1));
            collection.update(object, update);
        }
    }

    public void addNewPlayer(String nickname, int rewards) {
        DBObject object = new BasicDBObject("name", nickname);
        object.put("rewards", rewards);
        collection.insert(object);
    }

    public boolean canRetireRewards(String nickname) {
        DBObject object = collection.findOne(new BasicDBObject("name", nickname));
        if(object != null) {
            return (int) object.get("rewards") > 0;
        }
        return false;
    }

}
