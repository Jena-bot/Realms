package main.realms.java.Land;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.List;

public class LandCommand extends BukkitCommand {

    public LandCommand(String name, List<String> aliases, String permission) {
        super(name);
        this.description = "Check a land's status.";
        this.usageMessage = "/" + name + " <land>";
        setAliases(aliases);
        setPermission(permission);

    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
