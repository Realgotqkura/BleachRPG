package com.realgotqkura.bleachrpg.commands.tabcompleters;

import com.realgotqkura.bleachrpg.items.BleachItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GeneralTabComp implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("bleachitem")){
            if(strings.length == 1){
                List<String> args = new ArrayList<>();
                BleachItems items = new BleachItems();
                for(String ks : items.getItemMap().keySet()){
                    args.add(ks);
                }
                return args;
            }

            if(strings.length == 2){
                List<String> args = new ArrayList<>();
                for(Player pl : Bukkit.getOnlinePlayers()){
                    args.add(pl.getName());
                }
                return args;
            }
        }
        return null;
    }
}
