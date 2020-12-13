package main.realms.java.Human;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HumanListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        // todo add something to counter name changes
        if (RealmsAPI.getHuman(event.getPlayer()) != null) {
            Human human = RealmsAPI.getHuman(event.getPlayer());

            if (human.getName() != event.getPlayer().getName())
                human.setName(event.getPlayer().getName());
        } else {
            try {
                RealmsMain.humans.add(new Human(event.getPlayer(), true));
            } catch (RealmsException ee) {
                ee.printStackTrace();
            }
        }
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event) {
        Human human = RealmsAPI.getHuman(event.getPlayer());
        RealmsMain.humans.remove(human);
        human.setOnline(System.currentTimeMillis());
        RealmsMain.humans.add(human);
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        if (event.getTo().getChunk() != event.getFrom().getChunk()) {
            HumanChunkChangeEvent chunkChangeEvent = new HumanChunkChangeEvent(event.getFrom().getChunk(), event.getTo().getChunk(), RealmsAPI.getHuman(event.getPlayer()));
            Bukkit.getPluginManager().callEvent(chunkChangeEvent);
        }
    }
}
