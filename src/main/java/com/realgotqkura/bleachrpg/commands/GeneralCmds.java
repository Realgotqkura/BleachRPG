package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.guis.MeditationGUI;
import com.realgotqkura.bleachrpg.guis.SpiritualLevelInv;
import com.realgotqkura.bleachrpg.guis.TutorialGUI;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class GeneralCmds implements CommandExecutor {

    final BleachRPG plugin;
    final PlayerConfig plConfig;

    public GeneralCmds(BleachRPG plugin, PlayerConfig playerConfig){
        this.plugin = plugin;
        this.plConfig = playerConfig;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(LangUtils.getMessage("OnlyForPlayersCMD"));
            return true;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("bleachrpg_tutorial")){
            TutorialGUI gui = new TutorialGUI(plugin);
            gui.createInv(player);
            return true;
        }

        if(label.equalsIgnoreCase("bleachitem")){
            if(!player.hasPermission("BleachOP.use")){
                player.sendMessage(LangUtils.getMessage("NoPermissionCMD"));
                return true;
            }

            if(args.length == 0){
                player.sendMessage(LangUtils.getMessage("Correct Usage") + "/bleachitem <item> <player (Optional)>");
                return true;
            }
            BleachItems items = new BleachItems();

            if(items.getSpecificItem(args[0]).equals(RandomUtils.errorStack)){
                player.sendMessage(LangUtils.getMessage("InvalidItem"));
                return true;
            }

            ItemStack item = items.getSpecificItem(args[0]);
            if(args.length > 1){
                Player receivingPlayer = Bukkit.getPlayer(args[1]);
                RandomUtils.addItemIntoPlInventoryNoSpecific(receivingPlayer, item);
                return true;
            }

            RandomUtils.addItemIntoPlInventoryNoSpecific(player, item);
        }


        //Open spiritual Level GUI
        if(label.equalsIgnoreCase("spiritualLvlgui")){
            SpiritualLevelInv inv = new SpiritualLevelInv(plugin);

            inv.createInv1(player);
        }


        if(label.equalsIgnoreCase("meditate")){

            MeditationGUI gui = new MeditationGUI(plugin, plConfig);

            gui.createInv(player);
        }

        return false;
    }

}
