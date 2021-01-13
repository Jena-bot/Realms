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

import static main.realms.java.RealmsMain.database;
import static main.realms.java.RealmsMain.realms;

@SuppressWarnings("unused")
public class Realm {
    public UUID owner;
    public String name;
    public long registered;
    public UUID uuid;
    public List<UUID> humans = new ArrayList<>();
    public UUID overlord;
    private final File data;
    public List<Chunk> claims = new ArrayList<>();

    public Realm(Human owner, String name, List<Human> humans) {
        this.owner = owner.getUuid();
        this.name = name;

        // UUID change
        humans.forEach(human -> this.humans.add(human.getUuid()));
        this.humans.add(owner.getUuid());

        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.overlord = null;
        this.data = new File(database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", this.owner.toString());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", "");

            // Humans (Lands)
            List<String> list = new ArrayList<>();
            for (UUID uuid : this.humans) list.add(uuid.toString());
            config.set("humans", list);

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

    public Realm(Human owner, String name) {
        this.owner = owner.getUuid();
        this.name = name;

        // UUID Changes
        this.humans = new ArrayList<>();
        humans.add(owner.getUuid());

        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.overlord = null;
        this.data = new File(database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", this.owner.toString());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", "");

            // Humans (Lands)
            List<String> list = new ArrayList<>();
            list.add(owner.getUuid().toString());
            config.set("humans", list);

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

    public Realm(Human owner, String name, List<Human> humans, Realm overlord) {
        this.owner = owner.getUuid();
        this.name = name;

        // UUID change
        humans.forEach(human -> this.humans.add(human.getUuid()));
        this.humans.add(owner.getUuid());

        this.overlord = overlord.getUuid();
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.overlord = overlord.getUuid();
        this.data = new File(database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", this.owner.toString());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", this.overlord.toString());

            // Humans (Lands)
            List<String> list = new ArrayList<>();
            for (UUID uuid : this.humans) list.add(uuid.toString());
            config.set("humans", list);

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

    public Realm(Human owner, String name, Realm overlord) {
        this.owner = owner.getUuid();
        this.name = name;
        this.humans.add(owner.getUuid());
        this.overlord = overlord.getUuid();
        this.uuid = UUID.randomUUID();
        this.registered = System.currentTimeMillis();
        this.overlord = overlord.getUuid();
        this.data = new File(database + "/realms", uuid.toString());

        // Data File saving
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.set("uuid", uuid.toString());
            config.set("name", name);
            config.set("owner", owner.getUuid().toString());
            config.set("registered", registered);
            config.set("vassals", "");
            config.set("overlord", overlord.getUuid().toString());

            // Humans (Lands)
            List<String> list = new ArrayList<>();
            for (UUID uuid : this.humans) list.add(uuid.toString());
            config.set("humans", list);

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
            if (config.getString("overlord") != null && config.getString("overlord") != "" && config.getString("overlord") != "''") {
                this.overlord = UUID.fromString(config.getString("overlord"));
            } else this.overlord = null;
            this.owner = UUID.fromString(config.getString("owner"));

            // Lands
            for (String s : config.getStringList("humans")) {
                this.humans.add(UUID.fromString(s));
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
        return RealmsAPI.getHuman(owner);
    }

    public UUID getOwnerUUID() {
        return owner;
    }

    public List<Human> getMembers() {
        List<Human> humanList = new ArrayList<>();
        humans.forEach(human -> humanList.add(RealmsAPI.getHuman(human)));
        return humanList;
    }

    public List<UUID> getMemberUUIDs() {
        return humans;
    }

    public List<UUID> getVassalUUIDs() {
        List<UUID> uuids = new ArrayList<>();
        realms.forEach(realm -> {
            if (realm.getOverlordUUID() == this.getUuid()) uuids.add(realm.getUuid());
        });
        return uuids;
    }

    public List<Realm> getVassals() {
        List<Realm> realms = new ArrayList<>();
        RealmsMain.realms.forEach(realm -> {
            if (realm.getOverlordUUID() == this.getUuid()) realms.add(realm);
        });
        return realms;
    }

    public void setOwner(Human owner) {
        this.owner = owner.getUuid();

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public void addMember(Human human) {
        humans.add(human.getUuid());

        Realm.UpdateClaims(this);

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public void addVassal(Realm realm) {
        realm.setOverlordUUID(this.getUuid());

        realms.removeIf(realm2 -> realm2.getUuid() == this.getUuid());
        realms.add(this);
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

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public void setRegistered(long registered) {
        this.registered = registered;

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public Realm getOverlord() {
        return RealmsAPI.getRealm(overlord);
    }

    public UUID getOverlordUUID() {
        return overlord;
    }

    public void setOverlord(Realm overlord) {
        this.overlord = overlord.getUuid();

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public void setOverlordUUID(UUID uuid) {
        this.overlord = uuid;

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    public File getData() {
        return data;
    }

    public List<Chunk> getClaims() {
        return claims;
    }

    public void setClaims(List<Chunk> claims) {
        this.claims = claims;

        realms.removeIf(realm -> realm.getUuid() == getUuid());
        realms.add(this);
    }

    // Static Methods

    public static void UpdateClaims(Realm realm) {
        realm.setClaims(new ArrayList<>());

        // claims
        for (Human human : realm.getMembers()) {
            for (Land land : human.getLands()) {
                for (Chunk chunk : land.getChunks()) {
                    Vector2i point = new Vector2i(chunk.getX(), chunk.getZ());
                    for (int x = -5; x <= 5; x++)
                        for (int y = -5; y <= 5; y++)
                            if (x * x + y * y <= 25) {
                                Chunk chunk1 = chunk.getWorld().getChunkAt(x + point.getX(), y + point.getY());
                                if (RealmsAPI.getLand(chunk1) == null && RealmsAPI.getRealm(chunk1) == null)
                                    realm.getClaims().add(chunk1);
                            }
                }
            }
        }
    }

    public static void newRealm(Human human, String name) {
        Realm realm = new Realm(human, name);
        human.setTitle("Lord");

        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatInfo.sendCenteredMessage(player, "&8&m--------------&r &5&lNEW REALM &8&m--------------");
            ChatInfo.sendCenteredMessage(player, "&d" + human.getName() + " has founded §5§l" + name.toUpperCase());
        }

        Realm.UpdateClaims(realm);
        realms.add(realm);
    }


}
