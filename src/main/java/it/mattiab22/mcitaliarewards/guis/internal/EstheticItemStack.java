package it.mattiab22.mcitaliarewards.guis.internal;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class EstheticItemStack {

    private @Getter final ItemStack item;
    private @Getter final int slot;

    public EstheticItemStack(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
    }

}
