package com.realgotqkura.bleachrpg.utils;

import com.realgotqkura.bleachrpg.BleachRPG;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Method;
import java.security.SecureRandom;

public class ItemUtils {

    private static String generateRandomString(int stringLength) {
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Random lowercase letter
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static ItemStack makeItemUnstackable(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("NonStackableBleachRPG"),
                PersistentDataType.STRING, generateRandomString(26));

        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Changes the persistentDataContainer so the item is "different"
     * and so the unstackable works. It's a new method instead of using
     * the old one because i have to check if it exists. If it doesn't then
     * nothing is changed. So the first one puts a tag and the second one
     * refreshes it if it exists. And if it doesn't it does nothing
     */
    public static ItemStack refreshNonStackable(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();

        if(!meta.getPersistentDataContainer().has(RandomUtils.nskStorage.get("NonStackableBleachRPG"), PersistentDataType.STRING))
            return stack;

        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("NonStackableBleachRPG"),
                PersistentDataType.STRING, generateRandomString(26));

        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack setReiatsuStat(ItemStack stack, int count){
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("ItemReiatsu"),
                PersistentDataType.INTEGER, count);

        stack.setItemMeta(meta);
        return stack;
    }





}
