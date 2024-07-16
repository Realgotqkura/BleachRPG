package com.realgotqkura.bleachrpg.utils.objectclasses;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.utils.BleachFaction;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.ShinigamiStage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class BleachPlayer {


    /*
    The statics are for object lifespawn
     */
    private static HashMap<Player, Boolean> blockingMap = new HashMap<>();
    private static Map<UUID, Double> cooldowns = new HashMap<>();
    private static Map<UUID, Double> heavyAttackCooldown = new HashMap<>();
    private Player player;
    private PlayerConfig plConfig;

    private BleachRPG plugin;
    private FileConfiguration defaultConfig;

    public BleachPlayer(Player player){
        this.player = player;
        this.plugin = BleachRPG.instance;
        this.plConfig = BleachRPG.playerConf;
        this.defaultConfig = plugin.getConfig();

        if(!blockingMap.containsKey(player)){
            blockingMap.put(player, false);
        }
    }

    public Player getBukkitPlayer(){
        return player;
    }

    public int getSpiritualXP(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".spiritualXP");
    }

    public int getFactionXP(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".factionXP");
    }


    public BleachFaction getFaction(){
        return BleachFaction.fromValue(plConfig.getConfig().getInt("players." + player.getUniqueId() + ".faction"));
    }

    public ShinigamiStage getShinigamiStage(){
        return ShinigamiStage.fromInt(plConfig.getConfig().getInt("players." + player.getUniqueId() + ".currentShinigamiStage"));
    }

    public void setShinigamiStage(ShinigamiStage stage){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".currentShinigamiStage", stage.toInt());
        plConfig.saveConfig();
    }

    public float getCurrentReiatsu(){
        return BleachUtils.playerReiatsuMap.get(player);
    }

    public void setCurrentReiatsu(float num){
        BleachUtils.playerReiatsuMap.put(player, num);
    }

    public int getSpiritualLevel(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".spiritualLvl");
    }

    public boolean isBlocking(){
        return blockingMap.get(player);
    }

    public boolean isHoldingABleachWeapon(){
        ItemStack stack = player.getInventory().getItemInMainHand();

        if(stack.getItemMeta() == null)
            return false;

        if(stack.getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachWeapon"), PersistentDataType.BOOLEAN))
            return true;

        return false;
    }


    public String getSpirit(){
        return plConfig.getConfig().getString("players." + player.getUniqueId() + ".spirit");
    }



    /**
     *
     * @apiNote Check if the player is holding a bleach weapon before running this
     *
     * @return returns the spirit of the weapon: for shinigami ex: (Zangetsu, Senbonzakura); for arrancar its their name; and for quincy their schrift.
     */

    public String getBleachWeaponSpirit(){
        ItemStack weapon = player.getInventory().getItemInMainHand();

        return weapon.getItemMeta().getPersistentDataContainer().get(RandomUtils.nskStorage.get("BleachWeaponSpecific"), PersistentDataType.STRING);
    }

    public boolean isHeavyAttackReady(){
        return checkCooldown(player, defaultConfig.getDouble("heavyAttackCD") - getHeavyAttackCDReduct(), heavyAttackCooldown);
    }

    /*
     * Setters
     */

    public void gainSpiritualXP(int xpCount){
        int currentXp = plConfig.getConfig().getInt("players." + player.getUniqueId() + ".spiritualXP");
        plConfig.getConfig().set("players." + player.getUniqueId() + ".spiritualXP", currentXp + xpCount);
        plConfig.saveConfig();
    }

    public void levelUpSpiritual(){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".spiritualLvl", getSpiritualLevel() + 1);
        plConfig.saveConfig();
    }

    public void resetSpiritualLevelAndXP(){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".spiritualLvl", 0);
        plConfig.getConfig().set("players." + player.getUniqueId() + ".spiritualXP", 0);
        plConfig.saveConfig();
    }

    public void setBlocking(boolean value){
        if(value){
            if(checkCooldown(player, defaultConfig.getDouble("blockingCDAfterBreak") - getBlockingCDReduct(), cooldowns)){
                BleachActionBar.getActionBar("blocking_bar").setEnabled(true);
                blockingMap.replace(player, true);
                return;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(RandomUtils.color("&c&lBlock Broken!")));
            return;
        }

        BleachActionBar.getActionBar("blocking_bar").setEnabled(false);
        blockingMap.replace(player, false);
    }

    /**
     * Breaks the player attack block
     * Not an actual block
     */
    public void breakBlock(){
        setBlocking(false);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(RandomUtils.color("&c&lBlock Broken!")));
        if(checkCooldown(player, defaultConfig.getDouble("blockingCDAfterBreak") - getBlockingCDReduct(), cooldowns)){
            updateCooldown(player, cooldowns);
        }
    }

    public void doHeavyAttack(){
        if(checkCooldown(player, defaultConfig.getDouble("heavyAttackCD") - getHeavyAttackCDReduct(), heavyAttackCooldown)){
            updateCooldown(player, heavyAttackCooldown);
        }
    }




    /*

    --------------------------STATS---------------------------

     */


    public float getAddedReiatsu(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".addedReiatsu");
    }

    public void setAddedReiatsu(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".addedReiatsu", num);
        plConfig.saveConfig();
    }

    public float getReiatsuRegen(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".reiatsuRegen");
    }

    public void setReiatsuRegen(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".reiatsuRegen", num);
        plConfig.saveConfig();
    }

    public int getAddedMeditationXPPerSec(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".addedMeditationXPPerSec");
    }

    public void setAddedMeditationXPPerSec(int num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".addedMeditationXPPerSec", num);
        plConfig.saveConfig();
    }

    public float getFlashstepPowerIncrease(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".FSPowerIncrease");
    }

    public void setFlashstepPowerIncrease(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".FSPowerIncrease", num);
        plConfig.saveConfig();
    }

    public float getFlashstepCDReduct(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".FSCooldownReduct");
    }

    public void setFlashstepCDReduct(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".FSCooldownReduct", num);
        plConfig.saveConfig();
    }

    public float getHeavyAttackCDReduct(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".HeavyAttackCDReduct");
    }

    public void setHeavyAttackCDReduct(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".HeavyAttackCDReduct", num);
        plConfig.saveConfig();
    }

    public float getBlockingCDReduct(){
        return (float) plConfig.getConfig().getDouble("players." + player.getUniqueId() + ".BlockingCDReduct");
    }

    public void setBlockingCDReduct(float num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".BlockingCDReduct", num);
        plConfig.saveConfig();
    }

    public int getSkillPoint(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".skillPoints");
    }

    public void setSkillPoints(int num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".skillPoints", num);
        plConfig.saveConfig();
    }

    public int getFlashStepReductionCost(){
        return plConfig.getConfig().getInt("players." + player.getUniqueId() + ".FSReductionCost");
    }

    public void setFlashStepReductionCost(int num){
        plConfig.getConfig().set("players." + player.getUniqueId() + ".FSReductionCost", num);
        plConfig.saveConfig();
    }




    /*
    Utils
     */

    private boolean checkCooldown(Player player, double cooldownSeconds, Map<UUID, Double> cooldowns) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            double lastUse = cooldowns.get(player.getUniqueId());
            double currentTime = System.currentTimeMillis() / 1000.0; // Convert to seconds

            if (currentTime - lastUse < cooldownSeconds) {
                // Still in cooldown
                return false;
            }
        }

        return true;
    }

    private void updateCooldown(Player player, Map<UUID, Double> cooldowns) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000.0);
    }
}
