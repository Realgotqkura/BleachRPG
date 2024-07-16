package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.configs.XPConfig;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.ShinigamiStage;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class GainXPEvents implements Listener {

    XPConfig xpConfig;
    PlayerConfig plConfig;
    BleachRPG plugin;


    public GainXPEvents(BleachRPG plugin, XPConfig xpConfig, PlayerConfig plConfig){
        this.xpConfig = xpConfig;
        this.plConfig = plConfig;
        this.plugin = plugin;
    }

    @EventHandler
    public void hitEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            BleachPlayer player = new BleachPlayer((Player) event.getDamager());
            if(player.getShinigamiStage() == ShinigamiStage.HUMAN)
                return;

            event.getEntity().setMetadata("attacker", new FixedMetadataValue(plugin, player.getBukkitPlayer().getName()));
        }
    }

    @EventHandler
    public void killingMobs(EntityDeathEvent event){
        if(!event.getEntity().hasMetadata("attacker"))
            return;

        BleachPlayer bleachPlayer = new BleachPlayer(Bukkit.getPlayerExact(event.getEntity().getMetadata("attacker").get(0).asString()));
        int spiritualXPGained = xpConfig.getConfig().getInt("SPIRITUAL_XP_" + event.getEntity().getType().toString());
        bleachPlayer.gainSpiritualXP(spiritualXPGained);
        SoundAndEffectsUtils.playSound(bleachPlayer.getBukkitPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f);
        if(checkIfLvledUp(bleachPlayer)){
            levelUp(bleachPlayer);
        }
    }

    private boolean checkIfLvledUp(BleachPlayer player){
        return BleachUtils.getPlayerSpiritualXPNoOtherlvls(player.getBukkitPlayer()) >= BleachUtils.xpToLvlMap.get(player.getSpiritualLevel() + 1);
    }

    private void levelUp(BleachPlayer player){
        Player bukkitPlayer = player.getBukkitPlayer();
        SoundAndEffectsUtils.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0f);
        player.levelUpSpiritual();
        bukkitPlayer.sendTitle(RandomUtils.color("&x&0&8&F&B&D&D&lS&x&0&8&F&5&D&F&lp&x&0&7&E&F&E&1&li&x&0&7&E&8&E&3&lr&x&0&6&E&2&E&6&li&x&0&6&D&C&E&8&lt&x&0&5&D&6&E&A&lu&x&0&5&D&0&E&C&la&x&0&4&C&A&E&E&ll &x&0&3&B&D&F&2&lL&x&0&3&B&7&F&4&le&x&0&2&B&1&F&7&lv&x&0&2&A&B&F&9&le&x&0&1&A&4&F&B&ll &x&0&0&9&8&F&F&l") + player.getSpiritualLevel(), RandomUtils.color("&x&6&4&F&B&0&8&lL&x&7&4&F&C&0&7&le&x&8&5&F&C&0&6&lv&x&9&5&F&D&0&5&le&x&A&6&F&D&0&4&ll &x&C&6&F&E&0&2&lU&x&D&7&F&F&0&1&lp&x&E&7&F&F&0&0&l!"),
                10,40,10);

        bukkitPlayer.sendMessage(ChatColor.of("#0098FF") + "------------LEVEL UP----------");
        String msg = "Click here to see your rewards!";
        TextComponent message = new TextComponent(msg);
        message.setColor(ChatColor.of("#08FBDD"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spirituallvlgui"));
        BaseComponent[] msg1 = TextComponent.fromLegacyText("Click");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg1));
        bukkitPlayer.spigot().sendMessage(message);
        lvlUpReward(player, player.getSpiritualLevel());

        SoundAndEffectsUtils.makeParticle(bukkitPlayer.getLocation(), Particle.FIREWORKS_SPARK, 20, 3, 1);
    }

    public static void simulateLevelUp(BleachPlayer player){
        Player bukkitPlayer = player.getBukkitPlayer();
        SoundAndEffectsUtils.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0f);
        bukkitPlayer.sendTitle(RandomUtils.color("&x&0&8&F&B&D&D&lS&x&0&8&F&5&D&F&lp&x&0&7&E&F&E&1&li&x&0&7&E&8&E&3&lr&x&0&6&E&2&E&6&li&x&0&6&D&C&E&8&lt&x&0&5&D&6&E&A&lu&x&0&5&D&0&E&C&la&x&0&4&C&A&E&E&ll &x&0&3&B&D&F&2&lL&x&0&3&B&7&F&4&le&x&0&2&B&1&F&7&lv&x&0&2&A&B&F&9&le&x&0&1&A&4&F&B&ll &x&0&0&9&8&F&F&l") + player.getSpiritualLevel(), RandomUtils.color("&x&6&4&F&B&0&8&lL&x&7&4&F&C&0&7&le&x&8&5&F&C&0&6&lv&x&9&5&F&D&0&5&le&x&A&6&F&D&0&4&ll &x&C&6&F&E&0&2&lU&x&D&7&F&F&0&1&lp&x&E&7&F&F&0&0&l!"),
                10,40,10);

        bukkitPlayer.sendMessage(ChatColor.of("#0098FF") + "------------LEVEL UP----------");
        String msg = "Click here to see your rewards!";
        TextComponent message = new TextComponent(msg);
        message.setColor(ChatColor.of("#08FBDD"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bleachrpg_tutorial"));
        BaseComponent[] msg1 = TextComponent.fromLegacyText("Click");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg1));
        bukkitPlayer.spigot().sendMessage(message);

        SoundAndEffectsUtils.makeParticle(bukkitPlayer.getLocation(), Particle.FIREWORKS_SPARK, 20,3, 1);
    }

    /*
    ----------------------Spiritual LVL mapping-----------------
     */


    public void lvlUpReward(BleachPlayer player, int lvl){

        switch (lvl){
            case 2:
            case 6:
            case 12:
            case 16:
            case 22:
            case 26:
            case 32:
            case 36:
            case 42:
            case 46:
                player.setReiatsuRegen(player.getReiatsuRegen() + 0.2f);
                break;
            case 3:
            case 7:
            case 13:
            case 17:
            case 19:
            case 23:
            case 27:
            case 33:
            case 37:
            case 43:
            case 47:
            case 50:
                player.setSkillPoints(player.getSkillPoint() + 1);
                break;
            case 4:
            case 14:
            case 24:
            case 34:
                player.setAddedMeditationXPPerSec(player.getAddedMeditationXPPerSec() + 1);
                break;
            case 5:
            case 25:
            case 35:
            case 45:
                player.setFlashstepPowerIncrease(player.getFlashstepPowerIncrease() + 0.15f);
                player.setSkillPoints(player.getSkillPoint() + 2);
                break;
            case 8:
            case 18:
            case 28:
            case 38:
            case 48:
                player.setAddedReiatsu(player.getAddedReiatsu()+5);
                break;
            case 9:
            case 11:
            case 31:
            case 39:
            case 41:
            case 49:
                player.setFlashstepCDReduct(player.getFlashstepCDReduct() + 0.29f);
                break;
            case 10:
                player.setSkillPoints(player.getSkillPoint()+1);
                player.setHeavyAttackCDReduct(player.getHeavyAttackCDReduct()+1);
                break;
            case 15:
                player.setSkillPoints(player.getSkillPoint() + 1);
                player.setFlashStepReductionCost(player.getFlashStepReductionCost() + 5);
                //Unlocks Shikai
                break;
            case 20:
                player.setSkillPoints(player.getSkillPoint()+1);
                player.setHeavyAttackCDReduct(player.getHeavyAttackCDReduct()+0.5f);
                break;
            case 21:
            case 29:
                player.setFlashstepPowerIncrease(player.getFlashstepPowerIncrease() + 0.15f);
                break;
            case 30:
                player.setSkillPoints(player.getSkillPoint() + 1);
                player.setFlashStepReductionCost(player.getFlashStepReductionCost() + 5);
                //Unlocks Bankai
                break;
            case 40:
                player.setSkillPoints(player.getSkillPoint() + 1);
                player.setBlockingCDReduct(player.getBlockingCDReduct()+1);
                //Unlocks True Bankai
                break;
            case 44:
                player.setAddedMeditationXPPerSec(player.getAddedMeditationXPPerSec() + 2);





        }

        player.setAddedReiatsu(player.getAddedReiatsu()+5);
    }



}
