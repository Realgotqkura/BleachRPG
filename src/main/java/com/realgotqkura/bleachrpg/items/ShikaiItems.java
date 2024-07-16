package com.realgotqkura.bleachrpg.items;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.ItemUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ShikaiItems {

    public ItemStack zangetsuShikai(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.CYAN_DYE);
        ItemMeta meta = stack.getItemMeta();


        int taihoRadius = plugin.getConfig().getInt("Zangetsu.getsugaTaihoRadius");

        meta.setDisplayName(ChatColor.of("#32a8a0") + "" + ChatColor.BOLD + "Shikai Abilities");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color(ChatColor.of("#d0ff42") + "&lRIGHT CLICK &r" + ChatColor.of("#00fff2") + "Getsuga Tenshō: "));
        lore.add(RandomUtils.color("&7Unleashes a blast of energy in front of you going"));
        lore.add(RandomUtils.color("&7in a straight line. It will continue if it hits a"));
        lore.add(RandomUtils.color("&7wall."));
        lore.add(RandomUtils.color(ChatColor.of("#d4d4d4") + "Damage: &c" + plugin.getConfig().getInt("Zangetsu.getsugaTenshouDamage") + "⚔"
        + " " + ChatColor.of("#d4d4d4") + "Reiatsu: &b" + plugin.getConfig().getInt("Zangetsu.getsugaTenshouCost") + "✺" +
                " " + ChatColor.of("#d4d4d4") + "Cooldown: &a" + plugin.getConfig().getDouble("Zangetsu.getsugaTenshouCD") + "s"));
        lore.add("");
        lore.add(RandomUtils.color(ChatColor.of("#d0ff42") + "&lSHIFT RIGHT CLICK &r" + ChatColor.of("#00fff2") + "Getsuga Taihō: "));
        lore.add(RandomUtils.color("&7Creates an explosion using spiritual energy"));
        lore.add(RandomUtils.color("&7that launches players in a &a" + taihoRadius + "&7x&a" + taihoRadius + "&7x&a" + taihoRadius + " &7cube"));
        lore.add(RandomUtils.color("&7Does not break blocks."));
        lore.add(RandomUtils.color(ChatColor.of("#d4d4d4") + "Damage: &c" + plugin.getConfig().getInt("Zangetsu.getsugaTaihoDamage") + "⚔"
                + " " + ChatColor.of("#d4d4d4") + "Reiatsu: &b" + plugin.getConfig().getInt("Zangetsu.getsugaTaihoCost") + "✺" +
                " " + ChatColor.of("#d4d4d4") + "Cooldown: &a" + plugin.getConfig().getDouble("Zangetsu.getsugaTaihoCD") + "s"));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachID"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN, true);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        stack = ItemUtils.makeItemUnstackable(stack);
        return stack;
    }
}
