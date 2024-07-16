package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.JLogger.JLogger;
import com.realgotqkura.bleachrpg.configs.PlayerConfig;
import com.realgotqkura.bleachrpg.npc.NPCUtils;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.SoundAndEffectsUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerEvents implements Listener {


    private BleachRPG plugin;
    private PlayerConfig playerConfig;

    public PlayerEvents(BleachRPG plugin, PlayerConfig playerConfig){
        this.plugin = plugin;
        this.playerConfig = playerConfig;
    }
    /*
    Blocking events
     */
    @EventHandler
    public void moveWhenBlocking(PlayerMoveEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        if(player.isBlocking()){
            Vector velocity = player.getBukkitPlayer().getVelocity();
            player.getBukkitPlayer().setVelocity(new Vector(velocity.getX(), -1, velocity.getZ()));
            player.getBukkitPlayer().setSprinting(false);
            if(player.getBukkitPlayer().getWalkSpeed() != 0.1f){
                player.getBukkitPlayer().setWalkSpeed(0.1f);
            }

            SoundAndEffectsUtils.makeParticle(player.getBukkitPlayer().getLocation(), Particle.CRIT, 1, 3, 1);
        }else{
            if(player.getBukkitPlayer().getWalkSpeed() < 0.2f){
                player.getBukkitPlayer().setWalkSpeed(0.2f);
            }
        }
    }

    /*
    block break attack
     */

    @EventHandler
    public void heavyAttack(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            BleachPlayer damager = new BleachPlayer((Player) event.getDamager());

            if(!damager.isHoldingABleachWeapon())
                return;

            if(!damager.getBukkitPlayer().isSneaking())
                return;

            if(!damager.isHeavyAttackReady())
                return;

            if(event.getEntity() instanceof Player){
                BleachPlayer prey = new BleachPlayer((Player) event.getEntity());

                if(prey.isBlocking()){
                    prey.breakBlock();
                    SoundAndEffectsUtils.playSound(event.getEntity().getLocation(), Sound.ITEM_SHIELD_BREAK, 1);
                    return;
                }
            }

            event.setDamage(event.getDamage() * plugin.getConfig().getDouble("heavyAttackDamageMultiplier"));
            damager.doHeavyAttack();
            SoundAndEffectsUtils.makeParticle(event.getEntity().getLocation(), Particle.CRIT, 5,7,1);
            SoundAndEffectsUtils.playSound(event.getEntity().getLocation(), Sound.BLOCK_ANVIL_LAND, 0.3f);

        }


    }


    /*
    Bankai transformation
     */


    @EventHandler(priority = EventPriority.HIGHEST)
    public void stopMovement(PlayerMoveEvent event){
        if(RandomUtils.isAnimatedTitleDone.get(event.getPlayer()) == null)
            return;

        if(!RandomUtils.isAnimatedTitleDone.get(event.getPlayer())){
            event.getPlayer().getPlayer().setVelocity(new Vector().zero());
        }
    }

    @EventHandler
    public void makeInvinsible(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(!RandomUtils.shouldStopUsage(player))
                return;

            event.setCancelled(true);
        }
    }


    /*
      NPC Looking
     */

    @EventHandler
    public void onMoveNPCLook(PlayerMoveEvent event){
        for(Entity entity : event.getPlayer().getNearbyEntities(5,5,5)){
            if(!entity.hasMetadata("NPC"))
                continue;

            NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
            NPCUtils.lookAtPlayer(npc, event.getPlayer());
        }
    }





}
