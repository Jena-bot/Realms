package main.realms.java.Land;

import main.realms.java.Human.Human;
import main.realms.java.Land.events.DeleteLandEvent;
import main.realms.java.Land.events.NewLandEvent;
import main.realms.java.Realm.Realm;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.ChatInfo;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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
    private final File data;
    private final long registered;

    //todo contructors
    public Land(List<Chunk> chunks, Human owner, String name) {
        this.name = name;
        this.chunks = chunks;
        this.owner = owner;
        try {
            this.realm = RealmsAPI.getRealm(owner);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
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
            config.set("registered", registered);

            config.set("owner", owner.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.set("realm", "");
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(Chunk chunk, Human owner, String name) {
        this.name = name;

        // alternate constructor
        this.chunks = new ArrayList<>();
        chunks.add(chunk);

        this.owner = owner;
        try {
            this.realm = RealmsAPI.getRealm(owner);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
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
            config.set("registered", registered);

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
        try {
            this.realm = RealmsAPI.getRealm(owner);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
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
            config.set("registered", registered);

            config.set("owner", owner.getUuid().toString());
            //todo config.set("realm", realm.getUuid().toString());
            config.set("uuid", uuid.toString());
            config.save(data);
        } catch (IOException e) {e.printStackTrace();}
    }

    public Land(List<Chunk> chunks, Human owner, UUID uuid, String name) throws NotFoundException {
        this.chunks = chunks;
        this.owner = owner;
        this.realm = RealmsAPI.getRealm(owner);
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
            config.set("registered", registered);

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
            this.registered = config.getLong("registered");

            // multiple chunks
            List<String> coords = config.getStringList("coords");
            for (String s : coords)
                this.chunks.add(Bukkit.getWorld(s.split(",")[0]).getChunkAt(Integer.parseInt(s.split(",")[1]), Integer.parseInt(s.split(",")[2])));

            this.owner = RealmsAPI.getHuman(UUID.fromString(config.getString("owner")));
            //notice Realm setting is in loadData() rather than here, due to the load order.
        } catch (InvalidConfigurationException | IOException | RealmsException e) {
            throw new RealmsException("201");
        }
    }

    // getters and setters


    public void setName(String name) {
        this.name = name;
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public String getName() {
        return name;
    }

    public void setRealm(Realm realm) {
        if (this.realm != null) this.realm.lands.removeIf(land -> land.getUuid() == this.getUuid());
        this.realm = realm;
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public void setRealmData(Realm realm) {
        if (this.realm != null) this.realm.lands.removeIf(land -> land.getUuid() == this.getUuid());
        this.realm = realm;
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(getData());
            config.set("realm", realm.getUuid());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

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

        // realms
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        try {
            this.realm = RealmsAPI.getRealm(owner);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public void addChunk(Chunk chunk) {
        chunks.add(chunk);
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public void removeChunk(Chunk chunk) {
        chunks.remove(chunk);
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        RealmsMain.lands.add(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        realm.getLands().removeIf(land -> land.getUuid() == this.getUuid());
        realm.addLand(this);

        RealmsMain.lands.removeIf(land -> land.getName().equals(getName()));
        RealmsMain.lands.add(this);
    }

    public long getRegistered() {
        return registered;
    }

    public File getData() {
        return data;
    }

    /* Methods */

    public void delete() {
        //todo add a deleted folder or rename the file
        data.delete();

        RealmsMain.lands.removeIf(land -> land.getUuid() == getUuid());
        this.getRealm().lands.removeIf(land -> land.getUuid() == getUuid());

        Bukkit.getPluginManager().callEvent(new DeleteLandEvent(this.getName(), this.getOwner()));
    }

    public boolean hasRealm() {
        return this.getRealm() != null;
    }

    // static stuff

    public static Land newLand(Human human, String name) throws NotFoundException {
        if (human.isOnline()) {
            Chunk chunk = human.getPlayer().getPlayer().getLocation().getChunk();
            Land land = new Land(chunk, human, name);
            NewLandEvent event = new NewLandEvent(land, human);
            Bukkit.getPluginManager().callEvent(event);
            for (Player player : Bukkit.getOnlinePlayers()) {
                ChatInfo.sendCenteredMessage(player, "&8&m--------------&r &3&lNEW LAND &8&m--------------");
                ChatInfo.sendCenteredMessage(player, "&b" + human.getName() + " has founded the land of &l" + land.getName().toUpperCase());
                ChatInfo.sendCenteredMessage(player, "");
            }
            //todo move this to RealmListener
            if (RealmsAPI.getRealm(chunk) == null) {
                Realm.newRealm(land, land.getName());
            } else {
                RealmsAPI.getRealm(chunk).addLand(land);
            }
            RealmsMain.lands.add(land);
            return land;
        }
        throw new NotFoundException();
    }
}
