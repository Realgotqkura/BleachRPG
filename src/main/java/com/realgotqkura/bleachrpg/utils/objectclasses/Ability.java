package com.realgotqkura.bleachrpg.utils.objectclasses;

import com.realgotqkura.bleachrpg.utils.RandomUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface Ability {

    /**
     *
     * @param activator - The player thats activating the ability
     * @param target - The target (Can be null)
     */

    default void activateAbility(Player activator, Player target){
        activator.sendMessage(RandomUtils.color("&cThis is the default ability function message."));
        activator.sendMessage(RandomUtils.color("&cIf you are seeing this something has gone terribly wrong."));
        activator.sendMessage(RandomUtils.color("&cContact an admin on your server!"));
    }

    default boolean checkCooldown(Player player, double cooldownSeconds, Map<UUID, Double> cooldowns) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            double lastUse = cooldowns.get(player.getUniqueId());
            double currentTime = System.currentTimeMillis() / 1000.0; // Convert to seconds

            if (currentTime - lastUse < cooldownSeconds) {
                player.sendMessage(RandomUtils.color("&cAbility Still in cooldown!"));
                return false;
            }
        }

        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000.0);
        return true;
    }


    //Kinda Unnecessary but just in case
    default void updateCooldown(Player player, Map<UUID, Double> cooldowns) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000.0);
    }
}
