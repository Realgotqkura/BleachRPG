package com.realgotqkura.bleachrpg.utils;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class BleachUtils {

    public static HashMap<Player, Float> playerReiatsuMap = new HashMap<>();

    /**
     * Reiatsu utils for now
     */

    //Get the max reiatsu per player
    public static float getMaxPlayerReiatsu(Player player){
        float defaultReiatsu = (float) BleachRPG.instance.getConfig().getDouble("defaultReiatsuCount");
        defaultReiatsu += getItemReiatsu(player.getInventory().getItemInMainHand());
        defaultReiatsu += new BleachPlayer(player).getAddedReiatsu();

        return defaultReiatsu;
    }

    public static float getReiatsu(Player player){
        return playerReiatsuMap.get(player);
    }

    public static float setReiatsu(Player player, float count){
        return playerReiatsuMap.put(player, count);
    }

    public static int getItemReiatsu(ItemStack item){
        if(item.getItemMeta() == null)
            return 0;

        if(!item.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("ItemReiatsu"), PersistentDataType.INTEGER))
            return 0;

        return item.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("ItemReiatsu"), PersistentDataType.INTEGER).intValue();
    }


    /**
     * Leveling/Xp utils
     */

    public static HashMap<Integer, Integer> xpToLvlMap = new HashMap<>();


    public static void fillXPMap(){
        xpToLvlMap.put(1, 50);
        xpToLvlMap.put(2, 100);
        xpToLvlMap.put(3, 200);
        xpToLvlMap.put(4, 400);
        xpToLvlMap.put(5, 600);
        xpToLvlMap.put(6, 800);
        xpToLvlMap.put(7, 1000);
        xpToLvlMap.put(8, 1050);
        xpToLvlMap.put(9, 1100);
        xpToLvlMap.put(10, 1200);
        xpToLvlMap.put(11, 1300);
        xpToLvlMap.put(12, 1400);
        xpToLvlMap.put(13, 1500);
        xpToLvlMap.put(14, 1000);
        xpToLvlMap.put(15, 5000); //Shikai Requirement
        xpToLvlMap.put(16, 1000);
        xpToLvlMap.put(17, 1000);
        xpToLvlMap.put(18, 2000);
        xpToLvlMap.put(19, 2000);
        xpToLvlMap.put(20, 1500);
        xpToLvlMap.put(21, 1500);
        xpToLvlMap.put(22, 1500);
        xpToLvlMap.put(23, 1500);
        xpToLvlMap.put(24, 3000);
        xpToLvlMap.put(25, 3000);
        xpToLvlMap.put(26, 3000);
        xpToLvlMap.put(27, 5000);
        xpToLvlMap.put(28, 5000);
        xpToLvlMap.put(29, 1300);
        xpToLvlMap.put(30, 50000); //Bankai requirement
        xpToLvlMap.put(31, 10000);
        xpToLvlMap.put(32, 10000);
        xpToLvlMap.put(33, 10000);
        xpToLvlMap.put(34, 10000);
        xpToLvlMap.put(35, 10000);
        xpToLvlMap.put(36, 10000);
        xpToLvlMap.put(37, 10000);
        xpToLvlMap.put(38, 10000);
        xpToLvlMap.put(39, 10000);
        xpToLvlMap.put(40, 50000); //True Bankai Requirement
        xpToLvlMap.put(41, 10000);
        xpToLvlMap.put(42, 10000);
        xpToLvlMap.put(43, 20000);
        xpToLvlMap.put(44, 20000);
        xpToLvlMap.put(45, 20000);
        xpToLvlMap.put(46, 20000);
        xpToLvlMap.put(47, 20000);
        xpToLvlMap.put(48, 100000);
        xpToLvlMap.put(49, 100000);
        xpToLvlMap.put(50, 100000);
    }

    /**
     * Gets the XP amount subtracting the previous levels XP
     */
    public static int getPlayerSpiritualXPNoOtherlvls(Player player){
        BleachPlayer bleachPlayer = new BleachPlayer(player);
        int xpGotten = 0;

        for(int i = 0; i < bleachPlayer.getSpiritualLevel(); i++){
            xpGotten += xpToLvlMap.get(i+1);
        }

        return bleachPlayer.getSpiritualXP() - xpGotten;
    }

    /**
     *
     * @param stack
     * @return If the number is 0 then it doesn't have an ID
     */
    public static int getBleachID(ItemStack stack){
        if(stack == null)
            return 0;

        if(!stack.hasItemMeta())
            return 0;

        if(!stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachID"), PersistentDataType.INTEGER))
            return 0;

        return stack.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachID"), PersistentDataType.INTEGER).intValue();
    }

    public static boolean isBleachWeapon(ItemStack stack){
        if(stack == null)
            return false;

        if(!stack.hasItemMeta())
            return false;

        return stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.BOOLEAN) || stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.INTEGER);
    }

    public static void removeZanpakutoItemsFromInv(Player player){
        for(ItemStack stack : player.getInventory()){
            if(isBleachWeapon(stack)){
                player.getInventory().remove(stack);
            }
        }
    }
}
