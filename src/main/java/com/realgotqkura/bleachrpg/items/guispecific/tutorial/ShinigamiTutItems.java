package com.realgotqkura.bleachrpg.items.guispecific.tutorial;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShinigamiTutItems {


    public ItemStack unlockingShikai(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(RandomUtils.color("&f&lUnlocking Shikai"));

        int cooldownInSecs = plugin.getConfig().getInt("Shikai.fightcooldownInSecs");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color("&7Shikai is the first transformation you can"));
        lore.add(RandomUtils.color("&7unlock. Therefore it's the easiest! There are"));
        lore.add(RandomUtils.color("&a3 &7requirements to unlocking shikai:"));
        lore.add("");
        lore.add(RandomUtils.color("&6- &7Reaching Spiritual LvL &a15"));
        lore.add(RandomUtils.color("&6- &7Reaching 10th or higher division seat"));
        lore.add(RandomUtils.color("&6- &7Meditating for a total of &a5 &7minutes"));
        lore.add("");
        lore.add(RandomUtils.color("&7After meeting the requirements you will have"));
        lore.add(RandomUtils.color("&7to fight your shikai. To fight him you just"));
        lore.add(RandomUtils.color("&7type &a/shikaiBattle &7and you will be teleported"));
        lore.add(RandomUtils.color("&7Into your arena. You will have a &a" + cooldownInSecs / 60f + "m &7cooldown"));
        lore.add(RandomUtils.color("&7if you lose."));
        lore.add(RandomUtils.color("&7Once you defeated your shikai you will be able to"));
        lore.add(RandomUtils.color("&7transform."));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }
}
