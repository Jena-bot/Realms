package main.realms.java.Realm;

import main.realms.java.Land.Land;
import main.realms.java.RealmsAPI;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static main.realms.utils.ChatInfo.*;

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
                        switch (args[1]) {

                            case "name":
                                if (args.length == 3) {
                                    try {
                                        Realm realm = RealmsAPI.getRealm(RealmsAPI.getHuman(cs.getName()));

                                        if (realm.getOwner().getUuid() == RealmsAPI.getHuman(cs.getName()).getUuid()) {
                                            realm.setName(args[2]);
                                            cs.sendMessage(prefix("&bRealm name changed to ") + realm.getName());
                                        }
                                    } catch (RealmsException e) {
                                        cs.sendMessage(prefix("&c" + args[0] + " isn't a valid realm."));
                                    }
                                }

                        } break;

                    default:
                        try {
                            Realm realm = RealmsAPI.getRealm(args[0]);

                            // lands
                            List<String> list = new ArrayList<>();
                            for (Land land : realm.getLands())
                                list.add(land.getName());

                            //todo move formatting to Formatter.java
                            sendCenteredMessage(cs, "&8&m-----------------&r &5&l" + realm.getName().toUpperCase() + " &8&m-----------------&r");
                            if (realm.getOverlord() != null)
                                sendCenteredMessage(cs, "&dVassal of " + realm.getOverlord().getName());
                            sendCenteredMessage(cs, "&dOwned Lands:");
                            sendCenteredMessage(cs, "&5[&d" + String.join(", ", list) + "&5]");
                            sendCenteredMessage(cs, "&8&m--------------");
                            sendCenteredMessage(cs, "&6&l" + realm.getOwner().getTitle().toUpperCase() + realm.getOwner().getName().toUpperCase());
                            sendCenteredMessage(cs, "§dFounded on " + registeredFormat.format(realm.getRegistered()));

                            if (realm.getVassals() != null) {
                                sendCenteredMessage(cs, "&8&m--------------");
                                sendCenteredMessage(cs, "&dVassals:");

                                List<String> list1 = new ArrayList<>();
                                for (Realm vassal : realm.getVassals())
                                    list1.add(realm.getName());
                                sendCenteredMessage(cs, "&5[&d" + String.join(",", list1) + "&5]");
                            }


                        } catch (NotFoundException e) {
                            cs.sendMessage(prefix("&c" + args[0] + " isn't a valid realm."));
                        } catch (RealmsException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
        }
        return false;
    }
}
