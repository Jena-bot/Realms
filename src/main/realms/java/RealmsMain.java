package main.realms.java;

import main.realms.java.Land.Land;
import main.realms.java.Realm.Realm;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerSpecialCommands() {

    }
}
