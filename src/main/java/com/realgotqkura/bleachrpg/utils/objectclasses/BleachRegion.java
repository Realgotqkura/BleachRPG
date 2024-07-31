package com.realgotqkura.bleachrpg.utils.objectclasses;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BleachRegion {

    private static HashMap<Player, BleachRegion> playerRegions = new HashMap<>();

    private final Location NULL_LOCATION = new Location(Bukkit.getWorld("world"),0,0,0);
    private Location firstLoc;
    private Location secondLoc;
    private Player player;

    public BleachRegion(Player player){
        this.player = player;

        if(playerRegions.get(player) == null){
            this.firstLoc = NULL_LOCATION;
            this.secondLoc = NULL_LOCATION;
            playerRegions.put(player, this);
            return;
        }

        this.firstLoc = playerRegions.get(player).getLocations()[0];
        this.secondLoc = playerRegions.get(player).getLocations()[1];
    }


    public Location[] getLocations(){
        return new Location[]{firstLoc, secondLoc};
    }

    public void setLocation(int num, Location loc){
        if(num == 0){
            firstLoc = loc;
        }else{
            secondLoc = loc;
        }
        save();
    }

    private void save(){
        playerRegions.replace(player, this);
    }



}
