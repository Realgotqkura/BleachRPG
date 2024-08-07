package com.realgotqkura.bleachrpg.abilities.zangetsu.bankai;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.Ability;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class GetsugaJujisho implements Ability {

    private static HashMap<UUID, Double> cooldowns = new HashMap<>();

    private double abilityCooldown;
    private int damage;
    private BleachRPG plugin;
    public GetsugaJujisho(BleachRPG plugin, double cooldown, int damage){
        this.abilityCooldown = cooldown;
        this.plugin = plugin;
        this.damage = damage;
    }

    @Override
    public void activateAbility(Player activator, Player target) {
        BleachPlayer bleachActivator = new BleachPlayer(activator);
        int reiatsuCost = plugin.getConfig().getInt("Zangetsu.getsugaJujishoCost");

        if(bleachActivator.getCurrentReiatsu() < reiatsuCost)
            return;

        if (!checkCooldown(activator, abilityCooldown, cooldowns))
            return;

        bleachActivator.setCurrentReiatsu(bleachActivator.getCurrentReiatsu() - reiatsuCost);
        SoundAndEffectsUtils.playSound(activator.getLocation(), Sound.BLOCK_CHAIN_BREAK, 4);
        Bat proj = (Bat) activator.getWorld().spawnEntity(activator.getLocation(), EntityType.BAT);
        proj.setAI(true);
        proj.setVisibleByDefault(false);
        proj.setCollidable(false);

        Vector pDir = activator.getLocation().getDirection().normalize();

        Vector nVelocity = pDir.multiply(1.8);

        //Particles
        new BukkitRunnable(){

            @Override
            public void run() {
                proj.setVelocity(nVelocity);
                Location projLocation = proj.getLocation();
                Location[] locations = new Location[6];

                locations[0] = projLocation.clone();  // Original location
                locations[1] = projLocation.clone().add(0, -1, 0);  // Offset by -1 on Y-axis
                locations[2] = projLocation.clone().add(0, 1, 0);  // Offset by -1 on Y-axis
                locations[3] = projLocation.clone().add(1 * Math.cos(Math.toRadians(projLocation.getYaw())), 1, 1 * Math.sin(Math.toRadians(projLocation.getYaw())));   // Offset by +1 on Y-axis
                locations[4] = projLocation.clone().add(-1 * Math.cos(Math.toRadians(projLocation.getYaw())), 1, -1 * Math.sin(Math.toRadians(projLocation.getYaw())));   // Offset by +1 on Y-axis
                locations[5] = projLocation.clone().add(0, 2, 0);   // Offset by +2 on Y-axis

                for (Location loc : locations) {
                    SoundAndEffectsUtils.makeRedstoneParticlePrecise(loc, new int[]{110, 0, 0}, new int[]{0, 0, 0}, 2.5f, 50);
                }
                for(Entity entity : proj.getNearbyEntities(1.5,2.5,1.5)){
                    if((entity instanceof LivingEntity) && !entity.equals(activator)){
                        entity.setMetadata("attacker", new FixedMetadataValue(plugin, activator.getName()));
                        ((LivingEntity) entity).damage(RandomUtils.calculateDamageApplied(damage, (LivingEntity) entity));
                    }
                }

                if(proj.isDead())
                    cancel();
            }

        }.runTaskTimer(plugin, 0, 1);

        new BukkitRunnable(){

            @Override
            public void run() {
                proj.remove();
                cancel();
            }

        }.runTaskLater(plugin, 5*20);
    }
}
