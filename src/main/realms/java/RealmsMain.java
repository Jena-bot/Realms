package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.utils.ChatInfo;
import main.realms.utils.RealmsException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class RealmsMain extends JavaPlugin {
    public static String database = "plugins/Realms/data";

    @Override
    public void onLoad() {
        super.onLoad();
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        loadData();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (!saveData())
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError saving data"));
            this.getLogger().severe("ERROR: DATA WAS NOT SAVED.");
    }

    private void registerSpecialCommands() {

    }

    private boolean saveData() {
        YamlConfiguration config = new YamlConfiguration();

        // Humans
        for (Human human : Realms.getHumans()) {
            config.set("uuid", human.uuid.toString());
            config.set("title", human.getTitle());
            config.set("played", human.getPlayed());
            config.set("online", human.getOnline());

            try {
                config.save(human.getData());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean loadData() {
        YamlConfiguration config = new YamlConfiguration();

        // Humans
        File file = new File(database + "/humans");
        Realms.setHumans(new LinkedList<>());

        for (File data : file.listFiles()) {
            try {
                Human human = new Human(data);
                Realms.addHuman(human);
            } catch (RealmsException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
