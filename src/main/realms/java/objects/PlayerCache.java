package main.realms.java.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCache {
    public WorldCoord lastWorldCoord;
    public Location lastlocation;
    public UUID player;

    public PlayerCache(Player player) {
        this.lastWorldCoord = new WorldCoord(player.getLocation().getWorld().getName(),
                player.getLocation().getChunk());
        this.lastlocation = player.getLocation();
        this.player = player.getUniqueId();
    }

    public UUID getPlayer() {
        return player;
    }

    public Location getLastlocation() {
        return lastlocation;
    }

    public void setLastlocation(Location lastlocation) {
        this.lastlocation = lastlocation;
    }

    public void setLastWorldCoord(WorldCoord lastWorldCoord) {
        this.lastWorldCoord = lastWorldCoord;
    }

    public WorldCoord getLastWorldCoord() {
        return lastWorldCoord;
    }
}
