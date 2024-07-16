package com.realgotqkura.bleachrpg.items;

import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiItems {

    public ItemStack GUIglassDark() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + ".");
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack GUIglass() {
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + ".");
        glass.setItemMeta(meta);
        return glass;
    }

    public ItemStack GUIClose() {
        ItemStack close = new ItemStack(Material.IRON_DOOR);
        ItemMeta meta = close.getItemMeta();
        meta.setDisplayName(LangUtils.getMessage("GUICloseName"));
        close.setItemMeta(meta);
        return close;
    }

    public ItemStack page(boolean up){
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();

        if(up){
            meta.setDisplayName(RandomUtils.color("&aPage Up"));
        }else {
            meta.setDisplayName(RandomUtils.color("&aPage Down"));
        }
        stack.setItemMeta(meta);
        return stack;
    }
}
