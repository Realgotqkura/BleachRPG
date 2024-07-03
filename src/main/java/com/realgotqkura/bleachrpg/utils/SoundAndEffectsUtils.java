package com.realgotqkura.bleachrpg.utils;

import org.bukkit.Location;
import org.bukkit.Sound;

public class SoundUtils {


    public static void playSound(Location location, Sound sound, float volume){
        location.getWorld().playSound(location,sound,volume, 1.0f);
    }
}
