package com.realgotqkura.bleachrpg.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenString {

    private static final String LOWERCASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomString(int stringLength) {
        StringBuilder rString = new StringBuilder();

        for (int i = 0; i < stringLength; i++) {
            // Alternate between lowercase and uppercase
            if (i % 2 == 0) {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, LOWERCASE_ALPHABET.length());
                rString.append(LOWERCASE_ALPHABET.charAt(randomIndex));
            } else {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, UPPERCASE_ALPHABET.length());
                rString.append(UPPERCASE_ALPHABET.charAt(randomIndex));
            }
        }

        return rString.toString();
    }

    public ItemStack makeItemUnstackable(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();
        
    }
}
