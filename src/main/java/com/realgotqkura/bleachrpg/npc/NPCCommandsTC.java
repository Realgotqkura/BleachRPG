package com.realgotqkura.bleachrpg.npc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class NPCCommandsTC implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("bleachnpcspawn")){
            if(strings.length == 1){
                List<String> args = new ArrayList<>();
                args.add("urahara");
                return args;
            }
        }
        return null;
    }
}
