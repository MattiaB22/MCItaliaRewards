package it.mattiab22.mcitaliarewards.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import it.mattiab22.mcitaliarewards.Main;
import it.mattiab22.mcitaliarewards.guis.internal.EstheticItemStack;
import it.mattiab22.mcitaliarewards.guis.internal.RewardItemStack;
import it.mattiab22.mcitaliarewards.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class RewardsMenu implements InventoryProvider {

    private final Main instance;
    private final List<EstheticItemStack> esthetics;
    private final List<RewardItemStack> rewards;

    public RewardsMenu(Main instance, List<EstheticItemStack> esthetics, List<RewardItemStack> rewards) {
        this.instance = instance;
        this.esthetics = esthetics;
        this.rewards = rewards;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        for(EstheticItemStack item : esthetics) inventoryContents.set(getRow(item.getSlot()), getColumn(item.getSlot()), ClickableItem.empty(item.getItem()));
        for(RewardItemStack item : rewards) {
            if(item.isConsole()) {
                inventoryContents.set(getRow(item.getSlot()), getColumn(item.getSlot()), ClickableItem.of(item.getItem(), e -> {
                    player.closeInventory();
                    if(instance.getMongoData().canRetireRewards(player.getName())) {
                        instance.getMongoData().removeReward(player.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), item.getCommand().replaceAll("%p", player.getName()));
                    }else {
                        player.sendMessage(Lang.NO_PRIZES_TO_REDEEM.get());
                    }
                }));
            }else {
                inventoryContents.set(getRow(item.getSlot()), getColumn(item.getSlot()), ClickableItem.of(item.getItem(), e -> {
                    player.closeInventory();
                    if(instance.getMongoData().canRetireRewards(player.getName())) {
                        instance.getMongoData().removeReward(player.getName());
                        player.performCommand(item.getCommand());
                    }else {
                        player.sendMessage(Lang.NO_PRIZES_TO_REDEEM.get());
                    }
                }));
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}

    public int getRow(int slot) {
        return slot/9;
    }

    public int getColumn(int slot) {
        return slot%9;
    }

}
