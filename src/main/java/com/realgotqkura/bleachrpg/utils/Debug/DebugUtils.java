package com.realgotqkura.bleachrpg.utils.Debug;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DebugUtils {


    /**
     * Stupid Multiverse making my gamemode survival every time i reload
     * And because this stupid plugin does it i have to do it 1 tick later
     *
     */
    public static void makeDevCreative(BleachRPG plugin){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player.isOp()){
                        player.setGameMode(GameMode.CREATIVE);
                    }
                }
                cancel();
            }
        }.runTaskLater(plugin, 10);
    }

    public static boolean TEST_MODE = false;

}
