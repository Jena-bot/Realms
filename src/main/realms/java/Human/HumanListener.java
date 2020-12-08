package main.realms.java.Human;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Realms;
import main.realms.java.RealmsAPI;
import main.realms.java.RealmsMain;
import main.realms.java.objects.PlayerCache;
import main.realms.java.objects.WorldCoord;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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

        try {
            RealmsAPI.getPlayerCache(event.getPlayer());
        } catch (NotFoundException e) {
            Realms.addCache(new PlayerCache(event.getPlayer()));
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

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        PlayerCache cache;

        try {
            cache = RealmsAPI.getPlayerCache(event.getPlayer());
            cache.setLastlocation(event.getPlayer().getLocation());
            cache.setLastWorldCoord(new WorldCoord(event.getTo().getWorld().getName(), event.getTo().getChunk()));
        } catch (NotFoundException e) {
            cache = new PlayerCache(event.getPlayer());
            Realms.addCache(cache);
        }

        if (event.getTo().getChunk() != event.getFrom().getChunk()) {
            try {
                HumanChunkChangeEvent chunkChangeEvent = new HumanChunkChangeEvent(new WorldCoord(event.getFrom().getWorld().getName(), event.getFrom().getChunk()),
                        new WorldCoord(event.getTo().getWorld().getName(), event.getTo().getChunk()),
                        RealmsAPI.getHuman(event.getPlayer()));
                Bukkit.getServer().getPluginManager().callEvent(chunkChangeEvent);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
