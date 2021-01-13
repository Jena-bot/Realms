package main.realms.java.Realm;

import main.realms.java.Human.Human;
import main.realms.java.RealmsAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static main.realms.utils.ChatInfo.prefix;
import static main.realms.utils.ChatInfo.sendCenteredMessage;

public class RealmCommand extends BukkitCommand {
    public static final SimpleDateFormat registeredFormat = new SimpleDateFormat("MMMMM d yyyy");

    public RealmCommand(String name, List<String> aliases, String permission) {
        super(name);
        this.description = "For all realm-related commands";
        this.usageMessage = "/" + name + " <realm>";
        setAliases(aliases);
        setPermission(permission);
    }

    @Override
    public boolean execute(CommandSender cs, String s, String[] args) {
        switch (args.length) {
            case 0:
                //todo self realm

            case 1:
                switch (args[0]) {
                    case "set":
                        cs.sendMessage(prefix("&cYou need to specfiy an argument"));
                        break;

                    default:
                        if (RealmsAPI.getRealm(args[0]) != null) {
                            Realm realm = RealmsAPI.getRealm(args[0]);

                            // lands
                            List<String> list = new ArrayList<>();
                            for (Human human : realm.getMembers())
                                list.add(human.getName());

                            //todo move formatting to Formatter.java
                            sendCenteredMessage(cs, "&8&m-----------------&r &5&l" + realm.getName().toUpperCase() + " &8&m-----------------&r");
                            if (realm.getOverlordUUID() != null)
                                sendCenteredMessage(cs, "&dVassal of " + realm.getOverlord().getName());
                            sendCenteredMessage(cs, "&dMembers:");
                            sendCenteredMessage(cs, "&5[&d" + String.join(", ", list) + "&5]");
                            sendCenteredMessage(cs, "&8&m--------------");
                            sendCenteredMessage(cs, "&6&l" + realm.getOwner().getTitle().toUpperCase() + realm.getOwner().getName().toUpperCase());
                            sendCenteredMessage(cs, "§dFounded on " + registeredFormat.format(realm.getRegistered()));

                            if (realm.getVassals() != null) {
                                sendCenteredMessage(cs, "&8&m--------------");
                                sendCenteredMessage(cs, "&dVassals:");

                                List<String> list1 = new ArrayList<>();
                                for (Realm vassal : realm.getVassals())
                                    list1.add(vassal.getName());
                                sendCenteredMessage(cs, "&5[&d" + String.join(",", list1) + "&5]");
                            }


                        } else cs.sendMessage(prefix("&c" + args[0] + " isn't a valid realm."));
                        break;
                }
                break;

            case 2:
                switch (args[0]) {
                    case "set":
                        switch (args[1]) {

                            case "name":
                                cs.sendMessage(prefix("&cYou need to specify a name."));
                                break;

                        }
                        break;
                }

            case 3:
                switch (args[0]) {
                    case "set":
                        switch (args[1]) {

                            case "name":
                                if (args.length == 3) {
                                    if (RealmsAPI.getRealm(RealmsAPI.getHuman(cs.getName())) != null) {
                                        Realm realm = RealmsAPI.getRealm(RealmsAPI.getHuman(cs.getName()));

                                        if (realm.getOwner().getUuid() == RealmsAPI.getHuman(cs.getName()).getUuid()) {
                                            realm.setName(args[2]);
                                            cs.sendMessage(prefix("&bRealm name changed to ") + realm.getName());
                                        }
                                    } else cs.sendMessage(prefix("&c" + args[0] + " isn't a valid realm."));
                                }

                        }
                        break;
                }
        }
        return false;
    }
}
