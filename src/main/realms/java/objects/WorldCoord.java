package main.realms.java.objects;

import main.realms.utils.Coord;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

@SuppressWarnings("unused")
public class WorldCoord extends Coord {

    // github.com/TownyAdvanced/Towny
    public Chunk chunk;
    private String worldname;

    public WorldCoord(String world, int x, int z) {
        super(x, z);
        this.worldname = world;
        this.chunk = Bukkit.getWorld(world).getChunkAt(x, z);
    }

    public WorldCoord(String world, Chunk chunk) {
        super(chunk.getX(), chunk.getZ());
        this.worldname = world;
        this.chunk = chunk;
    }

    public WorldCoord(String world, Coord Coord) {
        super(Coord);
        this.worldname = world;
        this.chunk = Bukkit.getWorld(world).getChunkAt(Coord.getX(), Coord.getZ());
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(worldname);
    }

    public Chunk getChunk() {
        return chunk;
    }

    public String getWorldname() {
        return worldname;
    }

    public WorldCoord add(int xOffset, int zOffset) {
        return new WorldCoord(worldname, getX() + xOffset, getZ() + zOffset);
    }

    public WorldCoord setChunk(Chunk chunk) {
        return new WorldCoord(chunk.getWorld().getName(), chunk);
    }

    public void setWorldname(String worldname) {
        this.worldname = worldname;
    }

    public void setWorld(World world) {
        this.worldname = world.getName();
    }
}
