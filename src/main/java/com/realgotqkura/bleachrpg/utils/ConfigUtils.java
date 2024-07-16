package com.realgotqkura.bleachrpg.utils;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.npc.npcs.UraharaNPC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class ConfigUtils {

    private BleachRPG plugin;


    public ConfigUtils(BleachRPG plugin){
        this.plugin = plugin;
    }

    public void checkDefaultValues(){
        //Cooldowns
        checkDoubleIfNull("flashStepCD", 2.5);
        checkDoubleIfNull("blockingCDAfterBreak", 5);
        checkDoubleIfNull("heavyAttackCD", 3);
        checkDoubleIfNull("Zangetsu.getsugaTenshouCD", 10);
        checkDoubleIfNull("Zangetsu.getsugaTaihoCD", 7.5);
        checkDoubleIfNull("Zangetsu.getsugaJujishoCD", 10);

        //Stuff
        checkIntIfNull("regularZanpakutoDamage", 6);
        checkIntIfNull("regularZanpakutoReiatsu", 50);
        checkDoubleIfNull("defaultReiatsuCount", 100);
        checkDoubleIfNull("defaultReiatsuRegen", 1.1);
        checkDoubleIfNull("heavyAttackDamageMultiplier", 1.25d);

        //AbilityDamage
        checkIntIfNull("Zangetsu.getsugaTenshouDamage", 6);
        checkIntIfNull("Zangetsu.getsugaTaihoDamage", 3);
        checkIntIfNull("Zangetsu.getsugaJujishoDamage", 9);

        //Zangetsu
        checkIntIfNull("Zangetsu.shikaiZanpakutoDamage", 7);
        checkIntIfNull("Zangetsu.bankaiZanpakutoDamage", 9);
        checkIntIfNull("Zangetsu.shikaiZanpakutoReiatsu", 70);
        checkIntIfNull("Zangetsu.bankaiZanpakutoReiatsu", 100);
        checkDoubleIfNull("Zangetsu.speedPotEffectAmp", 1);
        checkDoubleIfNull("Zangetsu.strengthPotEffectAmp", 0);

        //Reiatsu Costs
        checkIntIfNull("flashStepReiatsuCost", 10);
        checkIntIfNull("Zangetsu.getsugaTenshouCost", 35);
        checkIntIfNull("Zangetsu.getsugaTaihoCost", 40);
        checkIntIfNull("Zangetsu.getsugaJujishoCost", 45);


        //Ability Specifications
        checkIntIfNull("Zangetsu.getsugaTaihoRadius", 4);

        checkStringIfNull("pluginLanguage", "en");
        checkIntIfNull("defaultMeditationXPPerSec", 1);


        //NPC Dialogues and stuff
        checkStringIfNull("Urahara.Name", ChatColor.of("#285e36") + "" + ChatColor.BOLD + "Urahara");
        checkStringListIfNull("Urahara.SoulReaperLinesBeforeQuestion", UraharaNPC.SRLinesBeforeQuestion);
        checkStringListIfNull("Urahara.SoulReaperLinesAfterQuestion", UraharaNPC.SRLinesAfterQuestion);
        checkStringIfNull("Urahara.SoulReaperLinesAnswer", "19");
        checkStringIfNull("Urahara.SoulReaperLinesAnsnwerWrongLine", "&fUnlucky... Talk to me later.");
        checkBooleanIfNull("Urahara.SoulReaperLinesNoQuestion", false);
    }



    public int getDamage(String zanpakuto, ShinigamiStage stage){
        if(stage == ShinigamiStage.REGULAR){
            return plugin.getConfig().getInt("regularZanpakutoDamage");
        }

        if(zanpakuto.equalsIgnoreCase("zangetsu")){
            switch (stage){
                case SHIKAI:
                    return plugin.getConfig().getInt("Zangetsu.shikaiZanpakutoDamage");
                case BANKAI:
                    return plugin.getConfig().getInt("Zangetsu.bankaiZanpakutoDamage");

            }
        }

        return 0;
    }

    public int getReiatsu(String zanpakuto, ShinigamiStage stage){
        if(stage == ShinigamiStage.REGULAR){
            return plugin.getConfig().getInt("regularZanpakutoReiatsu");
        }

        if(zanpakuto.equalsIgnoreCase("zangetsu")){
            switch (stage){
                case SHIKAI:
                    return plugin.getConfig().getInt("Zangetsu.shikaiZanpakutoReiatsu");
                case BANKAI:
                    return plugin.getConfig().getInt("Zangetsu.bankaiZanpakutoReiatsu");

            }
        }

        return 0;
    }

    /**
     *
     * @param configLine - Gets the config string ex: 'regularZanpakutoDamage'
     * @param defaultAmount - it applies the default amount set by the developer;
     * @return
     *
     *
     */
    private int checkIntIfNull(String configLine, int defaultAmount){
        FileConfiguration config = plugin.getConfig();
        if(config.get(configLine) == null){
            config.set(configLine, defaultAmount);
            plugin.saveConfig();
            return config.getInt(configLine);
        }

        return config.getInt(configLine);
    }

    private List<String> checkStringListIfNull(String configLine, List<String> defaultArray){
        FileConfiguration config = plugin.getConfig();
        if(config.get(configLine) == null){
            config.set(configLine, defaultArray);
            plugin.saveConfig();
            return (List<String>) config.getList(configLine);
        }

        return (List<String>) config.getList(configLine);
    }

    private double checkDoubleIfNull(String configLine, double defaultAmount){
        FileConfiguration config = plugin.getConfig();
        if(config.get(configLine) == null){
            config.set(configLine, defaultAmount);
            plugin.saveConfig();
            return config.getDouble(configLine);
        }

        return config.getDouble(configLine);
    }

    private boolean checkBooleanIfNull(String configline, boolean defaultValue){
        FileConfiguration config = plugin.getConfig();
        if(config.get(configline) == null){
            config.set(configline, defaultValue);
            plugin.saveConfig();
            return config.getBoolean(configline);
        }

        return config.getBoolean(configline);
    }

    private String checkStringIfNull(String configline, String defaultValue){
        FileConfiguration config = plugin.getConfig();
        if(config.get(configline) == null){
            config.set(configline, defaultValue);
            plugin.saveConfig();
            return config.getString(configline);
        }

        return config.getString(configline);
    }
}
