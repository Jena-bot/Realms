package main.realms.java.Land;

import main.realms.java.RealmsAPI;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.text.SimpleDateFormat;
import java.util.List;

import static main.realms.utils.ChatInfo.*;

public class LandCommand extends BukkitCommand {
    public static final SimpleDateFormat registeredFormat = new SimpleDateFormat("MMMMM d yyyy");

    public LandCommand(String name, List<String> aliases, String permission) {
        super(name);
        this.description = "For all land-related commands";
        this.usageMessage = "/" + name + " <land>";
        setAliases(aliases);
        setPermission(permission);

    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        if (args.length == 0) {

        } else {
            switch (args.length) {
                case 1:
                    switch (args[0]) {

                        case "new":
                            cs.sendMessage(prefix("&cYou need to specify a name."));
                            break;

                        default:
                            // land status
                            try {
                                Land land = RealmsAPI.getLand(args[0]);

                                //todo move formatting to Formatter.java
                                sendCenteredMessage(cs, "&8&m-----------------&r &b&l" + land.getName().toUpperCase() + " &8&m-----------------&r");
                                //todo sendCenteredMessage(cs, "&5&l" + land.getRealm().getName());
                                sendCenteredMessage(cs, "&bSize &3[" + land.getChunks().toArray().length + "]");
                                sendCenteredMessage(cs, "&8&m-----------------");
                                sendCenteredMessage(cs, "&bOwned by");
                                if (cs.getName() != land.getOwner().getName()) sendCenteredMessage(cs, "&3&l" + land.getOwner().getName().toUpperCase());
                                else sendCenteredMessage(cs, "&3&lYOU");
                                sendCenteredMessage(cs, "&8&m-----------------");
                                sendCenteredMessage(cs, "&3Founded on " + registeredFormat.format(land.getRegistered()));
                            } catch (NotFoundException e) {
                                cs.sendMessage(prefix("&c" + args[0] + " is not a valid land."));
                            }
                            break;
                    }

                case 2:
                    switch (args[0]) {
                        case "new":
                        //todo add confirmation
                            try {
                                Land.newLand(RealmsAPI.getHuman(cs.getName()), args[1]);
                            } catch (RealmsException e) {
                                e.printStackTrace();
                            }
                    }
            }
        }
        return false;
    }
}
