package it.mattiab22.mcitaliarewards.guis.internal;

import fr.minuskube.inv.SmartInventory;
import it.mattiab22.mcitaliarewards.Main;
import it.mattiab22.mcitaliarewards.guis.RewardsMenu;
import it.mattiab22.mcitaliarewards.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private final String name;
    private final int rows;
    private final List<EstheticItemStack> esthetics;
    private final List<RewardItemStack> rewards;
    private final Main instance;

    public MenuManager(Main instance) {
        this.name = instance.getConfig().getString("gui.name").replaceAll("&", "§");
        this.rows = instance.getConfig().getInt("gui.rows");
        this.instance = instance;
        this.esthetics = new ArrayList<>();
        this.rewards = new ArrayList<>();
        loadItems();
    }

    public void loadItems() {
        FileConfiguration config = instance.getConfig();
        //load esthetics
        for(String itemName : config.getConfigurationSection("gui.esthetics").getKeys(false)) {
            ItemStack item = loadItem(config, "gui.esthetics." + itemName);
            for(int i : config.getIntegerList("gui.esthetics." + itemName + ".slots")) esthetics.add(new EstheticItemStack(item.clone(), i));
        }
        //load rewards
        for(String itemName : config.getConfigurationSection("gui.rewards").getKeys(false)) {
            ItemStack item = loadItem(config, "gui.rewards." + itemName);
            String command = config.getString("gui.rewards." + itemName + ".command");
            boolean console = config.getBoolean("gui.rewards." + itemName + ".run-as-console");
            int slot = config.getInt("gui.rewards." + itemName + ".slot");
            rewards.add(new RewardItemStack(item.clone(), command, console, slot));
        }
    }

    public SmartInventory getRewardsMenu() {
        return SmartInventory.builder().id("RewardsMenu")
           .provider(new RewardsMenu(instance, esthetics, rewards))
           .size(rows, 9)
           .title(name)
           .build();
    }

    public ItemStack loadItem(FileConfiguration config, String path) {
        Material material = Material.valueOf(config.getString(path + ".material"));
        int amount = config.getInt(path + ".amount");
        int data = config.getInt(path + ".data");
        String name = config.getString(path + ".name").replaceAll("&", "§");
        List<String> lore = config.getStringList(path + ".lore");
        for(int i=0; i<lore.size(); i++) lore.set(i, lore.get(i).replaceAll("&", "§"));
        return new ItemBuilder(material, amount, (byte) data).setName(name).setLore(lore).toItemStack();
    }

}
