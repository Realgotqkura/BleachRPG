package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Level;

public class NoCraftEvent implements Listener {

    /**
     * Prevents items from being crafted with plugin items
     */

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        for(ItemStack stack : event.getInventory().getMatrix()){
            if(stack == null)
                continue;

            if(!stack.hasItemMeta())
                continue;

            if(stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN)) {
                event.setCancelled(true);
            }
        }
    }
}
