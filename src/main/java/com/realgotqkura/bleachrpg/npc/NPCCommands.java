package com.realgotqkura.bleachrpg.npc;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.npc.npcs.ShikaiBossNPC;
import com.realgotqkura.bleachrpg.npc.npcs.UraharaNPC;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.spirits.ZanpakutoSpirits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtils.getMessage("OnlyForPlayersCMD"));
            return true;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("bleachnpcspawn")){
            if(!player.hasPermission("BleachOP.use")) {
                player.sendMessage(RandomUtils.color("&cYou don't have permission to use this command!"));
                return true;
            }

            if(args.length == 0){
                player.sendMessage(RandomUtils.color("&cCorrect Usage: /bleachnpcspawn <NPC>"));
                return true;
            }

            switch (args[0].toLowerCase()){
                case "urahara":
                    new UraharaNPC(player.getLocation());
                    break;
                case "shikaiboss":
                    new ShikaiBossNPC(player.getLocation(), ZanpakutoSpirits.ZANGETSU, BleachRPG.instance, player.getLocation());
                    break;
            }

            return true;
        }

        return true;
    }
}
