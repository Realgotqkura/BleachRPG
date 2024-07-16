package com.realgotqkura.bleachrpg.commands.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DebugTabCompleters implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("bleachRPG_debug")){
            if(strings.length == 1){
                List<String> args = new ArrayList<>();
                args.add("SimulateLvlUp");
                args.add("resetLevelAndXP");
                return args;
            }
        }
        return null;
    }
}
