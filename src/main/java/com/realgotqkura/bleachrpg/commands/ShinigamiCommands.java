package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.items.ZanpakutoItems;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.ShinigamiStage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShinigamiCommands implements CommandExecutor {


    private BleachRPG plugin;

    public ShinigamiCommands(BleachRPG plugin){
        this.plugin = plugin;
    }

    String[] validZanpakutos = new String[]{
       "zangetsu"
    };

    private String getZanpakuto(String s){
        for(int i = 0; i < validZanpakutos.length; i++){
            if(s.equalsIgnoreCase(validZanpakutos[i])){
                return validZanpakutos[i];
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(LangUtils.getMessage("OnlyForPlayersCMD"));
            return true;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("getzanpakuto")){

            if(!sender.hasPermission("BleachOP.use")){
                player.sendMessage(LangUtils.getMessage("NoPermissionCMD"));
                return true;
            }

            if(args.length < 1){
                player.sendMessage(LangUtils.getMessage("CorrectUsage") + "/getzanpakuto <Zanpkakuto> <Player (Optional)>");
                return true;
            }

            String zanpakuto = getZanpakuto(args[0]);
            if(zanpakuto == null){
                player.sendMessage(LangUtils.getMessage("InvalidZanpakuto"));
                return true;
            }

            ZanpakutoItems zanpakutoItems = new ZanpakutoItems(plugin);

            if(args.length > 1){
                Player itemRecieverPlayer = Bukkit.getPlayer(args[1]);
                if(itemRecieverPlayer == null){
                    player.sendMessage(LangUtils.getMessage("InvalidPlayer"));
                    return true;
                }
                RandomUtils.addItemIntoPlInventory(itemRecieverPlayer, zanpakutoItems.getZanpakuto(zanpakuto, ShinigamiStage.REGULAR), 0);
                return true;
            }
            RandomUtils.addItemIntoPlInventory(player, zanpakutoItems.getZanpakuto(zanpakuto, ShinigamiStage.REGULAR), 0);
            return true;
        }
        return false;
    }
}
