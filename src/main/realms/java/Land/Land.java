package main.realms.java.Land;

import main.realms.java.Human.Human;
import main.realms.java.Land.events.NewLandEvent;
import main.realms.java.Realm.Realm;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.ChatInfo;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Land {
    public String name;
    public UUID uuid;
    public List<Chunk> chunks = new ArrayList<>();
    public Realm realm;
    public Human owner;
    private File data;
    private long registered;

    //todo contructors
    public Land(List<Chunk> chunks, Human owner, String name) {
        this.name = name;
        this.chunks = chunks;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();

        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            for (Chunk chunk : chunks)
                coordlist.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            config.set("coords", coordlist);
            config.set("created", registered);

            config.set("owner", owner.getUuid().toString());
            //todo config.set("realm", realm.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(Chunk chunk, Human owner, String name) {
        this.name = name;

        // alternate constructor
        this.chunks = new ArrayList<>();
        chunks.add(chunk);

        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();

        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            coordlist.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            config.set("coords", coordlist);
            config.set("created", registered);

            config.set("owner", owner.getUuid().toString());
            //todo config.set("realm", realm.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(Chunk chunk, Human owner, UUID uuid, String name) {
        this.name = name;

        // alternate constructor
        this.chunks = new ArrayList<>();
        chunks.add(chunk);

        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = uuid;
        this.registered = System.currentTimeMillis();


        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            coordlist.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            config.set("coords", coordlist);
            config.set("created", registered);

            config.set("owner", owner.getUuid().toString());
            //todo config.set("realm", realm.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(List<Chunk> chunks, Human owner, UUID uuid, String name) {
        this.chunks = chunks;
        this.owner = owner;
        this.realm = owner.getRealm();
        this.uuid = uuid;
        this.registered = System.currentTimeMillis();


        this.data = new File(RealmsMain.database + "/lands", uuid.toString());
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("name", name);

            // multiple chunks branch (Jena-bot)
            List<String> coordlist = new ArrayList<>();
            for (Chunk chunk : chunks)
                coordlist.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            config.set("coords", coordlist);
            config.set("created", registered);

            config.set("owner", owner.getUuid().toString());
            config.set("realm", "");
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(File data) throws RealmsException {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(data);
            this.name = config.getString("name");
            this.uuid = UUID.fromString(config.getString("uuid"));
            this.data = data;
            this.registered = config.getLong("created");

            // multiple chunks
            List<String> coords = config.getStringList("coords");
            for (String s : coords)
                this.chunks.add(Bukkit.getWorld(s.split(",")[0]).getChunkAt(Integer.parseInt(s.split(",")[1]), Integer.parseInt(s.split(",")[2])));

            this.owner = RealmsAPI.getHuman(UUID.fromString(config.getString("owner")));
            //todo realm
            this.realm = null;

        } catch (InvalidConfigurationException | IOException | RealmsException e) {
            throw new RealmsException("201");
        }
    }

    // getters and setters


    public void setName(String name) {
        this.name = name;

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public String getName() {
        return name;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public Realm getRealm() {
        return realm;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public void addChunk(Chunk chunk) {
        chunks.add(chunk);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public void removeChunk(Chunk chunk) {
        chunks.remove(chunk);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;

        RealmsMain.lands.removeIf(land -> land.getName().equals(getName()));
        RealmsMain.lands.add(this);
    }

    public long getRegistered() {
        return registered;
    }

    /* Methods */

    public void delete() {
        //todo add a deleted folder or rename the file
        data.delete();

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
    }

    // static stuff

    public static Land newLand(Human human, String name) throws NotFoundException {
        if (human.isOnline()) {
            Land land = new Land(human.getPlayer().getPlayer().getLocation().getChunk(), human, name);
            NewLandEvent event = new NewLandEvent(land, human);
            Bukkit.getPluginManager().callEvent(event);
            for (Player player : Bukkit.getOnlinePlayers()) {
                ChatInfo.sendCenteredMessage(player, "&8&m--------------&r &3&lNEW LAND &8&m--------------");
                ChatInfo.sendCenteredMessage(player, "&b" + human.getName() + " has founded the land of &l" + land.getName().toUpperCase());
            }
            RealmsMain.lands.add(land);
            return land;
        }
        throw new NotFoundException();
    }
}
