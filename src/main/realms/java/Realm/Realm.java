package main.realms.java.Realm;

import com.flowpowered.math.vector.Vector2i;
import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.ChatInfo;
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
public class Realm {
    public Human owner;
    public String name;
    public long registered;
    public UUID uuid;
    public List<Land> lands = new ArrayList<>();
    public List<Realm> vassals = new ArrayList<>();
    public Realm overlord;
    private final File data;
    public List<Chunk> claims = new ArrayList<>();

    public Realm(Human owner, String name, List<Land> lands) {
        this.owner = owner;
        this.name = name;
        this.lands = lands;
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.vassals = null;
        this.overlord = null;
        this.data = new File(RealmsMain.database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", owner.getUuid());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", "");

            // lands
            List<String> list = new ArrayList<>();
            for (Land land : lands) list.add(land.getUuid().toString());
            config.set("lands", list);

            // claims
            Realm.UpdateClaims(this);
            List<String> list1 = new ArrayList<>();
            for (Chunk chunk : claims) {
                list1.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            }
            config.set("claims", list1);

            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Realm.UpdateClaims(this);
    }

    public Realm(Human owner, String name, Land land) {
        this.owner = owner;
        this.name = name;
        this.lands.add(land);
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.vassals = null;
        this.overlord = null;
        this.data = new File(RealmsMain.database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", owner.getUuid());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", "");

            // lands
            List<String> list = new ArrayList<>();
            list.add(land.getUuid().toString());
            config.set("lands", list);

            // claims
            Realm.UpdateClaims(this);
            List<String> list1 = new ArrayList<>();
            for (Chunk chunk : claims) {
                list1.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            }
            config.set("claims", list1);

            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Realm.UpdateClaims(this);
    }

    public Realm(Human owner, String name, List<Land> lands, Realm overlord) {
        this.owner = owner;
        this.name = name;
        this.lands = lands;
        this.overlord = overlord;
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.vassals = null;
        this.overlord = null;
        this.data = new File(RealmsMain.database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", owner.getUuid());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", overlord.getUuid().toString());

            // lands
            List<String> list = new ArrayList<>();
            for (Land land : lands) list.add(land.getUuid().toString());
            config.set("lands", list);

            // claims
            Realm.UpdateClaims(this);
            List<String> list1 = new ArrayList<>();
            for (Chunk chunk : claims) {
                list1.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            }
            config.set("claims", list1);

            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Realm(Human owner, String name, Land land, Realm overlord) {
        this.owner = owner;
        this.name = name;
        this.lands.add(land);
        this.overlord = overlord;
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.vassals = null;
        this.overlord = null;
        this.data = new File(RealmsMain.database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", owner.getUuid());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", overlord.getUuid().toString());

            // lands
            List<String> list = new ArrayList<>();
            list.add(land.getUuid().toString());
            config.set("lands", list);

            // claims
            Realm.UpdateClaims(this);
            List<String> list1 = new ArrayList<>();
            for (Chunk chunk : claims) {
                list1.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            }
            config.set("claims", list1);

            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Realm(File data) throws RealmsException {
        this.data = data;

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(data);
            this.name = config.getString("name");
            this.uuid = UUID.fromString(config.getString("uuid"));
            this.registered = config.getLong("registered");
            //todo this.overload = RealmsAPI.getRealm(UUID.fromString(config.getString("overlord")));

            // Lands
            for (String s : config.getStringList("lands")) {
                this.lands.add(RealmsAPI.getLand(UUID.fromString(s)));
            }

            // vassals
             for (String s : config.getStringList("vassals")) {
                 this.vassals.add(RealmsAPI.getRealm(UUID.fromString(s)));
             }

             // claims
            for (String s : config.getStringList("claims")) {
                this.claims.add(Bukkit.getWorld(s.split(",")[0]).getChunkAt(Integer.parseInt(s.split(",")[1]), Integer.parseInt(s.split(",")[2])));
            }

        } catch (IOException | InvalidConfigurationException e) {
            throw new RealmsException("301");
        }
    }

    // getters & setters

    public Human getOwner() {
        return owner;
    }

    public List<Land> getLands() {
        return lands;
    }

    public List<Realm> getVassals() {
        return vassals;
    }

    public void setOwner(Human owner) {
        this.owner = owner;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public void addLand(Land land) {
        lands.add(land);

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public void addVassal(Realm realm) {
        vassals.add(realm);

        RealmsMain.realms.removeIf(realm2 -> realm2.getUuid() == this.getUuid());
        RealmsMain.realms.add(this);
    }

    public long getRegistered() {
        return registered;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public void setRegistered(long registered) {
        this.registered = registered;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public Realm getOverlord() {
        return overlord;
    }

    public void setLands(List<Land> lands) {
        this.lands = lands;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public void setVassals(List<Realm> vassals) {
        this.vassals = vassals;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public void setOverlord(Realm overlord) {
        this.overlord = overlord;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    public File getData() {
        return data;
    }

    public List<Chunk> getClaims() {
        return claims;
    }

    public void setClaims(List<Chunk> claims) {
        this.claims = claims;

        RealmsMain.realms.removeIf(realm -> realm.getUuid() == getUuid());
        RealmsMain.realms.add(this);
    }

    // Static Methods

    public static void UpdateClaims(Realm realm) {
        realm.setClaims(new ArrayList<>());

        // claims
        for (Land land : realm.getLands()) {
            for (Chunk chunk : land.getChunks()) {
                Vector2i point = new Vector2i(chunk.getX(), chunk.getZ());
                for (int x = -5; x <= 5; x++)
                    for (int y = -5; y <= 5; y++)
                        if (x * x + y * y <= 25) {
                            Chunk chunk1 = chunk.getWorld().getChunkAt(x + point.getX(), y + point.getY());
                            if (RealmsAPI.getLand(chunk1) == null && RealmsAPI.getRealm(chunk1) == null) realm.getClaims().add(chunk1);
                        }
            }
        }
    }

    public static void newRealm(Land land, String name) {
        Realm realm = new Realm(land.getOwner(), name, land);

        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatInfo.sendCenteredMessage(player, "&8&m--------------&r &5&lNEW REALM &8&m--------------");
            ChatInfo.sendCenteredMessage(player, "&d" + land.getOwner().getName() + " has founded §5§l" + name.toUpperCase() + "§d in ");
        }
    }


}
