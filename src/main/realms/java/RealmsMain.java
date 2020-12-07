package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Human.HumanCommand;
import main.realms.java.Human.HumanListener;
import main.realms.utils.ChatInfo;
import main.realms.utils.RealmsException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RealmsMain extends JavaPlugin {
    public static String database = "plugins/Realms/data";

    @Override
    public void onLoad() {
        super.onLoad();
        saveDefaultConfig();

        // to create data.
        File file = new File(database);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (!loadData())
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError loading data"));
            this.getLogger().severe("ERROR: DATA WAS NOT LOADED CORRECTLY.");
            this.getPluginLoader().disablePlugin(this);

        registerSpecialCommands();

        this.getServer().getPluginManager().registerEvents(new HumanListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (!saveData())
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError saving data"));
            this.getLogger().severe("ERROR: DATA WAS NOT SAVED.");
    }

    private void registerSpecialCommands() {
        // status command aliases
        List<String> statalias = new ArrayList<>();
        statalias.add("stat");
        ((CraftServer) this.getServer()).getCommandMap().register("status", new HumanCommand("status", statalias, "realms.command.status"));
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
        File humandata = new File(database + "/humans");
        Realms.setHumans(new LinkedList<>());


        for (File data : Objects.requireNonNull(humandata.listFiles())) {
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
