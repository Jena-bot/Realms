package main.realms.java.Human;

import main.realms.java.RealmsAPI;
import main.realms.utils.ChatInfo;
import main.realms.utils.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.text.SimpleDateFormat;
import java.util.List;

public class HumanCommand extends BukkitCommand {
    // github.com/TownyAdvanced/Towny
    public static final SimpleDateFormat registeredFormat = new SimpleDateFormat("MMM d yyyy");
    public static final SimpleDateFormat lastOnlineFormat = new SimpleDateFormat("MMMMM dd '@' HH:mm");

    protected HumanCommand(String name, List<String> aliases) {
        super(name);
        this.description = "Check a person's status.";
        this.usageMessage = "/" + name + " <player>";
        setAliases(aliases);
    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        if (args.length >= 2) {

            // /command args[0] args[1] args[2]
            if (Bukkit.getPlayer(args[0]) != null) {
                try {
                    Human human = RealmsAPI.getHuman(Bukkit.getPlayer(args[0]));

                    //todo move formatting to Formatter.java
                    ChatInfo.sendCenteredMessage(cs, ChatInfo.color("&8&m-----------------&r &6&l" + human.getTitle().toUpperCase() + human.getPlayer().getName().toUpperCase() + " &8&m-----------------&r"));
                    if (human.getRealm() != null) cs.sendMessage(ChatInfo.color("&e" + human.getTitle().replace(" ", "") + " of &6&l" + human.getRealm().getName().toUpperCase()));
                    cs.sendMessage(ChatInfo.color("&eJoined on " + registeredFormat.format(human.getPlayed())));
                    cs.sendMessage(ChatInfo.color("&cLast online " + lastOnlineFormat.format(human.getOnline())));
                } catch (RealmsException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
