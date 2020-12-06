package main.realms.java.Human;

import main.realms.java.RealmsMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("unused")
public class Human {
    public Player player;
    public UUID uuid;
    public String title;
    private long played;
    private long online;
    private File data;

    public Human(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.title = "";
        this.played = System.currentTimeMillis();
        this.online = System.currentTimeMillis();

        this.data = new File(RealmsMain.database, this.uuid.toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        // configuration
        try {
            config.set("uuid", this.uuid.toString());
            config.set("title", "");
            config.set("played", System.currentTimeMillis());
            config.set("online", System.currentTimeMillis());
            config.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public  void setTitle(String title) {
        this.title = title;
    }
}
