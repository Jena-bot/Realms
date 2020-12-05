package main.realms.java.Human;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Human {
    public static Player player;
    public static UUID uuid;
    public static String title;
    private long played;
    private long online;
    private File data;

    public static Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Human.title = title;
    }
}
