package main.realms.java;

import org.bukkit.plugin.java.JavaPlugin;

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
