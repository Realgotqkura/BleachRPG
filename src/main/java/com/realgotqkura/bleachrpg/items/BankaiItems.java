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

import java.util.ArrayList;
import java.util.List;

public class BankaiItems {

    public ItemStack zangetsuBankai(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.BLACK_DYE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.of("#ff0000") + "" + ChatColor.BOLD + "Bankai Abilities");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color(ChatColor.of("#d0ff42") + "&lRIGHT CLICK &r &8&kEE " + ChatColor.of("#b30707") + "&lGetsuga Jūjishō &r&8&kEE: "));
        lore.add(RandomUtils.color("&7A more powerful version of Getsuga Tenshō."));
        lore.add(RandomUtils.color("&7It unleashes a cross of reiatsu instead of a"));
        lore.add(RandomUtils.color("&7line."));
        lore.add(RandomUtils.color(ChatColor.of("#d4d4d4") + "Damage: &c" + plugin.getConfig().getInt("Zangetsu.getsugaJujishoDamage") + "⚔"
                + " " + ChatColor.of("#d4d4d4") + "Reiatsu: &b" + plugin.getConfig().getInt("Zangetsu.getsugaJujishoCost") + "✺" +
                " " + ChatColor.of("#d4d4d4") + "Cooldown: &a" + plugin.getConfig().getDouble("Zangetsu.getsugaJujishoCD") + "s"));
        lore.add("");
        lore.add(RandomUtils.color(ChatColor.of("#d0ff42") + "&lPASSIVE &r &8&kEE " + ChatColor.of("#b30707") + "Kōsoku Zangetsu &8&kEE: "));
        lore.add(RandomUtils.color("&7Gives you speed and strength effects"));
        lore.add(RandomUtils.color(ChatColor.of("#d4d4d4") + "Speed: &f" + (plugin.getConfig().getInt("Zangetsu.speedPotEffectAmp") + 1) + "✧"
                + " " + ChatColor.of("#d4d4d4") + "Strength: &4" + (plugin.getConfig().getInt("Zangetsu.strengthPotEffectAmp") + 1) + "⚒"));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER, 2);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachID"), PersistentDataType.INTEGER, 2);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachUncraftable"), PersistentDataType.BOOLEAN, true);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        stack = ItemUtils.makeItemUnstackable(stack);
        return stack;
    }
}
