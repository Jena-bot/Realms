package main.realms.java.Human;

import main.realms.java.Land.Land;
import main.realms.java.Realm.Realm;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Human {
    public String name;
    public UUID uuid;
    public String title;
    private long played;
    private long online;
    private final File data;

    public Human(Player player, boolean create) throws RealmsException {
        if (create) {
            this.name = player.getName();
            this.uuid = player.getUniqueId();
            this.title = "";
            this.played = System.currentTimeMillis();
            this.online = System.currentTimeMillis();

            this.data = new File(RealmsMain.database + "/humans", this.uuid.toString());
            YamlConfiguration config = new YamlConfiguration();

            // configuration
            try {
                config.set("name", this.name);
                config.set("uuid", this.uuid.toString());
                config.set("title", "");
                config.set("realm", "");
                config.set("played", System.currentTimeMillis());
                config.set("online", System.currentTimeMillis());
                config.save(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.data = new File(RealmsMain.database + "/humans", player.getUniqueId().toString());

            // data loading
            YamlConfiguration config = new YamlConfiguration();
            try {
                config.load(data);
                this.name = config.getString("name");
                this.uuid = UUID.fromString(config.getString("uuid"));
                this.title = config.getString("title");
                this.played = config.getLong("played");
                this.online = config.getLong("online");
            } catch (IOException | InvalidConfigurationException e) {
                throw new RealmsException("101");
            }
        }
    }

    public Human(Player player, boolean create, Realm realm) throws RealmsException {
        if (create) {
            this.name = player.getName();
            this.uuid = player.getUniqueId();
            this.title = "";
            this.played = System.currentTimeMillis();
            this.online = System.currentTimeMillis();

            this.data = new File(RealmsMain.database + "/humans", this.uuid.toString());
            YamlConfiguration config = new YamlConfiguration();

            // configuration
            try {
                config.set("name", this.name);
                config.set("uuid", this.uuid.toString());
                config.set("title", "");
                config.set("realm", realm.toString());
                config.set("played", System.currentTimeMillis());
                config.set("online", System.currentTimeMillis());
                config.save(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.data = new File(RealmsMain.database + "/humans", player.getUniqueId().toString());

            // data loading
            YamlConfiguration config = new YamlConfiguration();
            try {
                config.load(data);
                this.name = config.getString("name");
                this.uuid = UUID.fromString(config.getString("uuid"));
                this.title = config.getString("title");
                this.played = config.getLong("played");
                this.online = config.getLong("online");
            } catch (IOException | InvalidConfigurationException e) {
                throw new RealmsException("101");
            }
        }
    }

    public Human(File file) throws RealmsException {
        YamlConfiguration config = new YamlConfiguration();

        // File loading and var setting
        try {
            config.load(file);
            this.uuid = UUID.fromString(config.getString("uuid"));
            this.data = file;
            this.name = config.getString("name");


            // title, separate due to "" being equal to null when in YAML configuration.
            if (config.getString("title") == null) {
                this.title = "";
            } else {
                this.title = config.getString("title");
            }

            this.played = config.getLong("played");
            this.online = config.getLong("online");
        } catch (InvalidConfigurationException | IOException e) {
            throw new RealmsException("101");
        }
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;

        RealmsMain.humans.removeIf(human1 -> human1.getUuid() == this.getUuid());
        RealmsMain.humans.add(this);
    }

    public String getTitle() {
        return title + " ";
    }

    public  void setTitle(String title) {
        this.title = title;

        Human.UpdateAPI(this);

    }

    public long getOnline() {
        return online;
    }

    public long getPlayed() {
        return played;
    }

    public File getData() {
        return data;
    }

    protected void setOnline(long online) {
        this.online = online;

        Human.UpdateAPI(this);
    }

    protected void setPlayed(long played) {
        this.played = played;

        Human.UpdateAPI(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        Human.UpdateAPI(this);
    }

    public boolean isOnline() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId() == player.getUniqueId()) return true;
        }
        return false;
    }

    public boolean hasRealm() {
        return getRealm() != null;
    }

    public Realm getRealm() {
        if (hasRealm()) {
            return RealmsAPI.getRealm(this);
        }
        else return null;
    }

    public List<Land> getLands() {
        return RealmsAPI.getOwnedLand(this);
    }

    /* static methods */
    public static void UpdateAPI(Human human) {
        RealmsMain.humans.removeIf(human1 -> human1.getUuid() == human.getUuid());
        RealmsMain.humans.add(human);
    }
}
