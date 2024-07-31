package com.realgotqkura.bleachrpg.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.realgotqkura.bleachrpg.BleachRPG;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static String locationToString(Location loc){
        return RandomUtils.color("&7world: &a" + loc.getWorld().getName() + "&7, x: &a" + loc.getX() + "&7, &ay: " + loc.getY() + "&7, &az: " + loc.getZ());
    }

    public static boolean isInside(Location checkLoc, Location firstLoc, Location secondLoc){
        int[] xSorted = new int[]{firstLoc.getBlockX(), secondLoc.getBlockX()};
        int[] zSorted = new int[]{firstLoc.getBlockZ(), secondLoc.getBlockZ()};
        Arrays.sort(xSorted);
        Arrays.sort(zSorted);

        if((checkLoc.getX() >= xSorted[0] && checkLoc.getX() <= xSorted[1]) &&
                (checkLoc.getZ() >= zSorted[0] && checkLoc.getZ() <= zSorted[1]))
            return true;

        return false;
    }


    public static double calculateDamageApplied(double damage, LivingEntity victim) {
        int epf = getEPFLivingEntity(victim);
        double points = victim.getAttribute(Attribute.GENERIC_ARMOR).getValue();
        double toughness = victim.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();
        PotionEffect effect = victim.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        int resistance = effect == null ? 0 : effect.getAmplifier();
        double withArmorAndToughness = damage * (1 - Math.min(20, Math.max(points / 5, points - damage / (2 + toughness / 4))) / 25);
        double withResistance = withArmorAndToughness * (1 - (resistance * 0.2));
        double withEnchants = withResistance * (1 - (Math.min(20.0, epf) / 25));
        return withEnchants;
    }

    private static int getEPFLivingEntity(LivingEntity lv) {
        ItemStack helm = lv.getEquipment().getHelmet();
        ItemStack chest = lv.getEquipment().getChestplate();
        ItemStack legs = lv.getEquipment().getLeggings();
        ItemStack boot = lv.getEquipment().getBoots();

        return (helm != null ? helm.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) +
                (chest != null ? chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) +
                (legs != null ? legs.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0) +
                (boot != null ? boot.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) : 0);
    }


    public static String applyPlaceholdersToString(String s, Player player){
        String rString = s;
        rString = rString.replace("%player_name%", player.getName());
        return rString;
    }


    /**
     * Basically just applies the placeholders to all lines in the String[] given.
     * @return the processed String[] with the placeholders changed
     */
    public static String[] processVoiceLines(List<String> array, Player player){
        String[] rArray = new String[array.size()];

        for(int i = 0; i < array.size(); i++){
            rArray[i] = applyPlaceholdersToString(array.get(i), player);
        }

        return rArray;
    }


}
