package com.realgotqkura.bleachrpg.events.otheritems;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.events.zanpakuto.zangetsu.ZangetsuEvents;
import com.realgotqkura.bleachrpg.items.BankaiItems;
import com.realgotqkura.bleachrpg.items.BleachItems;
import com.realgotqkura.bleachrpg.items.ShikaiItems;
import com.realgotqkura.bleachrpg.items.ZanpakutoItems;
import com.realgotqkura.bleachrpg.utils.*;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import com.realgotqkura.bleachrpg.utils.voicelines.BankaiVoiceLines;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SoulReaperBadge implements Listener {

    private BleachRPG plugin;

    public SoulReaperBadge(BleachRPG plugin){
        this.plugin = plugin;
    }

    private String[] shikaiActivationWords = {
        "Roar", "Unleash", "Activate", "Cut", "Wake up", "Dance", "Play", "Stand up", "Come out",
                "Ignite", "Ascend", "Awaken", "Burst forth", "Erupt", "Manifest", "Release", "Shine", "Soar",
                "Strike", "Unveil", "Vanish", "Whisper", "Unchain", "Revel", "Resonate", "Arise", "Engage",
                "Empower", "Revive", "Charge", "Liberate", "Command"
    };

    HashMap<UUID, Double> rightClickCD = new HashMap<>();
    HashMap<UUID, Double> dropCD = new HashMap<>();


    @EventHandler
    public void interact(PlayerInteractEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        BleachItems items = new BleachItems();
        if(player.getBukkitPlayer().getInventory().getItemInMainHand() == null)
            return;

        if(!player.getBukkitPlayer().getInventory().getItemInMainHand().hasItemMeta())
            return;

        if(!player.getBukkitPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(items.getSpecificItem("SubstituteShinigamiBadge").getItemMeta().getDisplayName()))
            return;

        if(!player.getBukkitPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER))
            return;

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(player.getBukkitPlayer().isSneaking()) {
                onShiftRightClick();
                return;
            }

            if(checkCooldown(player.getBukkitPlayer(), 5, rightClickCD)){
                onRightClick(player);
            }
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event){
        BleachPlayer player = new BleachPlayer(event.getPlayer());

        BleachItems items = new BleachItems();

        if(!event.getItemDrop().getItemStack().hasItemMeta())
            return;

        if(!event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(items.getSpecificItem("SubstituteShinigamiBadge").getItemMeta().getDisplayName()))
            return;

        if(!event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(RandomUtils.nskStorage.get("BleachItem"), PersistentDataType.INTEGER))
            return;

        if(checkCooldown(player.getBukkitPlayer(), 3, dropCD)){
            onDropItem(player);
        }
    }



    private void onRightClick(BleachPlayer player){
        Player bukkitPlayer = player.getBukkitPlayer();

        if(player.getShinigamiStage() == ShinigamiStage.HUMAN){
            player.setShinigamiStage(ShinigamiStage.REGULAR);


            ZanpakutoItems items = new ZanpakutoItems(plugin);
            RandomUtils.addItemIntoPlInventory(bukkitPlayer, items.getZanpakuto(player.getSpirit().toString(), ShinigamiStage.REGULAR), 0);

            bukkitPlayer.sendTitle(RandomUtils.color("&8Shinigami"), "", 10, 20, 10);
            return;
        }

        //Opposite

        player.setShinigamiStage(ShinigamiStage.HUMAN);
        BleachUtils.removeZanpakutoItemsFromInv(bukkitPlayer);
        bukkitPlayer.sendTitle(RandomUtils.color("&7Human"), "", 10, 20, 10);
    }

    private void onShiftRightClick(){

    }

    private void onDropItem(BleachPlayer player){
        ShikaiItems items = new ShikaiItems();
        BankaiItems bkItems = new BankaiItems();
        ZanpakutoItems zanpakutoItems = new ZanpakutoItems(plugin);

        switch (player.getShinigamiStage()){
            case REGULAR:
                int random = ThreadLocalRandom.current().nextInt(0, shikaiActivationWords.length);
                player.setShinigamiStage(ShinigamiStage.SHIKAI);
                player.getBukkitPlayer().sendMessage(RandomUtils.color("&c[" + player.getBukkitPlayer().getName() + "]: &f" +
                        shikaiActivationWords[random] + " Zangetsu!"));
                SoundAndEffectsUtils.playSound(player.getBukkitPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1);
                SoundAndEffectsUtils.makeParticle(player.getBukkitPlayer().getLocation(), Particle.EXPLOSION_LARGE, 3,5, 0);
                BleachUtils.removeZanpakutoItemsFromInv(player.getBukkitPlayer());
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), items.zangetsuShikai(plugin), 1);
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), zanpakutoItems.getZanpakuto("zangetsu", ShinigamiStage.SHIKAI), 0);
                break;
            case SHIKAI:
                BleachUtils.removeZanpakutoItemsFromInv(player.getBukkitPlayer());
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), items.zangetsuShikai(plugin), 1);
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), zanpakutoItems.getZanpakuto("zangetsu", ShinigamiStage.BANKAI), 0);
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), bkItems.zangetsuBankai(plugin), 2);
                doBankaiAnimation(player, "zangetsu");
                player.setShinigamiStage(ShinigamiStage.BANKAI);
                break;
            case BANKAI:
                BleachUtils.removeZanpakutoItemsFromInv(player.getBukkitPlayer());
                RandomUtils.addItemIntoPlInventory(player.getBukkitPlayer(), zanpakutoItems.getZanpakuto("zangetsu", ShinigamiStage.REGULAR), 0);
                player.setShinigamiStage(ShinigamiStage.REGULAR);
                break;
            default:
                break;
        }
    }


    private boolean checkCooldown(Player player, double cooldownSeconds, Map<UUID, Double> cooldowns) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            double lastUse = cooldowns.get(player.getUniqueId());
            double currentTime = System.currentTimeMillis() / 1000.0; // Convert to seconds

            if (currentTime - lastUse < cooldownSeconds) {
                player.sendMessage(RandomUtils.color("&cPlease wait a bit before doing this!"));
                return false;
            }
        }

        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000.0);
        return true;
    }


    private void doBankaiAnimation(BleachPlayer player, String zanpakutoName){
        BankaiVoiceLines vl = new BankaiVoiceLines();

        RandomUtils.sendMultipleAnimatedTitles(vl.getVoiceLines(zanpakutoName).get(0), vl.getVoiceLines(zanpakutoName).get(1),
                1, plugin, player.getBukkitPlayer(), 20);

        switch (zanpakutoName.toLowerCase()){
            case "zangetsu":
                ZangetsuEvents.bankaiAnimation(player, plugin);
                break;
        }
    }


}
