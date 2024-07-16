package com.realgotqkura.bleachrpg.utils;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.objectclasses.Ability;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RandomUtils {

    public static ItemStack errorStack = new ItemStack(Material.ACACIA_PLANKS);
    public static HashMap<String, NamespacedKey> nskStorage = new HashMap<>();


    public static void createNSK(BleachRPG plugin){
        nskStorage.put("BleachID", new NamespacedKey(plugin, "bleach_id"));
        nskStorage.put("BleachItem", new NamespacedKey(plugin, "bleach_item"));
        nskStorage.put("BleachWeapon", new NamespacedKey(plugin, "bleach_weapon"));
        nskStorage.put("NonStackableBleachRPG", new NamespacedKey(BleachRPG.instance, "non_stackable_bleach_rpg"));
        nskStorage.put("ItemReiatsu", new NamespacedKey(plugin, "weapon_reiatsu"));
        nskStorage.put("BleachWeaponSpecific", new NamespacedKey(plugin, "bleach_weapon_specific"));
        nskStorage.put("BleachUncraftable", new NamespacedKey(plugin, "bleach_uncraftable"));
    }

    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Just makes sure the item is added in a slot without deleting an already existing item in the inventory
     */
    public static void addItemIntoPlInventory(Player player, ItemStack stack, int slot){
        Inventory inv = player.getInventory();


        if(inv.getItem(slot) == null) {
            player.getInventory().setItem(slot, stack);
            return;
        }

        int openSlot = -1;

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null){
                openSlot = i;
                break;
            }
        }

         if(openSlot != -1){
             player.getInventory().setItem(openSlot, player.getInventory().getItem(slot));
         }else{
             player.getWorld().dropItemNaturally(player.getLocation(), player.getInventory().getItem(slot));
         }

        player.getInventory().setItem(slot, stack);
    }

    public static void addItemIntoPlInventoryNoSpecific(Player player, ItemStack stack){
        Inventory inv = player.getInventory();
        int openSlot = -1;

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null){
                openSlot = i;
                break;
            }
        }

        if(openSlot != -1){
            player.getInventory().setItem(openSlot, stack);
        }else{
            player.getWorld().dropItemNaturally(player.getLocation(), stack);
        }

    }

    public static int getEarliestEmptySlot(Player player){
        Inventory inv = player.getInventory();
        int openSlot = -1;

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null){
                openSlot = i;
                break;
            }
        }
        return openSlot;
    }

    public static void removeItemFromInv(Player player, ItemStack item){
        List<ItemStack> removeList = new ArrayList<>();
        for(ItemStack stack : player.getInventory()){
            if(stack.isSimilar(item)){
                removeList.add(stack);
            }
        }

        removeList.forEach(e ->{
            player.getInventory().remove(e);
        });
    }

    public static float roundToSpecificDecimalPoint(float value, int decimalPlaces){
        float scale = (float) Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }


    public static HashMap<Player, Boolean> isAnimatedTitleDone = new HashMap<>();
    /**
     * String should be ran through with RandomUtils.color("")
     *
     * @param title
     * @param subtitle
     * @param period - The period in the bukkit runnable. Lower = faster
     */
    public static void sendMultipleAnimatedTitles(String[] title, String[] subtitle, int period, BleachRPG plugin, Player player, int periodBetweenTitles){
        isAnimatedTitleDone.put(player, false);
        int count = title.length;
        new BukkitRunnable(){

            int i = 0;
            int periodBetween = 0;
            String addedTitle = "";
            String addedSub = "";
            @Override
            public void run() {
                char[] titleChars = title[i].toCharArray();
                char[] subChars = subtitle[i].toCharArray();

                int doneCount = 0;
                String sendTitleStr = "";
                String sendSubStr = "";
                if(addedTitle.length() != title[i].length()){
                    sendTitleStr += addedTitle + titleChars[addedTitle.length()];
                    addedTitle += titleChars[addedTitle.length()];
                }else{
                    sendTitleStr = title[i];
                    doneCount++;
                }

                if(addedSub.length() != subtitle[i].length()){
                    sendSubStr += addedSub + subChars[addedSub.length()];
                    addedSub += subChars[addedSub.length()];
                }else{
                    sendSubStr = subtitle[i];
                    doneCount++;
                }

                player.sendTitle(sendTitleStr, sendSubStr, 0, 20, 10);

                if(periodBetween >= periodBetweenTitles){
                    i++;
                    addedTitle = "";
                    addedSub = "";
                    periodBetween = 0;
                }

                if(doneCount == 2){
                    periodBetween += period;
                }

                if(i == count){
                    isAnimatedTitleDone.put(player, true);
                    cancel();
                }

            }

        }.runTaskTimer(plugin, 0,period);
    }

    public static void sendMultipleAnimTitlesNoTransform(String[] title, String[] subtitle, int period, BleachRPG plugin, Player player, int periodBetweenTitles){
        int count = title.length;
        new BukkitRunnable(){

            int i = 0;
            int periodBetween = 0;
            String addedTitle = "";
            String addedSub = "";
            @Override
            public void run() {
                char[] titleChars = title[i].toCharArray();
                char[] subChars = subtitle[i].toCharArray();

                int doneCount = 0;
                String sendTitleStr = "";
                String sendSubStr = "";
                if(addedTitle.length() != title[i].length()){
                    sendTitleStr += addedTitle + titleChars[addedTitle.length()];
                    addedTitle += titleChars[addedTitle.length()];
                }else{
                    sendTitleStr = title[i];
                    doneCount++;
                }

                if(addedSub.length() != subtitle[i].length()){
                    sendSubStr += addedSub + subChars[addedSub.length()];
                    addedSub += subChars[addedSub.length()];
                }else{
                    sendSubStr = subtitle[i];
                    doneCount++;
                }

                player.sendTitle(sendTitleStr, sendSubStr, 0, 20, 10);

                if(periodBetween >= periodBetweenTitles){
                    i++;
                    addedTitle = "";
                    addedSub = "";
                    periodBetween = 0;
                }

                if(doneCount == 2){
                    periodBetween += period;
                }

                if(i == count){
                    cancel();
                }

            }

        }.runTaskTimer(plugin, 0,period);
    }



    public static boolean shouldStopUsage(Player player){
        if(isAnimatedTitleDone.get(player) != null && !isAnimatedTitleDone.get(player))
            return true;


        return false;
    }

}
