package com.realgotqkura.bleachrpg.items.guispecific;

import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiritualLevelItems {

    private static HashMap<Integer, String[]> rewardsInLoreArr = new HashMap<>();

    public static void addIntoLoreList(){

        rewardsInLoreArr.put(0, new String[]{
                RandomUtils.color("&7Unlocks &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(1, new String[]{
                RandomUtils.color("&7Unlocks &bMeditation"),
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(2, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(3, new String[]{
                RandomUtils.color("&a+1 &bXP/s &7when Meditating"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(4, new String[]{
                RandomUtils.color("&a+2 &eSkill &7Point"),
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(5, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(6, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(7, new String[]{
                RandomUtils.color("&a+10 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(8, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(9, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a-1s &bHeavy Attack &7Cooldown"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(10, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(11, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(12, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(13, new String[]{
                RandomUtils.color("&a+1 &bXP/s &7when Meditating"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(14, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Points"),
                RandomUtils.color("&a-5 &bFlashStep &7Cost"),
                RandomUtils.color("&7Unlocks &4&l&k:: " +
                        "&x&0&0&8&8&F&F&lS&x&1&8&6&D&F&B&lh&x&2&F&5&2&F&7&li&x&4&7&3&6&F&4&lk&x&5&E&1&B&F&0&la&x&7&6&0&0&E&C&li " + "&4&l&k::"),
                RandomUtils.color("&a+5 &7Max &bReiatsu"),
                "",
                RandomUtils.color("&7For unlocking shikai. Look at"),
                RandomUtils.color("&a/bleachrpg_tutorial &7for steps.")

        });

        rewardsInLoreArr.put(15, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(16, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(17, new String[]{
                RandomUtils.color("&a+10 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(18, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(19, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a-0.5s &bHeavy Attack &7Cooldown"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(20, new String[]{
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(21, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(22, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(23, new String[]{
                RandomUtils.color("&a+1 &bXP/s &7when Meditating"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(24, new String[]{
                RandomUtils.color("&a+2 &eSkill &7Points"),
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(25, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(26, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(27, new String[]{
                RandomUtils.color("&a+10 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(28, new String[]{
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(29, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a-5 &bFlashStep &7Cost"),
                RandomUtils.color("&7Unlocks &b&l&k:: " +
                        "&x&F&F&4&5&0&0&lB&x&F&F&4&5&0&0&lA&x&F&A&3&4&0&0&lN&x&F&6&2&3&0&0&lK&x&F&1&1&1&0&0&lA&x&E&C&0&0&0&0&lI &b&l&k::"),
                RandomUtils.color("&a+5 &7Max &bReiatsu"),
                "",
                RandomUtils.color("&7For unlocking Bankai. Look at"),
                RandomUtils.color("&a/bleachrpg_tutorial &7for steps.")

        });

        rewardsInLoreArr.put(30, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(31, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(32, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(33, new String[]{
                RandomUtils.color("&a+1 &bXP/s &7when Meditating"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(34, new String[]{
                RandomUtils.color("&a+2 &eSkill &7Points"),
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(35, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(36, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(37, new String[]{
                RandomUtils.color("&a+10 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(38, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(39, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a-1s &bBlocking &7Cooldown"),
                RandomUtils.color("&7Unlocks &8&l&k:: " +
                        "&x&2&1&2&1&2&1&lT&x&2&1&2&1&2&1&lR&x&3&A&3&A&3&A&lU&x&5&2&5&2&5&2&lE &x&8&4&8&4&8&4&lB&x&9&C&9&C&9&C&lA&x&B&5&B&5&B&5&lN&x&C&E&C&E&C&E&lK&x&E&6&E&6&E&6&lA&x&F&F&F&F&F&F&lI &f&l&k::"),
                RandomUtils.color("&a+5 &7Max &bReiatsu"),
                "",
                RandomUtils.color("&7For unlocking Bankai. Look at"),
                RandomUtils.color("&a/bleachrpg_tutorial &7for steps.")
        });

        rewardsInLoreArr.put(40, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(41, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(42, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(43, new String[]{
                RandomUtils.color("&a+2 &bXP/s &7when Meditating"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(44, new String[]{
                RandomUtils.color("&a+2 &eSkill &7Points"),
                RandomUtils.color("&7Stronger &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(45, new String[]{
                RandomUtils.color("&a+0.2 &bReiatsu &7Regen"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(46, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(47, new String[]{
                RandomUtils.color("&a+10 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(48, new String[]{
                RandomUtils.color("&7Faster &bFlashStep"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });

        rewardsInLoreArr.put(49, new String[]{
                RandomUtils.color("&a+1 &eSkill &7Point"),
                RandomUtils.color("&a+5 &7Max &bReiatsu")
        });


        JLogger.log(JLogSeverity.DEBUG, "E: " + rewardsInLoreArr.size());

    }

    /**
     *
     * @param lvl - The level. Start it at 0 Please
     * @param hasDoneLvl - if the player has passed this lvl.
     * @return
     */
    public ItemStack skillItemStack(int lvl, boolean hasDoneLvl){
        ItemStack stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        if(hasDoneLvl){
            stack.setType(Material.LIME_STAINED_GLASS_PANE);
        }

        ItemMeta meta = stack.getItemMeta();

        if(hasDoneLvl){
            meta.setDisplayName(RandomUtils.color("&aSpiritual Level " + (lvl+1)));
            meta.addEnchant(Enchantment.DURABILITY,1,true);
        }else{
            meta.setDisplayName(RandomUtils.color("&cSpiritual Level " + (lvl+1)));
        }

        List<String> lore = new ArrayList<>();
        lore.add(RandomUtils.color("&7--------------------------"));
        lore.add("");
        lore.add(RandomUtils.color("&6Rewards: "));
        for(String s : rewardsInLoreArr.get(lvl)){
            lore.add("  " + s);
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }
}
