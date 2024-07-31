package com.realgotqkura.bleachrpg.items.guispecific.tutorial;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TutorialGUIItems {

    public ItemStack learnDash(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.FEATHER);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(LangUtils.getMessage("DashingTutName"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(LangUtils.getMessage("DashingTutDesc1"));
        lore.add(LangUtils.getMessage("DashingTutDesc2"));
        lore.add(LangUtils.getMessage("DashingTutDesc3"));
        lore.add(LangUtils.getMessage("DashingTutDesc4") + RandomUtils.color("&a" + plugin.getConfig().getDouble("flashStepCD") + "s ") + LangUtils.getMessage("Cooldown"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack learnXpGain(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.of("#80e048") + "" + ChatColor.BOLD + LangUtils.getMessage("XPTutName"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(LangUtils.getMessage("XPTutDesc1"));
        lore.add(LangUtils.getMessage("XPTutDesc2"));
        lore.add(LangUtils.getMessage("XPTutDesc3"));
        lore.add("");
        lore.add(LangUtils.getMessage("XPTutDesc4"));
        lore.add(LangUtils.getMessage("XPTutDesc5"));
        lore.add("");
        lore.add(LangUtils.getMessage("XPTutDesc6"));
        lore.add(LangUtils.getMessage("XPTutDesc7"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack shinigamiTutorialGUI(){
        ItemStack stack = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(RandomUtils.color(ChatColor.of("#ff524d") + "&lSoul Reapers"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color("&e&lCLICK &7for more info"));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack learnBasicCombat(BleachRPG plugin){
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = stack.getItemMeta();

        double blockingCD = plugin.getConfig().getDouble("blockingCDAfterBreak");
        double heavyAttackCD = plugin.getConfig().getDouble("heavyAttackCD");
        double heavyAttackDamage = plugin.getConfig().getDouble("heavyAttackDamageMultiplier");

        meta.setDisplayName(ChatColor.of("#b3b2af") + "" + ChatColor.BOLD + "Basic Combat");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(RandomUtils.color("&7This is only about basic combat so anything"));
        lore.add(RandomUtils.color("&7before &n&lshikai&r &7that you should know is here"));
        lore.add("");
        lore.add(RandomUtils.color("&6&lBlocking:"));
        lore.add(RandomUtils.color("&7Blocking is an essential part of combat pvp"));
        lore.add(RandomUtils.color("&7and pve. You can block by &aRight Clicking &7with"));
        lore.add(RandomUtils.color("&7your bleach sword. You don't have to hold it."));
        lore.add(RandomUtils.color("&7You can stop blocking by &aLeft Clicking &7or &bFlash"));
        lore.add(RandomUtils.color("&bStepping &7. Mobs have a chance to block break you"));
        lore.add(RandomUtils.color("&7which would put you in a &a" + blockingCD + "s &7cooldown for blocking."));
        lore.add(RandomUtils.color("&7&n&lblocking only blocks physical attacks!!"));
        lore.add("");
        lore.add(RandomUtils.color("&6&lHeavy Attacks:"));
        lore.add(RandomUtils.color("&7Now you might be wondering how can a player break"));
        lore.add(RandomUtils.color("&7someones block. Thats where heavy attacks come in."));
        lore.add(RandomUtils.color("&7Heavy attacks deal &ax" + heavyAttackDamage + " &7more damage than regular"));
        lore.add(RandomUtils.color("&7They also block break first try. Heavy attacks have a &a" + heavyAttackCD + "s"));
        lore.add(RandomUtils.color("&7cooldown."));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }
}
