package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Human.HumanCommand;
import main.realms.java.Human.HumanListener;
import main.realms.java.Land.Land;
import main.realms.java.Land.LandCommand;
import main.realms.java.Realm.Realm;
import main.realms.java.Realm.RealmCommand;
import main.realms.java.listeners.MovementListener;
import main.realms.utils.ChatInfo;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealmsMain extends JavaPlugin {
    public static String database = "plugins/Realms/data";

    // caching, files are saved using runnable, till then stored in memory.
    public static List<Human> humans = new ArrayList<>();
    public static List<Land> lands = new ArrayList<>();
    public static List<Realm> realms = new ArrayList<>();
    private static final Runnable save = RealmsMain::saveData;

    @Override
    public void onLoad() {
        saveDefaultConfig();

        // to create data.
        File file = new File(database);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void onEnable() {
        if (!loadData()) {
            this.getServer().getConsoleSender().sendMessage(ChatInfo.prefix("&cError loading data"));
            this.getLogger().severe("ERROR: DATA WAS NOT LOADED CORRECTLY.");
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, save, 1200, 1200);
        registerSpecialCommands();

        // registering listeners
        Bukkit.getPluginManager().registerEvents(new HumanListener(), this);
        Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
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

        // land command
        List<String> landalias = new ArrayList<>();
        landalias.add("l");
        ((CraftServer) this.getServer()).getCommandMap().register("land", new LandCommand("land", landalias, "realms.command.land"));

        // land command
        List<String> realmalias = new ArrayList<>();
        ((CraftServer) this.getServer()).getCommandMap().register("realm", new RealmCommand("realm", realmalias, "realms.command.realm"));
    }

    public static boolean saveData() {
        YamlConfiguration config = new YamlConfiguration();

        // Humans
        for (Human human : humans) {
            config.set("name", human.getName());
            config.set("uuid", human.getUuid().toString());
            config.set("title", human.title);
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
            config.set("name", land.getName());
            config.set("uuid", land.getUuid().toString());
            config.set("owner", land.getOwner().getUuid().toString());

            // multiple chunks
            List<String> coordlist = new ArrayList<>();
            for (Chunk chunk : land.getChunks())
                coordlist.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
            config.set("coords", coordlist);
            config.set("registered", land.getRegistered());

            try {
                config.save(land.getData());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Realms
        config = new YamlConfiguration();
        for (Realm realm : realms) {
            config.set("uuid", realm.getUuid().toString());
            config.set("name", realm.getName());
            config.set("owner", realm.getOwnerUUID().toString());
            config.set("registered", realm.getRegistered());
            if (realm.getOverlordUUID() != null) config.set("overlord", realm.getOverlordUUID().toString());
            else config.set("overlord", "");

            // Humans (Lands)
            List<String> list = new ArrayList<>();
            for (UUID uuid : realm.getMemberUUIDs()) list.add(uuid.toString());
            config.set("humans", list);

            // Vassals
            if (realm.getVassals() != null) {
                List<String> vassals = new ArrayList<>();
                for (Realm vassal : realm.getVassals()) vassals.add(vassal.getUuid().toString());
                config.set("vassals", vassals);
            } else config.set("vassals", "");

            // Claims
            if (realm.getClaims() != null) {
                List<String> cl = new ArrayList<>();
                realm.getClaims().forEach(chunk -> cl.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ()));
                config.set("claims", cl);
            }

            try {
                config.save(realm.getData());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean loadData() {
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

        lands = new ArrayList<>();
        // Lands
        File landsDATA = new File("plugins/Realms/data/lands");
        if (landsDATA.listFiles() != null) {
            for (File file : landsDATA.listFiles()) {
                try {
                    Land land = new Land(file);
                    lands.add(land);
                } catch (RealmsException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        realms = new ArrayList<>();
        // Realms
        File realmsDATA = new File("plugins/Realms/data/realms");
        if (realmsDATA.listFiles() != null) {
            for (File file : realmsDATA.listFiles()) {
                try {
                    Realm realm = new Realm(file);
                    if (realm.getClaims() == null) Realm.UpdateClaims(realm);
                    realms.add(realm);
                } catch (RealmsException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }
}
