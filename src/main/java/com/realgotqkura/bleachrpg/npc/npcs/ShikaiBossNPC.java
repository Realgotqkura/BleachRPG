package com.realgotqkura.bleachrpg.npc.npcs;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachRegion;
import com.realgotqkura.bleachrpg.utils.objectclasses.spirits.ZanpakutoSpirits;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SitTrait;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.trait.SleepTrait;
import net.citizensnpcs.trait.SneakTrait;
import net.citizensnpcs.util.NMS;
import net.citizensnpcs.util.PlayerAnimation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Bed;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcmonkey.sentinel.SentinelPlugin;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShikaiBossNPC implements Listener {

    private NPC npc;

    private static String npcName = "";

    private static BossBar currentBossBar;
    private BleachRPG plugin;
    private PlayerConfig plConfig;
    private static Location beforeFightLoc;

    private static Player killer = null;

    private static HashMap<Player, Boolean> isInDialogue = new HashMap<>();


    public ShikaiBossNPC(Location loc, ZanpakutoSpirits spirit, BleachRPG plugin, Location beforeFightLoc) {
        this.plugin = plugin;
        this.plConfig = BleachRPG.playerConf;
        this.beforeFightLoc = beforeFightLoc;
        npcName = pickNPCName(spirit);
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        this.npc.spawn(loc);
        //this.npc.getEntity().setMetadata("Sentinel", new FixedMetadataValue(BleachRPG.instance, true));
        SkinTrait skin = this.npc.getTraitNullable(SkinTrait.class);
        skin.setSkinPersistent("Zangetsu",
                "ECkcUo/RzH4fEmWwneYVAX6xYfO9R8RRORzwFwbiC0yaVlyF2MoP+Pw2JMM63uVrE16iLYLkRRoJMdH7OS7S5NxsA0XmCOS8au8OcS2Z0qiFKOL7owDVivG04fJg8lXvJb90gJ9PNe8/6IRWpwel7fBmrERFxSunxrG9P/+Qa383VESgpllDPxXPt7NzZbxHvDUgYAga9b4BxWDhDmP1Fh1+lt50Rp+qbbj1uAQGhck2k62AlSzbWVPlIqt/tCGmF0ZCJjCBCOwVUhy3H6it0KGBtFJ8/6FP9OFjnbuSE564fb89qdMcdGIqD3naM3bE0g79a2v0V4piBTDNQeNUVL3Wbie73cpskFGCp7ESXXpcF0hvUPTv36KyYSct8b9Wsu6aNHxvl8U3F+Cb49v0pVCwV0kmH78fkowXOuXif16fPQVFjEoI1Xy8kXBVNXNzOucfXivBqY7S4OGUK7BpeQ+39TmBPQmPHNbHtTM9oohMWReK99OH5y/NseWmAWelZi47DtbqCYPWYpgfgYLdWlZeF2GrJr0l76ypRS+pULxDHfsdk/5nxDwF6qHd7txqCm6YmrtMbyD0wlVtfqc7QvBOZSIFjRkN5xy1qFjsFjP7CkcnZhUFtXwkROLVjH2htcIlfHdKOc8YIgbfCpRpK+yp356G5yCVcdldPO5Yeek=",
                "ewogICJ0aW1lc3RhbXAiIDogMTcyMjAwODMyNTA4NCwKICAicHJvZmlsZUlkIiA6ICI5NTQ1NGYzOTkwNGE0MDg0OTcxMTQ0Y2Q5MmRhY2MyYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHcDA4MTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTUyNGUyMWRkMTdjNjA0M2RmMTZlM2JiMGJlYzdmYjVmOTA3Mjg1NzM0MDEwY2YzYjI5MzRkNjU1ZDhmOWUzNiIKICAgIH0KICB9Cn0=");

    }

    public ShikaiBossNPC(){
        this.plugin = BleachRPG.instance;
        this.plConfig = BleachRPG.playerConf;
    }

    @EventHandler
    public void playerDeath(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if(!plugin.getConfig().getBoolean("Arena.isBusy"))
            return;

        if(!plugin.getConfig().getString("Arena.playerFighting").equals(player.getName()))
            return;

        if ((player.getHealth() - event.getFinalDamage()) > 0)
            return;

        if(isInDialogue.get(player))
            return;

        event.setCancelled(true);

        int speed = plugin.getConfig().getInt("Zangetsu.shikaiVoiceLinesAfterFightSpeedInTicks");
        String[] loseFightVoiceLines = RandomUtils.processVoiceLines(plugin.getConfig().getStringList("Zangetsu.shikaiLinesAIWinFight"), player);
        npc.removeTrait(SentinelTrait.class);
        npc.addTrait(SitTrait.class);
        SitTrait sit = npc.getTraitNullable(SitTrait.class);
        sit.setSitting(npc.getStoredLocation());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*60, 10, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*60, 1, false, false, false));
        isInDialogue.put(player, true);
        new BukkitRunnable(){

            int index = 0;
            @Override
            public void run() {
                if(index >= loseFightVoiceLines.length){
                    isInDialogue.put(player, false);
                    player.teleport(beforeFightLoc);
                    CitizensAPI.getNPCRegistry().getById(npc.getId()).destroy();
                    plConfig.getConfig().set("players." + player.getUniqueId() + ".Dialogues.ZangetsuHeardBeforeFightVL", false);
                    BleachRPG.playerConf.saveConfig();
                    plugin.getConfig().set("Arena.isBusy", false);
                    plugin.getConfig().set("Arena.playerFighting", "");
                    plugin.saveConfig();
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.setHealth(player.getMaxHealth());
                    currentBossBar.removeAll();
                    cancel();
                    return;
                }
                player.sendMessage(RandomUtils.color("&e[&r" + npcName + "&e]: &r") + loseFightVoiceLines[index]);


                index++;
            }
        }.runTaskTimer(plugin, 1, speed);

    }

    @EventHandler
    public void shikaiBossDeath(EntityDeathEvent event){
        if(!event.getEntity().hasMetadata("NPC"))
            return;

        NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getEntity());


        if(!npc.getName().contains(ChatColor.stripColor(npcName)))
            return;


        Player player = event.getEntity().getKiller();
        NPC npc1 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc1.spawn(event.getEntity().getLocation());
        SkinTrait skin = npc1.getTraitNullable(SkinTrait.class);
        skin.setSkinPersistent("Zangetsu",
                "ECkcUo/RzH4fEmWwneYVAX6xYfO9R8RRORzwFwbiC0yaVlyF2MoP+Pw2JMM63uVrE16iLYLkRRoJMdH7OS7S5NxsA0XmCOS8au8OcS2Z0qiFKOL7owDVivG04fJg8lXvJb90gJ9PNe8/6IRWpwel7fBmrERFxSunxrG9P/+Qa383VESgpllDPxXPt7NzZbxHvDUgYAga9b4BxWDhDmP1Fh1+lt50Rp+qbbj1uAQGhck2k62AlSzbWVPlIqt/tCGmF0ZCJjCBCOwVUhy3H6it0KGBtFJ8/6FP9OFjnbuSE564fb89qdMcdGIqD3naM3bE0g79a2v0V4piBTDNQeNUVL3Wbie73cpskFGCp7ESXXpcF0hvUPTv36KyYSct8b9Wsu6aNHxvl8U3F+Cb49v0pVCwV0kmH78fkowXOuXif16fPQVFjEoI1Xy8kXBVNXNzOucfXivBqY7S4OGUK7BpeQ+39TmBPQmPHNbHtTM9oohMWReK99OH5y/NseWmAWelZi47DtbqCYPWYpgfgYLdWlZeF2GrJr0l76ypRS+pULxDHfsdk/5nxDwF6qHd7txqCm6YmrtMbyD0wlVtfqc7QvBOZSIFjRkN5xy1qFjsFjP7CkcnZhUFtXwkROLVjH2htcIlfHdKOc8YIgbfCpRpK+yp356G5yCVcdldPO5Yeek=",
                "ewogICJ0aW1lc3RhbXAiIDogMTcyMjAwODMyNTA4NCwKICAicHJvZmlsZUlkIiA6ICI5NTQ1NGYzOTkwNGE0MDg0OTcxMTQ0Y2Q5MmRhY2MyYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHcDA4MTAiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTUyNGUyMWRkMTdjNjA0M2RmMTZlM2JiMGJlYzdmYjVmOTA3Mjg1NzM0MDEwY2YzYjI5MzRkNjU1ZDhmOWUzNiIKICAgIH0KICB9Cn0=");

        npc1.addTrait(SleepTrait.class);
        SleepTrait trait = npc1.getTraitNullable(SleepTrait.class);
        trait.setSleeping(npc1.getStoredLocation());
        npc1.getEntity().setMetadata("Sleeping", new FixedMetadataValue(plugin, 1));
        npc1.setName("");

        int speed = plugin.getConfig().getInt("Zangetsu.shikaiVoiceLinesAfterFightSpeedInTicks");


        String[] winFightVoiceLines = RandomUtils.processVoiceLines(plugin.getConfig().getStringList("Zangetsu.shikaiLinesAILoseFight"), player);

        if(!isInDialogue.get(player)){
            isInDialogue.put(player, true);
            new BukkitRunnable(){

                int index = 0;
                @Override
                public void run() {
                    if(index >= winFightVoiceLines.length){
                        isInDialogue.put(player, false);
                        player.teleport(beforeFightLoc);
                        CitizensAPI.getNPCRegistry().getById(npc1.getId()).destroy();
                        plConfig.getConfig().set("players." + player.getUniqueId() + ".Dialogues.ZangetsuHeardBeforeFightVL", false);
                        BleachRPG.playerConf.saveConfig();
                        plugin.getConfig().set("Arena.isBusy", false);
                        plugin.getConfig().set("Arena.playerFighting", "");
                        plugin.saveConfig();
                        currentBossBar.removeAll();
                        cancel();
                        return;
                    }
                    player.sendMessage(RandomUtils.color("&e[&r" + npcName + "&e]: &r") + winFightVoiceLines[index]);


                    index++;
                }
            }.runTaskTimer(plugin, 1, speed);
        }


    }


    @EventHandler
    public void rc(NPCRightClickEvent event){
        if (!event.getNPC().getName().contains(ChatColor.stripColor(npcName)))
            return;

        Player player = event.getClicker();

        if(isInDialogue.get(player) == null)
            isInDialogue.put(player, false);

        if(isInDialogue.get(player))
            return;

        if(plConfig.getConfig().getBoolean("players." + player.getUniqueId() + ".Dialogues.ZangetsuHeardBeforeFightVL"))
            return;

        int speed = plugin.getConfig().getInt("Zangetsu.shikaiVoiceLinesBeforeFightSpeedInTicks");


        String[] beforeFightVoiceLines = RandomUtils.processVoiceLines(plugin.getConfig().getStringList("Zangetsu.shikaiLinesBeforeFight"), player);
        isInDialogue.put(player, true);

        plugin.getConfig().set("Arena.isBusy", true);
        plugin.getConfig().set("Arena.playerFighting", player.getName());
        plugin.saveConfig();
        new BukkitRunnable(){

            int index = 0;
            @Override
            public void run() {
                if(index >= beforeFightVoiceLines.length){
                    isInDialogue.put(player, false);
                    plConfig.getConfig().set("players." + player.getUniqueId() + ".Dialogues.ZangetsuHeardBeforeFightVL", true);
                    BleachRPG.playerConf.saveConfig();
                    npc.addTrait(SentinelTrait.class);
                    SentinelTrait sentinel = npc.getOrAddTrait(SentinelTrait.class);
                    npc.getEntity().setMetadata("Shikai", new FixedMetadataValue(plugin, 1));
                    sentinel.addTarget("uuid:" + player.getUniqueId());
                    sentinel.respawnTime = -1;
                    sentinel.attackRate = 5;
                    sentinel.avoidRange = 0.3f;
                    sentinel.chasing = player;
                    sentinel.healRate = 0;
                    sentinel.setHealth(plugin.getConfig().getInt("shikaiHealth"));
                    sentinel.speed = 3;
                    sentinel.accuracy = 1;
                    Equipment trait = npc.getOrAddTrait(Equipment.class);
                    trait.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
                    sentinel.damage = 2;
                    sentinel.range = 10;
                    npc = event.getNPC();

                    BossBar bossbar = Bukkit.createBossBar(npcName, BarColor.RED, BarStyle.SEGMENTED_6);
                    bossbar.addPlayer(player);
                    currentBossBar = bossbar;

                    cancel();
                    return;
                }
                player.sendMessage(RandomUtils.color("&e[&r" + npcName + "&e]: &r") + beforeFightVoiceLines[index]);


                index++;
            }


        }.runTaskTimer(plugin, 1, speed);

        this.npc = event.getNPC();
    }

    private String pickNPCName(ZanpakutoSpirits spirit){
        switch (spirit){
            case ZANGETSU:
                return RandomUtils.color(plugin.getConfig().getString("Zangetsu.Name"));
            case SENBONZAKURA:
                return RandomUtils.color("&c&k: " + ChatColor.of("#f274cf") + "Senbonzakura &c&k:");

        }
        return "No Name";
    }

    public NPC getNpc() {
        return this.npc;
    }

    @EventHandler
    public void damage(NPCDamageByEntityEvent event){
        int maxHealth = plugin.getConfig().getInt("shikaiHealth");

        if(!npc.isSpawned())
            return;

        if(!npc.getEntity().hasMetadata("Shikai"))
            return;

        SentinelTrait trait = npc.getTraitNullable(SentinelTrait.class);


        double percent = (trait.getLivingEntity().getHealth() - event.getDamage()) / maxHealth;
        Bukkit.broadcastMessage("P: " + percent + ", HP: " + trait.health + ", MAX: " + maxHealth);
        currentBossBar.setProgress(percent);
        List<Player> currentPlayers = currentBossBar.getPlayers();
        currentBossBar.removeAll();
        currentPlayers.forEach(e ->
                currentBossBar.addPlayer(e));


    }

}
