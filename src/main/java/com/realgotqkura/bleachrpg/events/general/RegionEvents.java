package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.Debug.DebugUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.logging.Level;

public class RegionEvents implements Listener {
    @EventHandler
    public void inter(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        BleachRegion region = new BleachRegion(player);

        if(event.getHand() != EquipmentSlot.HAND)
            return;

        if(stack == null)
            return;

        if(!stack.hasItemMeta())
            return;

        if(!stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.STRING))
            return;

        String tag = stack.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.STRING).toString();

        if(!tag.equals("bleachRegions"))
            return;

        if(event.getAction() == Action.LEFT_CLICK_BLOCK){
            region.setLocation(0, event.getClickedBlock().getLocation());
            player.sendMessage(RandomUtils.color("&5Changed the first location to: &r" + RandomUtils.locationToString(event.getClickedBlock().getLocation())));
            event.setCancelled(true);
        }else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            region.setLocation(1, event.getClickedBlock().getLocation());
            player.sendMessage(RandomUtils.color("&5Changed the second location to: &r" + RandomUtils.locationToString(event.getClickedBlock().getLocation())));
        }

        new BukkitRunnable(){

            @Override
            public void run() {
                player.getInventory().setItemInMainHand(BleachItems.bleachRegions(player));
            }

        }.runTaskLater(BleachRPG.instance, 1);
    }


    @EventHandler
    public void bBreak(BlockBreakEvent event){
        FileConfiguration config = BleachRPG.instance.getConfig();
        if(config.get("Arena.firstLocation") == null)
            return;

        Location firstLoc = config.getLocation("Arena.firstLocation");
        Location secondLoc = config.getLocation("Arena.secondLocation");

        if(RandomUtils.isInside(event.getBlock().getLocation(), firstLoc, secondLoc) && !DebugUtils.TEST_MODE)
            event.setCancelled(true);
    }

    @EventHandler
    public void bPlace(BlockPlaceEvent event){
        FileConfiguration config = BleachRPG.instance.getConfig();
        if(config.get("Arena.firstLocation") == null)
            return;

        Location firstLoc = config.getLocation("Arena.firstLocation");
        Location secondLoc = config.getLocation("Arena.secondLocation");

        if(RandomUtils.isInside(event.getBlock().getLocation(), firstLoc, secondLoc) && !DebugUtils.TEST_MODE)
            event.setCancelled(true);
    }


}
