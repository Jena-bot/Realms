package main.realms.java.Human;

import main.realms.java.Realms;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.NotFoundException;
import main.realms.utils.RealmsException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HumanListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        // todo add something to counter name changes
        try {
            RealmsAPI.getHuman(event.getPlayer()).setOnline(System.currentTimeMillis());
        } catch (NotFoundException e) {
            try {
                Realms.addHuman(new Human(event.getPlayer(), true));
            } catch (RealmsException ee) {
                ee.printStackTrace();
            }
        }
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event) throws RealmsException {
        for (Human human : Realms.getHumans()) {
            if (human.getUuid() == event.getPlayer().getUniqueId()) {
                human.setOnline(System.currentTimeMillis());
                return;
            }
        }

        Realms.addHuman(new Human(event.getPlayer(), true));
    }


    // debug
    @EventHandler
    public static void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().contains("saverealmsdata")) RealmsMain.saveData();
    }
}
