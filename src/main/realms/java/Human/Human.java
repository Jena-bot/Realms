package main.realms.java.Human;

import main.realms.java.Realm.Realm;
import main.realms.java.RealmsMain;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("unused")
public class Human {
    public String name;
    public UUID uuid;
    public String title;
    public Realm realm = null;
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
            } else {this.title = config.getString("title");}

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

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getName() == getName()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
    }

    public String getTitle() {
        return title + " ";
    }

    public  void setTitle(String title) {
        this.title = title;

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getUuid() == getUuid()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
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

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getUuid() == getUuid()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
    }

    protected void setOnline(long online) {
        this.online = online;

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getUuid() == getUuid()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
    }

    protected void setPlayed(long played) {
        this.played = played;

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getUuid() == getUuid()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        // to update RealmsAPI
        for (Human human : RealmsMain.humans) {
            if (human.getUuid() == getUuid()) RealmsMain.humans.remove(human); RealmsMain.humans.add(this);
        }
    }

    public boolean isOnline() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId() == player.getUniqueId()) return true;
        }
        return false;
    }
}
