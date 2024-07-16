package com.realgotqkura.bleachrpg;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.realgotqkura.bleachrpg.JLogger.JLogSeverity;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.commands.DebugCommands;
import com.realgotqkura.bleachrpg.commands.GeneralCmds;
import com.realgotqkura.bleachrpg.commands.LanguageCommands;
import com.realgotqkura.bleachrpg.commands.ShinigamiCommands;
import com.realgotqkura.bleachrpg.commands.tabcompleters.DebugTabCompleters;
import com.realgotqkura.bleachrpg.commands.tabcompleters.GeneralTabComp;
import com.realgotqkura.bleachrpg.commands.tabcompleters.LanguageTabComp;
import com.realgotqkura.bleachrpg.commands.tabcompleters.ShinigamiTabComp;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.configs.XPConfig;
import com.realgotqkura.bleachrpg.events.general.*;
import com.realgotqkura.bleachrpg.events.otheritems.SoulReaperBadge;
import com.realgotqkura.bleachrpg.events.zanpakuto.zangetsu.ZangetsuEvents;
import com.realgotqkura.bleachrpg.guis.MeditationGUI;
import com.realgotqkura.bleachrpg.guis.SpiritualLevelInv;
import com.realgotqkura.bleachrpg.guis.TutorialGUI;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.items.guispecific.SpiritualLevelItems;
import com.realgotqkura.bleachrpg.npc.NPCCommands;
import com.realgotqkura.bleachrpg.npc.NPCCommandsTC;
import com.realgotqkura.bleachrpg.npc.npcs.UraharaNPC;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.ConfigUtils;
import com.realgotqkura.bleachrpg.utils.Debug.DebugUtils;
import com.realgotqkura.bleachrpg.utils.LangUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class BleachRPG extends JavaPlugin {

    public static BleachRPG instance; //<- Only for special cases
    private static ProtocolManager protocolManager;
    public static PlayerConfig playerConf;
    private XPConfig xpConfig;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        this.playerConf = new PlayerConfig(this);
        this.xpConfig = new XPConfig(this);
        xpConfig.saveDefaultConfig();

        this.protocolManager = ProtocolLibrary.getProtocolManager();

        RandomUtils.createNSK(this);
        ConfigUtils confUtils = new ConfigUtils(this);
        confUtils.checkDefaultValues();

        loadCmds();
        registerEvents();

        LangUtils.fillLangMap(this);
        BleachUtils.fillXPMap();

        //Load ItemMap
        BleachItems bl_items = new BleachItems();
        bl_items.loadItemMap();
        ReiatsuEvents reiatsu = new ReiatsuEvents();
        reiatsu.init(this);


        loadMaps();


        /**
         * TODO: Comment out following for public release
         */

        DebugUtils.makeDevCreative(this);

    }

    @Override
    public void onDisable() {
        LangUtils.deleteLangFiles();
        JLogger.log(JLogSeverity.INFO, "Disabling BleachRPG");
    }


    private void loadCmds(){
        this.getCommand("bleachlang").setExecutor(new LanguageCommands(this));
        this.getCommand("bleachlang").setTabCompleter(new LanguageTabComp());
        this.getCommand("getzanpakuto").setExecutor(new ShinigamiCommands(this));
        this.getCommand("getzanpakuto").setTabCompleter(new ShinigamiTabComp());
        this.getCommand("bleachrpg_tutorial").setExecutor(new GeneralCmds(this, playerConf));
        this.getCommand("bleachitem").setExecutor(new GeneralCmds(this, playerConf));
        this.getCommand("bleachitem").setTabCompleter(new GeneralTabComp());
        this.getCommand("bleachrpg_debug").setExecutor(new DebugCommands());
        this.getCommand("bleachrpg_debug").setTabCompleter(new DebugTabCompleters());
        this.getCommand("spiritualLvlgui").setExecutor(new GeneralCmds(this, playerConf));
        this.getCommand("meditate").setExecutor(new GeneralCmds(this, playerConf));
        this.getCommand("bleachnpcspawn").setExecutor(new NPCCommands());
        this.getCommand("bleachnpcspawn").setTabCompleter(new NPCCommandsTC());
    }

    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new TutorialGUI(this), this);
        this.getServer().getPluginManager().registerEvents(new NoCraftEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ReiatsuEvents(), this);
        this.getServer().getPluginManager().registerEvents(new GainXPEvents(this, xpConfig, playerConf), this);
        this.getServer().getPluginManager().registerEvents(new Blocking(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(this, playerConf), this);
        this.getServer().getPluginManager().registerEvents(new SpiritualLevelInv(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemEvents(),this);
        this.getServer().getPluginManager().registerEvents(new SoulReaperBadge(this), this);
        this.getServer().getPluginManager().registerEvents(new MeditationGUI(this, playerConf), this);

        //Abilities
        this.getServer().getPluginManager().registerEvents(new DashAndFS(this), this);
        this.getServer().getPluginManager().registerEvents(new ZangetsuEvents(this), this);


        //NPCS
        this.getServer().getPluginManager().registerEvents(new UraharaNPC(), this);
    }

    private void loadMaps(){
        SpiritualLevelItems.addIntoLoreList();
    }


    public static ProtocolManager getProtocolManager(){
        return protocolManager;
    }
}
