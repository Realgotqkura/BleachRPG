package com.realgotqkura.bleachrpg.items;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.ConfigUtils;
import com.realgotqkura.bleachrpg.utils.ItemUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.ShinigamiStage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZanpakutoItems {

    public BleachRPG plugin;

    public ZanpakutoItems(BleachRPG plugin){
        this.plugin = plugin;
    }

    public ItemStack getZanpakuto(String s, ShinigamiStage stage){
      switch(s.toLowerCase()){
          case "zangetsu":
              return zangetsuZanpakuto(stage);
      }

      return new ItemStack(Material.ACACIA_PLANKS,1); // <- Will never run
    }

    /*
    Each zanpakuto will have 3 stages. Regular, Shikai, Bankai (AKA 3 different ItemStacks)

     */
    public ItemStack zangetsuZanpakuto(ShinigamiStage stage){
        ConfigUtils configUtils = new ConfigUtils(plugin);
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.of("#3db8d1") + "" + ChatColor.BOLD +"Zangetsu");
        List<String> lore = new ArrayList<>();
        lore.add(RandomUtils.color("&7Damage: &c" + configUtils.getDamage("zangetsu", stage) + "⚔"));
        lore.add(RandomUtils.color("&7Reiatsu: &b" + configUtils.getReiatsu("zangetsu", stage) + "✺"));

        AttributeModifier attackSpeedModifier = null;
        switch (stage){
            case REGULAR:
                lore.add(RandomUtils.color("&7Attack Speed: &eMedium"));
                attackSpeedModifier =  new AttributeModifier(UUID.randomUUID(), "generic.attackspeed", -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                break;
            case SHIKAI:
                lore.add(RandomUtils.color("&7Attack Speed: &eSlow"));
                attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackspeed", -3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                break;
            case BANKAI:
                stack.setType(Material.NETHERITE_SWORD);
                lore.add(RandomUtils.color("&7Attack Speed: &eFast"));
                attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackspeed", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                break;
        }
        //Enchants later i guess
        lore.add("");
        lore.add(RandomUtils.color("&8Type: Zanpakuto"));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(RandomUtils.nskStorage.get("BleachWeaponSpecific"), PersistentDataType.STRING, "Zangetsu");
        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", configUtils.getDamage("zangetsu", stage), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);

        if(attackSpeedModifier != null)
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);

        stack.setItemMeta(meta);
        stack = ItemUtils.setReiatsuStat(stack, configUtils.getReiatsu("zangetsu", stage));
        return stack;
    }


}
