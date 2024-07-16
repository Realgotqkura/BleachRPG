package com.realgotqkura.bleachrpg.commands.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class ShinigamiTabComp implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("getzanpakuto")){
            if(strings.length == 1){
                List<String> args = new ArrayList<>();
                args.add("zangetsu");
                return args;
            }

            if(strings.length == 2){
                List<String> players = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers()){
                    players.add(player.getName());
                }
                return players;
            }
        }
        return null;
    }
}
