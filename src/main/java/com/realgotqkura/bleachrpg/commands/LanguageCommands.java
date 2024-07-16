package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LanguageCommands implements CommandExecutor {

    final BleachRPG plugin;

    public LanguageCommands(BleachRPG plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("bleachlang")){
            /*
            if(sender.hasPermission("BleachOP.use")){
                if(args.length == 1){
                    String f_arg = args[0].toLowerCase();
                    switch(f_arg){
                        case "english":
                            plugin.getConfig().set("pluginLanguage", "en");
                            break;
                        case "español":
                        case "espanol":
                            plugin.getConfig().set("pluginLanguage", "es");
                            break;
                    }
                    sender.sendMessage(LangUtils.getMessage("ReloadServerWarning"));
                    plugin.saveConfig();
                }
            }else{
                sender.sendMessage(LangUtils.getMessage("NoPermissionCMD"));
                return true;
            }
             */
            sender.sendMessage(RandomUtils.color("&cKinda lazy to add all translations so for now its this :D"));
            return true;
        }
        return false;
    }
}
