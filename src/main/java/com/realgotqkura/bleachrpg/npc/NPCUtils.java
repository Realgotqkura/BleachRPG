package com.realgotqkura.bleachrpg.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.realgotqkura.bleachrpg.BleachRPG;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NPCUtils {

    public static void lookAtPlayer(NPC npc, Player player) {
        PacketContainer packetLook = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
        PacketContainer packetHeadRot = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);

        Location npcLocation = npc.getEntity().getLocation();
        Location newNpcLocation = npcLocation.setDirection(player.getLocation().subtract(npcLocation).toVector());
        float yaw = newNpcLocation.getYaw();
        float pitch = newNpcLocation.getPitch();
        byte yawAngle = (byte) (yaw * 256F / 360F);
        byte pitchAngle = (byte) (pitch * 256F / 360F);

        //Send packets
        packetLook.getIntegers().write(0, npc.getEntity().getEntityId());
        packetLook.getBytes().write(0,  yawAngle);
        packetLook.getBytes().write(1, pitchAngle);
        packetLook.getBooleans().write(0, false);

        packetHeadRot.getIntegers().write(0, npc.getEntity().getEntityId());
        packetHeadRot.getBytes().write(0, yawAngle);

        BleachRPG.getProtocolManager().sendServerPacket(player, packetHeadRot, true);
        BleachRPG.getProtocolManager().sendServerPacket(player, packetLook, true);

    }
}
