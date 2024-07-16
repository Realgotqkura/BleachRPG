package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.ThreadLocalRandom;

public class Blocking implements Listener {


    @EventHandler
    public void e(PlayerInteractEvent event){
        Player player = event.getPlayer();
        BleachPlayer bleachPlayer = new BleachPlayer(player);
        ItemStack stack = player.getInventory().getItemInMainHand();

        if(RandomUtils.shouldStopUsage(player))
            return;

        if(stack.getItemMeta() == null)
            return;

        if(!stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.BOOLEAN))
            return;

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            bleachPlayer.setBlocking(true);
        }else{
            bleachPlayer.setBlocking(false);
        }
    }

    @EventHandler
    public void disableBlockOnHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            BleachPlayer player = new BleachPlayer((Player) event.getDamager());
            if(!player.isHoldingABleachWeapon())
                return;

            player.setBlocking(false);

        }
    }

    @EventHandler
    public void attackWhenBlocking(EntityDamageByEntityEvent event){


        if(event.getEntity() instanceof Player){
            BleachPlayer prey = new BleachPlayer((Player) event.getEntity());

            if(event.getDamager() instanceof Player){

                if(prey.isBlocking()){
                    event.setDamage(0);
                }
            }else{

                if(prey.isBlocking()){
                    int random = ThreadLocalRandom.current().nextInt(0,5+1);
                    event.setDamage(event.getDamage() / 2);

                    if(random == 0){
                        prey.breakBlock();
                    }
                }
            }

        }
    }

}
