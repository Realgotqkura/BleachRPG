package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DashAndFS implements Listener {



    private Map<UUID, Double> cooldowns = new HashMap<>();
    private Map<Player, Vector> movementAngle = new HashMap<>();
    private BleachRPG plugin;

    public DashAndFS(BleachRPG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void move(PlayerMoveEvent event){

        for(Player player : Bukkit.getOnlinePlayers()){
            Vector angle = event.getFrom().subtract(event.getTo()).toVector().normalize();
            movementAngle.put(player, angle);
        }
    }
    @EventHandler
    public void click(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        BleachPlayer bleachPlayer = new BleachPlayer(player);
        ItemStack mainHand = event.getItemDrop().getItemStack();


        if(RandomUtils.shouldStopUsage(player))
            return;

        if(mainHand == null)
            return;

        if(mainHand.getItemMeta() == null)
            return;

        if(!mainHand.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.BOOLEAN))
            return;


        event.setCancelled(true);

        double cooldownDuration = plugin.getConfig().getDouble("flashStepCD") - bleachPlayer.getFlashstepCDReduct();
        int reiatsuCost = plugin.getConfig().getInt("flashStepReiatsuCost") - bleachPlayer.getFlashStepReductionCost();


        //Will become more complicated later now its just a basic dash

        if(BleachUtils.getReiatsu(player) < reiatsuCost){
            player.sendMessage(LangUtils.getMessage("NotEnoughReiatsu"));
            return;
        }
        if(checkCooldown(player, cooldownDuration)){
            if(!bleachPlayer.isBlocking() && bleachPlayer.getSpiritualLevel() >= 1){
                Vector dashDir = movementAngle.get(player);
                player.setVelocity(dashDir.setY(0).multiply(-1.7f - bleachPlayer.getFlashstepPowerIncrease())); //<-- Negative because its reversed
                BleachUtils.setReiatsu(player, BleachUtils.getReiatsu(player) - reiatsuCost);
                SoundAndEffectsUtils.playSound(player.getLocation(), Sound.ENTITY_GENERIC_SMALL_FALL, 0.5f);
                SoundAndEffectsUtils.makeParticlePrecise(player.getLocation(), Particle.EXPLOSION_NORMAL, 4);
            }
        }

    }

    private boolean checkCooldown(Player player, double cooldownSeconds) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            double lastUse = cooldowns.get(player.getUniqueId());
            double currentTime = System.currentTimeMillis() / 1000.0; // Convert to seconds

            if (currentTime - lastUse < cooldownSeconds) {
                // Still in cooldown
                return false;
            }
        }

        // Not in cooldown or cooldown expired, update the cooldown
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000.0);
        return true;
    }
}
