package com.realgotqkura.bleachrpg.events.zanpakuto.zangetsu;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.abilities.zangetsu.bankai.GetsugaJujisho;
import com.realgotqkura.bleachrpg.abilities.zangetsu.shikai.GetsugaTaiho;
import com.realgotqkura.bleachrpg.abilities.zangetsu.shikai.GetsugaTenshou;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.ShinigamiStage;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import com.realgotqkura.bleachrpg.utils.objectclasses.spirits.ZanpakutoSpirits;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ZangetsuEvents implements Listener {

    private BleachRPG plugin;

    public ZangetsuEvents(BleachRPG plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void interShikai(PlayerInteractEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        ItemStack item = player.getBukkitPlayer().getInventory().getItemInMainHand();

        if(BleachUtils.getBleachID(item) != 1)
            return;

        if(RandomUtils.shouldStopUsage(player.getBukkitPlayer()))
            return;



        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(player.getBukkitPlayer().isSneaking()){
                GetsugaTaiho taiho = new GetsugaTaiho(plugin,plugin.getConfig().getInt("Zangetsu.getsugaTaihoCD"), plugin.getConfig().getInt("Zangetsu.getsugaTaihoDamage"));
                taiho.activateAbility(player.getBukkitPlayer(), null);
                return;
            }

            GetsugaTenshou ability1 = new GetsugaTenshou(plugin,plugin.getConfig().getInt("Zangetsu.getsugaTenshouCD"), plugin.getConfig().getInt("Zangetsu.getsugaTenshouDamage"));
            ability1.activateAbility(player.getBukkitPlayer(), null);
        }
    }

    @EventHandler
    public void interBankai(PlayerInteractEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        ItemStack item = player.getBukkitPlayer().getInventory().getItemInMainHand();

        if(BleachUtils.getBleachID(item) != 2)
            return;

        if(RandomUtils.shouldStopUsage(player.getBukkitPlayer()))
            return;



        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            GetsugaJujisho ability1 = new GetsugaJujisho(plugin,plugin.getConfig().getInt("Zangetsu.getsugaJujishoCD"), plugin.getConfig().getInt("Zangetsu.getsugaJujishoDamage"));
            ability1.activateAbility(player.getBukkitPlayer(), null);
        }
    }

    @EventHandler
    public void moveBankai(PlayerMoveEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        if(player.getSpirit() == ZanpakutoSpirits.ZANGETSU && player.getShinigamiStage().toInt() >= ShinigamiStage.BANKAI.toInt()){
            int speedAmp = plugin.getConfig().getInt("Zangetsu.speedPotEffectAmp");
            int strengthAmp = plugin.getConfig().getInt("Zangetsu.strengthPotEffectAmp");
            player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, strengthAmp, false));
            player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, speedAmp, false));
        }
    }


    public static void bankaiAnimation(BleachPlayer player, BleachRPG plugin){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(RandomUtils.isAnimatedTitleDone.get(player.getBukkitPlayer())){
                    SoundAndEffectsUtils.playSound(player.getBukkitPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2);
                    SoundAndEffectsUtils.makeParticle(player.getBukkitPlayer().getLocation(), Particle.EXPLOSION_HUGE, 2, 4, 0);
                    cancel();
                }

                SoundAndEffectsUtils.playSound(player.getBukkitPlayer().getLocation(), Sound.ENTITY_BLAZE_BURN, 0.5f);
                SoundAndEffectsUtils.makeParticle(player.getBukkitPlayer().getLocation(), Particle.FLAME, 5, 10, 1);
            }
        }.runTaskTimer(plugin, 0 ,1);
    }
}
