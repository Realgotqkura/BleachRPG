package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.events.general.GainXPEvents;
import com.realgotqkura.bleachrpg.npc.npcs.ShikaiBossNPC;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.Debug.DebugUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DebugCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;


        Player player = (Player) sender;

        if(label.equalsIgnoreCase("bleachRPG_debug")){
            if(!(player.hasPermission("BleachDev.use")))
                return true;

            if(args.length == 0){
                player.sendMessage("Specify an argument");
                return true;
            }

            if(args[0].equalsIgnoreCase("SimulateLvlUp")) {
                GainXPEvents.simulateLevelUp(new BleachPlayer(player));
                return true;
            }

            if(args[0].equalsIgnoreCase("resetLevelAndXP")){
                BleachPlayer bleachPlayer = new BleachPlayer(player);
                bleachPlayer.resetSpiritualLevelAndXP();
                player.sendMessage("Reset");
                return true;
            }

            if(args[0].equalsIgnoreCase("toggleTestMode")){
                if(DebugUtils.TEST_MODE)
                    DebugUtils.TEST_MODE = false;
                else
                    DebugUtils.TEST_MODE = true;

                player.sendMessage("Toggled to " + DebugUtils.TEST_MODE);
                return true;
            }

            if(args[0].equalsIgnoreCase("tp_to_arena")){
                Location firstLoc = BleachRPG.instance.getConfig().getLocation("Arena.firstLocation");
                Location secondLoc = BleachRPG.instance.getConfig().getLocation("Arena.secondLocation");

                double tpX = (firstLoc.getX() + secondLoc.getX()) / 2;
                double tpZ = (firstLoc.getZ() + secondLoc.getZ()) / 2;
                int tpY = firstLoc.getWorld().getHighestBlockYAt((int) tpX, (int) tpZ) + 1 ;

                player.teleport(new Location(firstLoc.getWorld(), tpX, tpY, tpZ));
                return true;
            }

            if(args[0].equalsIgnoreCase("startFight")){
                Location playerLoc = BleachRPG.instance.getConfig().getLocation("Arena.playerLocation");
                Location bossLoc = BleachRPG.instance.getConfig().getLocation("Arena.bossLocation");

                Location oldPlLoc = player.getLocation();

                BleachPlayer bleachPlayer = new BleachPlayer(player);


                player.teleport(playerLoc);
                new ShikaiBossNPC(bossLoc, bleachPlayer.getSpirit(), BleachRPG.instance, oldPlLoc);
            }


            return true;
        }
        return false;
    }

}
