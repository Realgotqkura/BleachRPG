package com.realgotqkura.bleachrpg.events.general;

import com.realgotqkura.bleachrpg.BleachRPG;
import com.realgotqkura.bleachrpg.utils.BleachUtils;
import com.realgotqkura.bleachrpg.utils.RandomUtils;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachActionBar;
import com.realgotqkura.bleachrpg.utils.objectclasses.BleachPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ReiatsuEvents implements Listener {

    /**
     * Actionbar stuff
     */

    private void createActionBars(){
        BaseComponent[] blocking = TextComponent.fromLegacyText(RandomUtils.color("&c&l&k:: &r&c&lBlocking &c&l&k::"));
        new BleachActionBar(blocking, 1, "blocking_bar");
    }

    public void init(BleachRPG plugin){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(BleachUtils.playerReiatsuMap.containsKey(player))
                return;

            BleachUtils.playerReiatsuMap.put(player, BleachUtils.getMaxPlayerReiatsu(player));
        }

        showActionBar(plugin);
        startReiatsuRegen(plugin);
        createActionBars();
    }

    private void showActionBar(BleachRPG plugin){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    BleachPlayer bleachPlayer = new BleachPlayer(player);
                    if(BleachActionBar.getHighestPriorityEnabled() == null){
                        BaseComponent[] reiatsu = TextComponent.fromLegacyText(RandomUtils.color("&bReiatsu: " + BleachUtils.playerReiatsuMap.get(player) + "✺") +
                                "           " + ChatColor.of("#1db899") + "(" + BleachUtils.getPlayerSpiritualXPNoOtherlvls(player) + "/" + BleachUtils.xpToLvlMap.get(bleachPlayer.getSpiritualLevel() + 1) + "⚛)");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, reiatsu);
                    }else{
                        BleachActionBar bar = BleachActionBar.getHighestPriorityEnabled();
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, bar.getText());
                    }
                }
            }


        }.runTaskTimer(plugin, 5, 5);
    }

    private void startReiatsuRegen(BleachRPG plugin){
        float defaultReiatsuRegen = (float) plugin.getConfig().getDouble("defaultReiatsuRegen");
        /*
        Also works as a reiatsu regen
         */
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    BleachPlayer bleachPlayer = new BleachPlayer(player);
                    float maxReiatsu = BleachUtils.getMaxPlayerReiatsu(player);
                    if(BleachUtils.playerReiatsuMap.get(player) < maxReiatsu){
                        float value = BleachUtils.playerReiatsuMap.get(player) + (defaultReiatsuRegen + bleachPlayer.getReiatsuRegen());
                        if(value > maxReiatsu){
                            value = maxReiatsu;
                        }
                        BleachUtils.playerReiatsuMap.put(player, RandomUtils.roundToSpecificDecimalPoint(value, 1));
                    }
                }
            }


        }.runTaskTimer(plugin, 10, 10);
    }



    @EventHandler
    public void join(PlayerJoinEvent event){
        if(BleachUtils.playerReiatsuMap.containsKey(event.getPlayer()))
            return;

        BleachUtils.playerReiatsuMap.put(event.getPlayer(), BleachUtils.getMaxPlayerReiatsu(event.getPlayer()));
    }
}
