package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ItemEvents implements Listener {


    public HashMap<Player, List<ItemStack>> deathDrops = new HashMap<>();


    @EventHandler
    public void drop(PlayerDropItemEvent event){

        if(event.getItemDrop().getItemStack().getItemMeta() == null)
            return;

        if(event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER))
            event.setCancelled(true);
    }


    @EventHandler
    public void click(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        if(event.getCurrentItem() == null)
            return;

        ItemStack clickedItem = event.getCurrentItem();

        if(!clickedItem.hasItemMeta())
            return;



        if(clickedItem.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER) &&(
                clickedItem.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER) == 0 ||
                clickedItem.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER) == 8)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void death(PlayerDeathEvent event){
        List<ItemStack> restoreList = new ArrayList<>();
        event.getDrops().forEach(item -> {
            if(!item.hasItemMeta())
                return;

            if(item.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER)){
                restoreList.add(item);
            }
        });

        event.getDrops().removeAll(restoreList);
        deathDrops.put(event.getEntity(), restoreList);
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event){
        if(deathDrops.get(event.getPlayer()) == null)
            return;

        deathDrops.get(event.getPlayer()).forEach(item -> {
            event.getPlayer().getInventory().setItem(rearrangeItem(item), item);
        });

        deathDrops.remove(event.getPlayer());
    }


    /**
     * Only used for bleach items. Checks the name of it and then selects a slot for it to be put in.
     * @param stack - The item given
     * @return - The slot that the item should be put in
     */
    private int rearrangeItem(ItemStack stack){
        JLogger.log(JLogSeverity.DEBUG, "" + stack.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER).intValue());
        return stack.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER).intValue();
    }
}
