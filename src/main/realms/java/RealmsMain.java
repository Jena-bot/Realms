package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Human.HumanCommand;
import main.realms.java.Human.HumanListener;
import main.realms.java.Land.Land;
import main.realms.java.objects.WorldCoord;
import main.realms.utils.ChatInfo;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RealmsMain extends JavaPlugin {
    public static String database = "plugins/Realms/data";

    // caching, files are saved using runnable, till then stored in memory.
    public static List<Human> humans = new ArrayList<>();
    public static List<Land> lands = new ArrayList<>();
    private static final Runnable save = RealmsMain::saveData;

    @Override
    public void onLoad() {
        super.onLoad();
        saveDefaultConfig();

        // to create data.
        File file = new File(database);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (!loadData()) {
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError loading data"));
            this.getLogger().severe("ERROR: DATA WAS NOT LOADED CORRECTLY.");
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, save, 1200, 1200);

        registerSpecialCommands();

        this.getServer().getPluginManager().registerEvents(new HumanListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (!saveData()) {
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError saving data"));
            this.getLogger().severe("ERROR: DATA WAS NOT SAVED.");
        }
    }

    private void registerSpecialCommands() {
        // status command aliases
        List<String> statalias = new ArrayList<>();
        statalias.add("stat");
        ((CraftServer) this.getServer()).getCommandMap().register("status", new HumanCommand("status", statalias, "realms.command.status"));
    }

    public static boolean saveData() {
        YamlConfiguration config = new YamlConfiguration();

        // Humans
        for (Human human : humans) {
            config.set("name", human.getName());
            config.set("uuid", human.getUuid().toString());
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

        config = new YamlConfiguration();
        // Lands
        for (Land land : lands) {
            config.set("uuid", land.getUuid());
            config.set("owner", land.getOwner().getUuid());

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            for (WorldCoord coord : land.getCoords())
                coordlist.add(coord.getWorldname() + "," + coord.getX() + "," + coord.getZ());
            config.set("coods", coordlist);

            //todo realms
        }

        return true;
    }

    private boolean loadData() {
        YamlConfiguration config = new YamlConfiguration();
        humans = new ArrayList<>();

        // Humans
        File humanDATA = new File("plugins/Realms/data/humans");
        if (humanDATA.listFiles() != null) {
            for (File file : humanDATA.listFiles()) {
                try {
                    humans.add(new Human(file));
                } catch (RealmsException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        // Lands
        File landsDATA = new File("plugins/Realms/data/humans");
        if (landsDATA.listFiles() != null) {
            for (File file : landsDATA.listFiles()) {
                try {
                    lands.add(new Land(file));
                } catch (RealmsException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
