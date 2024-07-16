package com.realgotqkura.bleachrpg.npc.npcs;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.BleachFaction;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UraharaNPC implements Listener {

    private NPC npc;
    private static String npcName = "";

    public static final List<String> SRLinesBeforeQuestion = new ArrayList<>(Arrays.asList(
            RandomUtils.color("&fHey there, I see you are interested in becoming a Soul Reaper"),
            RandomUtils.color("&fNow I'm not one to help with such things, but I will make an exception."),
            RandomUtils.color("&fBut first you would have to answer one of my questions :)))"),
            RandomUtils.color("&fWhat is..."),
            RandomUtils.color("&f9+10?")
    ));

    public static final List<String> SRLinesAfterQuestion = new ArrayList<>(Arrays.asList(
            RandomUtils.color("&foooo, good job!"),
            RandomUtils.color("&fNow, it's going to hurt so yeah..."),
            RandomUtils.color("&f3"),
            RandomUtils.color("&f2"),
            RandomUtils.color("&f1..."),
            RandomUtils.color("&faaand done!"),
            RandomUtils.color("&fCongrats! You are a soul reaper now."),
            RandomUtils.color("&fto learn more do /bleachrpg_tutorial")
    ));

    private static HashMap<Player, Boolean> isInDialogue = new HashMap<>();

    public UraharaNPC(Location loc){
        npcName = BleachRPG.instance.getConfig().getString("Urahara.Name");
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.spawn(loc);
        SkinTrait skin = npc.getTraitNullable(SkinTrait.class);
        skin.setSkinName("KisukeUrahara");

    }

    //For the initialization
    public UraharaNPC(){};

    public NPC getNpc(){
        return npc;
    }

    @EventHandler
    public void click(NPCRightClickEvent event){
        if(!event.getNPC().getName().equals(ChatColor.stripColor(npcName)))
            return;

        Player player = event.getClicker();

        if(isInDialogue.get(player) == null)
            isInDialogue.put(player, false);

        if(isInDialogue.get(player))
            return;

        if(player.getInventory().getItemInMainHand() == null)
            return;

        if(!player.getInventory().getItemInMainHand().hasItemMeta())
            return;

        if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(RandomUtils.color("&aNPC Deleter"))){
            CitizensAPI.getNPCRegistry().getNPC(event.getNPC().getEntity()).destroy();
            player.sendMessage(RandomUtils.color("&aSuccessfully deleted " + npcName));
        }
    }

    @EventHandler
    public void clickDialogue(NPCRightClickEvent event){
        if(!event.getNPC().getName().equals(ChatColor.stripColor(npcName)))
            return;

        Player player = event.getClicker();

        if(isInDialogue.get(player) == null)
            isInDialogue.put(player, false);

        if(isInDialogue.get(player))
            return;


        FileConfiguration config = BleachRPG.instance.getConfig();
        FileConfiguration plConfig = BleachRPG.playerConf.getConfig();
        List<String> beforeQuestionLines = (List<String>) config.getList("Urahara.SoulReaperLinesBeforeQuestion");
        BleachItems items = new BleachItems();

        if(!plConfig.getBoolean("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion")){
            isInDialogue.put(player, true);
            new BukkitRunnable(){

                int index = 0;
                @Override
                public void run() {
                    player.sendMessage(RandomUtils.color("&e[" + npcName + "&e]: " + beforeQuestionLines.get(index)));
                    index++;

                    if(index == beforeQuestionLines.size()){
                        isInDialogue.put(player, false);
                        plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion", true);

                        if(config.getBoolean("Urahara.SoulReaperLinesNoQuestion")){
                            //Make him soul reaper
                            RandomUtils.addItemIntoPlInventory(player, items.getSpecificItem("SubstituteShinigamiBadge"), 8);
                            RandomUtils.sendMultipleAnimTitlesNoTransform(new String[]{RandomUtils.color("&7&lYou are now a Soul Reaper!")},
                                    new String[]{""}, 1, BleachRPG.instance, player, 20);
                            SoundAndEffectsUtils.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1);
                            SoundAndEffectsUtils.makeParticlePrecise(player.getLocation(), Particle.FIREWORKS_SPARK, 10);
                            plConfig.set("players." + player.getUniqueId() + ".faction", BleachFaction.SHINIGAMI.toInt());
                            BleachRPG.playerConf.saveConfig();

                        }else{
                            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion", true);
                            BleachRPG.playerConf.saveConfig();
                        }

                        cancel();
                    }
                }

            }.runTaskTimer(BleachRPG.instance, 1, 70);
            return;
        }
    }


    @EventHandler
    public void answerQuestion(AsyncPlayerChatEvent event){
        FileConfiguration plConfig = BleachRPG.playerConf.getConfig();

        Player player = event.getPlayer();
        if(!plConfig.getBoolean("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion"))
            return;

        event.setCancelled(true);
        String answer = BleachRPG.instance.getConfig().getString("Urahara.SoulReaperLinesAnswer");
        String msg = event.getMessage().trim();
        BleachItems items = new BleachItems();

        List<String> afterQuestionLines = (List<String>) BleachRPG.instance.getConfig().getList("Urahara.SoulReaperLinesAfterQuestion");
        if(answer.equals(msg)){
            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion", false);
            BleachRPG.playerConf.saveConfig();
            isInDialogue.put(player, true);
            new BukkitRunnable(){

                int index = 0;
                @Override
                public void run() {
                    player.sendMessage(RandomUtils.color("&e[" + npcName + "&e]: " + afterQuestionLines.get(index)));
                    index++;

                    if(index == afterQuestionLines.size()){
                        isInDialogue.put(player, false);
                        RandomUtils.addItemIntoPlInventory(player, items.getSpecificItem("SubstituteShinigamiBadge"), 8);
                        RandomUtils.sendMultipleAnimTitlesNoTransform(new String[]{RandomUtils.color("&7&lYou are now a Soul Reaper!")},
                                new String[]{""}, 1, BleachRPG.instance, player, 20);
                        SoundAndEffectsUtils.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1);
                        SoundAndEffectsUtils.makeParticlePrecise(player.getLocation(), Particle.FIREWORKS_SPARK, 10);
                        plConfig.set("players." + player.getUniqueId() + ".faction", BleachFaction.SHINIGAMI.toInt());
                        player.damage(player.getHealth()-1);
                        BleachRPG.playerConf.saveConfig();
                        cancel();
                    }
                }

            }.runTaskTimer(BleachRPG.instance, 1, 60);
        }else{
            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion", false);
            player.sendMessage(RandomUtils.color("&e[" + npcName + "&e]:" + BleachRPG.instance.getConfig().getString("Urahara.SoulReaperLinesAnsnwerWrongLine")));
        }
    }
}
