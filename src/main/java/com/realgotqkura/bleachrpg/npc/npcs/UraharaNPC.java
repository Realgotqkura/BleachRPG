package com.realgotqkura.bleachrpg.npc.npcs;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.utils.BleachFaction;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.Debug.DebugUtils;
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
import java.util.concurrent.ThreadLocalRandom;

public class UraharaNPC implements Listener {

    private NPC npc;

    private static String npcName = "";

    public static final List<String> SRLinesBeforeQuestion = new ArrayList<>(Arrays.asList(new String[] { RandomUtils.color("&fHey there, I see you are interested in becoming a Soul Reaper"),
            RandomUtils.color("&fNow I'm not one to help with such things, but I will make an exception."),
            RandomUtils.color("&fBut first you would have to answer one of my questions :)))"),
            RandomUtils.color("&fWhat is..."),
            RandomUtils.color("&f9+10?") }));

    public static final List<String> SRLinesAfterQuestion = new ArrayList<>(Arrays.asList(new String[] { RandomUtils.color("&foooo, good job!"),
            RandomUtils.color("&fNow, it's going to hurt so yeah..."),
            RandomUtils.color("&f3"),
            RandomUtils.color("&f2"),
            RandomUtils.color("&f1..."),
            RandomUtils.color("&faaand done!"),
            RandomUtils.color("&fCongrats! You are a soul reaper now."),
            RandomUtils.color("&fto learn more do /bleachrpg_tutorial") }));

    private static HashMap<Player, Boolean> isInDialogue = new HashMap<>();

    public UraharaNPC(Location loc) {
        npcName = BleachRPG.instance.getConfig().getString("Urahara.Name");
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        this.npc.spawn(loc);
        SkinTrait skin = this.npc.getTraitNullable(SkinTrait.class);
        skin.setSkinName("KisukeUrahara");
    }

    public UraharaNPC() {
        npcName = BleachRPG.instance.getConfig().getString("Urahara.Name");
    }

    public NPC getNpc() {
        return this.npc;
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        if (!event.getNPC().getName().contains(ChatColor.stripColor(npcName)))
            return;
        Player player = event.getClicker();

        if (isInDialogue.get(player) == null)
            isInDialogue.put(player, false);

        if ((isInDialogue.get(player)))
            return;

        if (player.getInventory().getItemInMainHand() == null)
            return;

        if (!player.getInventory().getItemInMainHand().hasItemMeta())
            return;

        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(RandomUtils.color("&aNPC Deleter"))) {
            CitizensAPI.getNPCRegistry().getNPC(event.getNPC().getEntity()).destroy();
            player.sendMessage(RandomUtils.color("&aSuccessfully deleted " + npcName));
        }
    }

    @EventHandler
    public void clickDialogue(NPCRightClickEvent event) {
        if (!event.getNPC().getName().equals(ChatColor.stripColor(npcName)))
            return;
        Player player = event.getClicker();

        if (isInDialogue.get(player) == null)
            isInDialogue.put(player, Boolean.valueOf(false));

        if ((isInDialogue.get(player)).booleanValue())
            return;

        FileConfiguration config = BleachRPG.instance.getConfig();
        FileConfiguration plConfig = BleachRPG.playerConf.getConfig();
        List<String> beforeQuestionLines = Arrays.asList(RandomUtils.processVoiceLines(config.getStringList("Urahara.SoulReaperLinesBeforeQuestion"), player));
        int speed = config.getInt("Urahara.SoulReaperLinesSpeedInTicks");
        BleachItems items = new BleachItems();
        if (!plConfig.getBoolean("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion")) {
            isInDialogue.put(player, Boolean.valueOf(true));
            (new BukkitRunnable() {
                int index = 0;

                public void run() {
                    player.sendMessage(RandomUtils.color("&e[" + UraharaNPC.npcName + "&e]: " + beforeQuestionLines.get(this.index)));
                    this.index++;
                    if (this.index == beforeQuestionLines.size()) {
                        UraharaNPC.isInDialogue.put(player, Boolean.valueOf(false));

                        if (!DebugUtils.TEST_MODE)
                            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion", Boolean.valueOf(true));

                        if (config.getBoolean("Urahara.SoulReaperLinesNoQuestion")) {
                            RandomUtils.addItemIntoPlInventory(player, items.getSpecificItem("SubstituteShinigamiBadge"), 8);
                            RandomUtils.sendMultipleAnimTitlesNoTransform(new String[] { RandomUtils.color("&7&lYou are now a Soul Reaper!") }, new String[] { "" }, 1, BleachRPG.instance, player, 20);
                            SoundAndEffectsUtils.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F);
                            SoundAndEffectsUtils.makeParticlePrecise(player.getLocation(), Particle.FIREWORKS_SPARK, 10);
                            plConfig.set("players." + player.getUniqueId() + ".faction", Integer.valueOf(BleachFaction.SHINIGAMI.toInt()));
                            BleachRPG.playerConf.saveConfig();
                        } else {
                            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion", Boolean.valueOf(true));
                            BleachRPG.playerConf.saveConfig();
                        }
                        cancel();
                    }
                }
            }).runTaskTimer(BleachRPG.instance, 1L, speed);
        }
    }

    @EventHandler
    public void answerQuestion(AsyncPlayerChatEvent event) {
        FileConfiguration plConfig = BleachRPG.playerConf.getConfig();
        Player player = event.getPlayer();

        if (!plConfig.getBoolean("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion"))
            return;

        event.setCancelled(true);
        String answer = BleachRPG.instance.getConfig().getString("Urahara.SoulReaperLinesAnswer");
        String msg = event.getMessage().trim();
        BleachItems items = new BleachItems();
        int speed = BleachRPG.instance.getConfig().getInt("Urahara.SoulReaperLinesSpeedInTicks");
        player.sendMessage(RandomUtils.color("&e[&c" + player.getName() + "&e]: &f" + event.getMessage()));
        List<String> afterQuestionLines = Arrays.asList(RandomUtils.processVoiceLines(BleachRPG.instance.getConfig().getStringList("Urahara.SoulReaperLinesAfterQuestion"), player));
        if (answer.equals(msg)) {
            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaAnsweringQuestion", Boolean.valueOf(false));
            BleachRPG.playerConf.saveConfig();
            isInDialogue.put(player, Boolean.valueOf(true));
            (new BukkitRunnable() {
                int index = 0;

                public void run() {
                    player.sendMessage(RandomUtils.color("&e[" + UraharaNPC.npcName + "&e]: " + afterQuestionLines.get(this.index)));
                    this.index++;
                    if (this.index == afterQuestionLines.size()) {
                        UraharaNPC.isInDialogue.put(player, Boolean.valueOf(false));
                        RandomUtils.addItemIntoPlInventory(player, items.getSpecificItem("SubstituteShinigamiBadge"), 8);
                        RandomUtils.sendMultipleAnimTitlesNoTransform(new String[] { RandomUtils.color("&7&lYou are now a Soul Reaper!") }, new String[] { "" }, 1, BleachRPG.instance, player, 20);
                        SoundAndEffectsUtils.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F);
                        SoundAndEffectsUtils.makeParticlePrecise(player.getLocation(), Particle.FIREWORKS_SPARK, 10);
                        plConfig.set("players." + player.getUniqueId() + ".faction", Integer.valueOf(BleachFaction.SHINIGAMI.toInt()));
                        int rngSpirit = ThreadLocalRandom.current().nextInt(0, BleachUtils.zanpakutoSpiritList.size());
                        plConfig.set("players." + player.getUniqueId() + ".spirit", BleachUtils.zanpakutoSpiritList.get(rngSpirit));
                        player.damage(player.getHealth() - 1.0D);
                        BleachRPG.playerConf.saveConfig();
                        cancel();
                    }
                }
            }).runTaskTimer(BleachRPG.instance, 1L, speed);
        } else {
            plConfig.set("players." + player.getUniqueId() + ".Dialogues.UraharaBeforeQuestion", Boolean.valueOf(false));
            player.sendMessage(RandomUtils.color("&e[" + npcName + "&e]:" + BleachRPG.instance.getConfig().getString("Urahara.SoulReaperLinesAnsnwerWrongLine")));
        }
    }
}
