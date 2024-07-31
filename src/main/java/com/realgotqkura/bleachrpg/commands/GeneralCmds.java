package com.realgotqkura.bleachrpg.commands;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.configs.XPConfig;
import com.realgotqkura.bleachrpg.events.general.RegionEvents;
import com.realgotqkura.bleachrpg.guis.MeditationGUI;
import com.realgotqkura.bleachrpg.guis.SpiritualLevelInv;
import com.realgotqkura.bleachrpg.guis.TutorialGUI;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachRegion;
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
    final XPConfig xpConfig;

    public GeneralCmds(BleachRPG plugin, PlayerConfig playerConfig, XPConfig xpConfig){
        this.plugin = plugin;
        this.plConfig = playerConfig;
        this.xpConfig = xpConfig;
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

        if(label.equalsIgnoreCase("bleach_reloadConfigs")){
            if(!player.hasPermission("BleachOP.use")) {
                player.sendMessage(RandomUtils.color("&cYou don't have permission to use this command"));
                return true;
            }

            plugin.reloadConfig();
            plConfig.reloadConfig();
            xpConfig.reloadConfig();
            player.sendMessage(RandomUtils.color("&aSuccessfully Reloaded the configs!"));
            return true;
        }

        if(label.equalsIgnoreCase("bleach_admin_regionwand")){
            if(!player.hasPermission("BleachOP.use")) {
                player.sendMessage(RandomUtils.color("&cYou don't have permission to use this command"));
                return true;
            }

            player.getInventory().addItem(BleachItems.bleachRegions(player));
            return true;
        }

        if(label.equalsIgnoreCase("bleach_set_arena_region")){
            if(!player.hasPermission("BleachOP.use")) {
                player.sendMessage(RandomUtils.color("&cYou don't have permission to use this command"));
                return true;
            }

            if(args.length == 0){
                BleachRegion region = new BleachRegion(player);

                plugin.getConfig().set("Arena.firstLocation", region.getLocations()[0]);
                plugin.getConfig().set("Arena.secondLocation", region.getLocations()[1]);
                plugin.saveConfig();

                player.sendMessage(RandomUtils.color("&aSuccessfully setup the arena region!"));
                return true;
            }

            if(args[0].equalsIgnoreCase("player_location")){
                plugin.getConfig().set("Arena.playerLocation", player.getLocation());
                plugin.saveConfig();

                player.sendMessage(RandomUtils.color("&aSuccessfully set the location of the player"));

                return true;
            }

            if(args[0].equalsIgnoreCase("boss_location")){
                plugin.getConfig().set("Arena.bossLocation", player.getLocation());
                plugin.saveConfig();

                player.sendMessage(RandomUtils.color("&aSuccessfully set the location of the boss"));

                return true;
            }


            return true;
        }

        return false;
    }

}
