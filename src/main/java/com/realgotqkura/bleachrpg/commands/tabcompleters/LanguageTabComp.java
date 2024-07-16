package com.realgotqkura.bleachrpg.commands.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LanguageTabComp implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("bleachlang")){
            if(strings.length == 1){
                List<String> args = new ArrayList<>();
                args.add("english");
                args.add("espanol");
                return args;
            }
        }
        return null;
    }
}

