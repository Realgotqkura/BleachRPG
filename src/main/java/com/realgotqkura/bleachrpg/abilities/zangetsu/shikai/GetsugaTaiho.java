package com.realgotqkura.bleachrpg.abilities.zangetsu.shikai;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.Ability;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class GetsugaTaiho implements Ability {

    private static HashMap<UUID, Double> cooldowns = new HashMap<>();

    private double abilityCooldown;
    private int damage;
    private BleachRPG plugin;
    public GetsugaTaiho(BleachRPG plugin, double cooldown, int damage){
        this.abilityCooldown = cooldown;
        this.plugin = plugin;
        this.damage = damage;
    }

    @Override
    public void activateAbility(Player activator, Player target) {
        if(!checkCooldown(activator, abilityCooldown, cooldowns))
            return;

        BleachPlayer bleachActivator = new BleachPlayer(activator);

        int range = plugin.getConfig().getInt("Zangetsu.getsugaTaihoRadius");

        int reiatsuCost = plugin.getConfig().getInt("Zangetsu.getsugaTaihoCost");

        if(bleachActivator.getCurrentReiatsu() < reiatsuCost)
            return;


        bleachActivator.setCurrentReiatsu(bleachActivator.getCurrentReiatsu() - reiatsuCost);

        for(Entity entity : activator.getNearbyEntities(range, range, range)){
            if(!(entity instanceof LivingEntity))
                continue;

            LivingEntity lEntity = (LivingEntity) entity;
            Vector plLoc = activator.getLocation().toVector();
            Vector entityLoc = entity.getLocation().toVector();

            Vector dir = plLoc.subtract(entityLoc).normalize().multiply(-1);

            Vector launchVec = dir.multiply(3);
            launchVec.setY(1);

            lEntity.setVelocity(launchVec);
            lEntity.setMetadata("attacker", new FixedMetadataValue(plugin, activator.getName()));
            lEntity.damage(damage);
        }

        Vector plVec = activator.getVelocity();
        plVec.setY(1);
        activator.setVelocity(plVec);

       World world = activator.getWorld();
       Location pl = activator.getLocation();

        for(int i = (range * -1); i < (range + 1); i++){
            for(int j = (range * -1); j < (range + 1); j++){
                int randomX = ThreadLocalRandom.current().nextInt(0, 200 + 1);
                Location nLoc = new Location(world, pl.getX() + i, pl.getY()-1, pl.getZ() + j);
                Location fbLoc = new Location(world, pl.getX() + i, pl.getY()+1 + (randomX/100), pl.getZ() + j);
                world.spawnFallingBlock(fbLoc, world.getBlockAt(nLoc).getType().createBlockData());
                world.spawnParticle(Particle.BLOCK_DUST, fbLoc, 10, 0,0,0, 0, Material.LIGHT_BLUE_WOOL.createBlockData());
                world.getBlockAt(nLoc).setType(Material.AIR);
                SoundAndEffectsUtils.playSound(activator.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5f);
            }
        }




    }
}
