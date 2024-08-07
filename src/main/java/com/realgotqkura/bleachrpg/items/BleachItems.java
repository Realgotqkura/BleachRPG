package com.realgotqkura.bleachrpg.items;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.ItemUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachRegion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BleachItems {

    private static HashMap<String, ItemStack> stackMap = new HashMap<>();

    public void loadItemMap(){
        stackMap.put("SubstituteShinigamiBadge", substituteSoulReaperBadge());
        stackMap.put("NPCDeleter", npcDeleter());
    }


    public ItemStack npcDeleter(){
        ItemStack stack = new ItemStack(Material.BONE);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(RandomUtils.color("&aNPC Deleter"));

        List<String> lore = new ArrayList<>();
        lore.add(RandomUtils.color("&7Delete BleachRPG NPCs with this item"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN, true);
        stack.setItemMeta(meta);
        stack = ItemUtils.makeItemUnstackable(stack);
        return stack;
    }

    public static ItemStack bleachRegions(Player player){
        ItemStack stack = new ItemStack(Material.STICK);
        ItemMeta meta = stack.getItemMeta();

        BleachRegion region = new BleachRegion(player);

        meta.setDisplayName(RandomUtils.color(ChatColor.of("#a577e6") + "&lBleachRPG Region Wand"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color("&8Used to make regions for boss fights."));
        lore.add(RandomUtils.color("&8Don't bother setting Y coords as they are useless."));
        lore.add(RandomUtils.color("&c!! REFRESHES AFTER RELOAD !!"));
        lore.add("");
        lore.add(RandomUtils.color("&e&lLEFT CLICK &r&7to set the first location"));
        lore.add(RandomUtils.color("&e&lRIGHT CLICK &r&7to set the second location"));
        lore.add("");
        lore.add(RandomUtils.color("&7First Location: " + RandomUtils.locationToString(region.getLocations()[0])));
        lore.add(RandomUtils.color("&7First Location: " + RandomUtils.locationToString(region.getLocations()[1])));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.STRING, "bleachRegions");
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN, true);
        stack.setItemMeta(meta);
        stack = ItemUtils.makeItemUnstackable(stack);
        return stack;
    }

    public ItemStack substituteSoulReaperBadge(){
        ItemStack stack = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.of("#c98a32") + "" + ChatColor.BOLD + "Substitute Shinigami Badge");
        List<String> lore = new ArrayList<>();
        lore.add(RandomUtils.color("&7Used to transform to a shinigami and"));
        lore.add(RandomUtils.color("&7back to a human."));
        lore.add("");
        lore.add(ChatColor.of("#8df7e4") + RandomUtils.color("&lRIGHT CLICK ") + ChatColor.of("#F7AA8D") +
                "Transform:");
        lore.add(ChatColor.of("#A29692") + "Transforms you");
        lore.add(ChatColor.of("#A29692") + "if shinigami -> human");
        lore.add(ChatColor.of("#A29692") + "if human -> shinigami");
        lore.add("");
        lore.add(ChatColor.of("#8df7e4") + RandomUtils.color("&lSHIFT+RIGHT CLICK ") + ChatColor.of("#F7AA8D") +
                "Senkaimon:");
        lore.add(ChatColor.of("#A29692") + "Opens a menu from which to choose");
        lore.add(ChatColor.of("#A29692") + "which dimension to go to.");
        lore.add("");
        lore.add(ChatColor.of("#8df7e4") + RandomUtils.color("&lDROP BUTTON CLICK ") + ChatColor.of("#F7AA8D") +
                "Power Up:");
        lore.add(ChatColor.of("#A29692") + "Makes you power up. in order of:");
        lore.add(ChatColor.of("#A29692") + "regular -> shikai -> bankai ->");
        lore.add(ChatColor.of("#A29692") + "true bankai -> mugetsu");
        meta.setLore(lore);
        meta.setCustomModelData(1);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER, 8);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN, true);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        stack = ItemUtils.makeItemUnstackable(stack);
        return stack;
    }


    public HashMap<String, ItemStack> getItemMap(){
        return stackMap;
    }

    public ItemStack getSpecificItem(String name){
        if(!stackMap.containsKey(name)){
            return RandomUtils.errorStack;
        }

        return ItemUtils.refreshNonStackable(stackMap.get(name));
    }
}
