package it.mattiab22.mcitaliarewards;

import it.mattiab22.mcitaliarewards.commands.RewardsCmd;
import it.mattiab22.mcitaliarewards.database.MongoData;
import it.mattiab22.mcitaliarewards.guis.internal.MenuManager;
import it.mattiab22.mcitaliarewards.utils.Lang;
import it.mattiab22.mcitaliarewards.votes.TaskChecker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    private @Getter MongoData mongoData;
    private @Getter TaskChecker taskChecker;
    private @Getter MenuManager menuManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        mongoData = new MongoData(this);
        taskChecker = new TaskChecker(this);
        menuManager = new MenuManager(this);
        taskChecker.start();
        setupCommands();
        setupLang();
    }

    @Override
    public void onDisable() {
        mongoData.close();
        taskChecker.interrupt();
    }

    public void setupCommands() {
        getCommand("rewards").setExecutor(new RewardsCmd(this));
    }

    public void setupLang(){
        for(Lang lang : Lang.values()){
            List<String> list = this.getConfig().getStringList("lang." + lang.getPath());
            String[] msg;
            if(list.size() == 0) {
                msg = new String[1];
                msg[0] = this.getConfig().getString("lang." + lang.getPath()).replaceAll("&", "§");
            }else {
                msg = list.toArray(new String[0]);
                for(int i=0; i<msg.length; i++){
                    msg[i] = msg[i].replaceAll("&", "§");
                }
            }
            lang.setMessage(msg);
        }
    }

}
