package com.realgotqkura.bleachrpg.utils;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class LangUtils {

    private static HashMap<String, String> messages = new HashMap<>();
    private static HashMap<String, File> s_langs = new HashMap<>();

    public static void deleteLangFiles(){
        for(File file : s_langs.values()){
            if(file.exists()){
                file.delete();
            }
        }
        s_langs.clear();
    }


    public static HashMap<String, File> loadLangFiles(){
        File langFolder = new File(BleachRPG.instance.getDataFolder(), "/languages");
        JLogger.log(JLogSeverity.DEBUG, BleachRPG.instance.getDataFolder().getPath());

        if(!langFolder.exists()){
            langFolder.mkdir();
        }

        HashMap<String,File> langs = new HashMap<>();
        langs.put("en" ,new File(langFolder, "en.yml"));
        langs.put("es" ,new File(langFolder, "es.yml"));
        s_langs = langs;

        for(File lang : langs.values()){
            try{
                if(!lang.exists()){
                    InputStream stream = BleachRPG.instance.getResource(lang.getName());
                    Files.copy(stream, lang.toPath());
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return langs;
    }

    public static void fillLangMap(BleachRPG plugin){
        loadLangFiles();
        if(!messages.isEmpty()){
            messages.clear();
        }

        String selectedLang = plugin.getConfig().getString("pluginLanguage");
        FileConfiguration conf = YamlConfiguration.loadConfiguration(s_langs.get(selectedLang));

        for(String key : conf.getKeys(false)){
            for(String messName : conf.getConfigurationSection(key).getKeys(false)){
                messages.put(messName, ChatColor.translateAlternateColorCodes('&', conf.getString(key + "." + messName)));
            }
        }
    }

    public static String getMessage(String key){
        return messages.get(key);
    }
}
