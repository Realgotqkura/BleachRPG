package com.realgotqkura.bleachrpg.guis;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.items.GuiItems;
import com.realgotqkura.bleachrpg.utils.GUISorting;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class MeditationGUI implements Listener {


    private static HashMap<Player, Inventory> meditationInvs = new HashMap<>();
    private static HashMap<Player, Integer> pastNumMap = new HashMap<>();
    private static HashMap<Player, BukkitRunnable> checkMeditationMap = new HashMap<>();
    private static HashMap<Player, BukkitRunnable> xpGainMap = new HashMap<>();
    String invName = RandomUtils.color("&6Meditate");
    private BleachRPG plugin;
    private PlayerConfig plConfig;

    public MeditationGUI(BleachRPG plugin, PlayerConfig plConfig){
        this.plugin = plugin;
        this.plConfig = plConfig;
    }



    private ItemStack clickyItem(){
        ItemStack stack = new ItemStack(Material.LIME_WOOL);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(RandomUtils.color("&eKeep Meditating"));
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }

    public void createInv(Player player){
        Inventory inv = Bukkit.createInventory(player, 54, invName);

        GUISorting sorting = new GUISorting();
        GuiItems items = new GuiItems();

        sorting.GetInner(inv, 54);
        sorting.GetOuter(inv, 54, items.GUIglass());

        if(checkMeditationMap.get(player) != null)
            return;

        player.openInventory(inv);
        meditationInvs.put(player, inv);
        plConfig.getConfig().set("players." + player.getUniqueId().toString() + ".isMeditating", true);
        plConfig.saveConfig();
        int XPPs = plugin.getConfig().getInt("defaultMeditationXPPerSec") + plConfig.getConfig().getInt("players." +
                player.getUniqueId() + ".addedMeditationXPPerSec");

        //Check if the meditation still valid and if so update
        new BukkitRunnable(){

            @Override
            public void run() {
                if(checkMeditationMap.get(player) == null)
                    checkMeditationMap.put(player, this);

                if(pastNumMap.get(player) != null){
                    meditationInvs.remove(player);
                    pastNumMap.remove(player);
                    checkMeditationMap.remove(player);
                    xpGainMap.get(player).cancel();
                    xpGainMap.remove(player);
                    plConfig.getConfig().set("players." + player.getUniqueId().toString() + ".isMeditating", false);
                    plConfig.saveConfig();
                    player.closeInventory();
                    player.sendMessage(RandomUtils.color("&cYou failed the meditation!"));
                    cancel();
                    return;
                }

                int randomSlot = ThreadLocalRandom.current().nextInt(0, 54);
                meditationInvs.get(player).setItem(randomSlot, clickyItem());
                pastNumMap.put(player, randomSlot);
            }

        }.runTaskTimer(plugin, 1*20, 5*20);


        BleachPlayer bleachPlayer = new BleachPlayer(player);
        //XP Gain
        new BukkitRunnable(){

            @Override
            public void run() {
                if(xpGainMap.get(player) == null)
                    xpGainMap.put(player, this);

                bleachPlayer.gainSpiritualXP(XPPs);
            }

        }.runTaskTimer(plugin, 20, 20);
    }

    @EventHandler
    public void click(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(invName))
            return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        GuiItems items = new GuiItems();

        if(event.getCurrentItem() == null)
            return;

        if(event.getCurrentItem().equals(clickyItem())){
            event.getInventory().setItem(pastNumMap.get(player), items.GUIglass());
            pastNumMap.remove(player);
        }
    }


    @EventHandler
    public void close(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!event.getView().getTitle().equals(invName))
            return;

        if(checkMeditationMap.get(player) == null)
            return;

        checkMeditationMap.get(player).cancel();
        checkMeditationMap.remove(player);
        xpGainMap.get(player).cancel();
        xpGainMap.remove(player);
        plConfig.getConfig().set("players." + player.getUniqueId() + ".isMeditating", false);
        plConfig.saveConfig();
    }

}
