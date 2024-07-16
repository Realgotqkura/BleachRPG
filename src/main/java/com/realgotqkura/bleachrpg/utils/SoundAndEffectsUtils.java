package com.realgotqkura.bleachrpg.utils;

import org.bukkit.*;

import java.util.concurrent.ThreadLocalRandom;

public class SoundAndEffectsUtils {


    public static void playSound(Location location, Sound sound, float volume){
        location.getWorld().playSound(location,sound,volume, 1.0f);
    }

    public static void playEffect(Location location, Effect effect){
        location.getWorld().playEffect(location, effect, 0);
    }

    public static void makeParticlePrecise(Location loc, Particle particle, int count){
        loc.getWorld().spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), count);
    }

    /**
     *
     * @param loc - Spawn loc
     * @param particle - Spawn particle
     * @param count - The amount of times the particle is spawned with different offsets
     * @param innerCount - The count inside the spawnParticle function. could just type 1 or more it will be fine.
     * @param changeYOffset - Enable Y Offset 0 - false, 1 - true
     */
    public static void makeParticle(Location loc, Particle particle, int count, int innerCount, int changeYOffset){
        for(int i = 0; i < count; i++){
            int randomOffsetX = ThreadLocalRandom.current().nextInt(-100,100);
            int randomOffsetY = ThreadLocalRandom.current().nextInt(-100,100) * changeYOffset;
            int randomOffsetZ = ThreadLocalRandom.current().nextInt(-100,100);
            loc.getWorld().spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), innerCount, randomOffsetX /100, randomOffsetY / 100, randomOffsetZ/ 100);
        }
    }

    public static void makeRedstoneParticlePrecise(Location loc, int[] rgb, int[] offsets, float size, int count){
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), count, offsets[0], offsets[1], offsets[2],
                new Particle.DustOptions(Color.fromRGB(rgb[0], rgb[1], rgb[2]), size));
    }

    /**
     *
     * @param loc - Spawn loc
     * @param rgb - Particle color
     * @param count - The amount of times the particle is spawned with a random offset
     * @param innerCount - The count inside the spawnParticle function. could just type 1 or more it will be fine.
     * @param size - Particle size
     * @param changeYOffset - enables Y Offset 0 - false, 1 - true
     */
    public static void makeRedstoneParticle(Location loc, int[] rgb, int count, int innerCount, float size, int changeYOffset){
        for(int i = 0; i < count; i++){
            int randomOffsetX = ThreadLocalRandom.current().nextInt(-100,100);
            int randomOffsetY = ThreadLocalRandom.current().nextInt(-100,100) * changeYOffset;
            int randomOffsetZ = ThreadLocalRandom.current().nextInt(-100,100);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), innerCount, randomOffsetX / 100, randomOffsetY / 100, randomOffsetZ / 100
            , new Particle.DustOptions(Color.fromRGB(rgb[0], rgb[1], rgb[2]), size));
        }
    }
}
