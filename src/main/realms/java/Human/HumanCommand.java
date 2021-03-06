package main.realms.java.Human;

import main.realms.java.RealmsAPI;
import main.realms.utils.ChatInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.text.SimpleDateFormat;
import java.util.List;

public class HumanCommand extends BukkitCommand {
    // github.com/TownyAdvanced/Towny
    public static final SimpleDateFormat registeredFormat = new SimpleDateFormat("MMMMM d yyyy");
    public static final SimpleDateFormat lastOnlineFormat = new SimpleDateFormat("MMMMM dd '@' HH:mm");

    public HumanCommand(String name, List<String> aliases, String permission) {
        super(name);
        this.description = "Check a person's status.";
        this.usageMessage = "/" + name + " <player>";
        setAliases(aliases);
        setPermission(permission);

    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        //todo allow for extra arguments
        if (args.length >= 1 && cs.hasPermission(this.getPermission())) {

            // /command args[0] args[1] args[2]
            //todo add a hide function that allows people to hide their own status
            if (RealmsAPI.getHuman(args[0]) != null) {
                Human human = RealmsAPI.getHuman(args[0]);

                //todo move formatting to Formatter.java
                ChatInfo.sendCenteredMessage(cs, "&8&m-----------------&r &6&l" + human.getTitle().toUpperCase() + human.getName().toUpperCase() + " &8&m-----------------&r");
                if (RealmsAPI.getRealm(human) != null)
                    ChatInfo.sendCenteredMessage(cs, "&6&l" + human.getTitle().replace(" ", "") + "&e of &6&l" + RealmsAPI.getRealm(human).getName().toUpperCase());
                ChatInfo.sendCenteredMessage(cs, "&eJoined on " + registeredFormat.format(human.getPlayed()));
                if (human.isOnline()) ChatInfo.sendCenteredMessage(cs, "&aCurrently Online!");
                else ChatInfo.sendCenteredMessage(cs, "&eLast Online on " + lastOnlineFormat.format(System.currentTimeMillis()));
            }
        } else if (cs.hasPermission(this.getPermission())) {
            if (RealmsAPI.getHuman(cs.getName()) != null) {
                Human human = RealmsAPI.getHuman(cs.getName());

                //todo move formatting to Formatter.java
                ChatInfo.sendCenteredMessage(cs, ChatInfo.color("&8&m-----------------&r &6&l" + human.getTitle().toUpperCase() + human.getPlayer().getName().toUpperCase() + " &8&m-----------------&r"));
                if (RealmsAPI.getRealm(human) != null) ChatInfo.sendCenteredMessage(cs, "&6&l" + human.getTitle().replace(" ", "") + "&e of &6&l" + RealmsAPI.getRealm(human).getName().toUpperCase());
                ChatInfo.sendCenteredMessage(cs, "&eJoined on " + registeredFormat.format(human.getPlayed()));
                ChatInfo.sendCenteredMessage(cs, "&aYou're currently online!");
            }
        } else {
            cs.sendMessage(ChatInfo.prefix("&cYou don't have permission to do this command."));
        }
        return false;
    }
}
