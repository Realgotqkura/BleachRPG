package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.events.general.GainXPEvents;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            }



            return true;
        }
        return false;
    }

}
