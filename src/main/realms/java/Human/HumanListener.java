package main.realms.java.Human;

import main.realms.java.Realms;
import main.realms.utils.RealmsException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HumanListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPlayedBefore()) {
            for (Human human : Realms.getHumans()) {
                if (human.getPlayer() == event.getPlayer() || human.getUuid() == event.getPlayer().getUniqueId()) {
                    human.setOnline(System.currentTimeMillis());
                } else {
                    return;
                }
            }
        } else {
            try {
                Realms.addHuman(new Human(event.getPlayer(), true));
            } catch (RealmsException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event) throws RealmsException {
        for (Human human : Realms.getHumans()) {
            if (event.getPlayer() == human.getPlayer() || human.getUuid() == event.getPlayer().getUniqueId()) {
                human.setOnline(System.currentTimeMillis());
                return;
            }
        }

        Realms.addHuman(new Human(event.getPlayer(), true));
    }
}
