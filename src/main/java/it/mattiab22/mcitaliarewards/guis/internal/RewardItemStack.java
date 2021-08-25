package it.mattiab22.mcitaliarewards.guis.internal;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class RewardItemStack {

    private @Getter final ItemStack item;
    private @Getter final String command;
    private @Getter final boolean console;
    private @Getter final int slot;

    public RewardItemStack(ItemStack item, String command, boolean console, int slot) {
        this.item = item.clone();
        this.command = command;
        this.console = console;
        this.slot = slot;
    }

}
