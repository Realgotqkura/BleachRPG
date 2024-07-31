package com.realgotqkura.bleachrpg.utils.voicelines;

import com.realgotqkura.bleachrpg.utils.RandomUtils;

import java.util.Arrays;
import java.util.List;

public class BankaiVoiceLines {


    public String[] zangetsuVoicelines = new String[]{
            RandomUtils.color("&c&kEE &8&lこの瞬間、すべてが変わります。&c&kEE"),
            RandomUtils.color("&c&kEE &8&lスピードが私の本質となり、 &c&kEE"),
            RandomUtils.color("&c&kEE &8&l強さ、私の信念。&c&kEE"),
            RandomUtils.color("&c&kEE &4*l卍解, 天鎖斬月 &c&kEE")
    };

    public String[] zangetsuVLTranslated = new String[]{
            RandomUtils.color("&7In this moment, everything changes."),
            RandomUtils.color("&7Speed becomes my essence,"),
            RandomUtils.color("&7Strength, my conviction"),
            RandomUtils.color("&4&lBANKAI, &7Tensa Zangetsu")
    };


    public List<String[]> getVoiceLines(String zanpakuto){
        switch (zanpakuto.toLowerCase()){
            case "zangetsu":
                return Arrays.asList(zangetsuVoicelines, zangetsuVLTranslated);
            default:
                return null;

        }
    }
}
