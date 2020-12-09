package main.realms.java.Land;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.events.LandEnterEvent;
import main.realms.java.Land.events.LandExitEvent;
import main.realms.java.RealmsAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LandListener implements Listener {

    @EventHandler
    public static void onChunkChange(HumanChunkChangeEvent event) {
        if (RealmsAPI.getLand(event.getTo()) != null && RealmsAPI.getLand(event.getFrom()) == null) {
            LandEnterEvent enterEvent = new LandEnterEvent(event.getFrom(), event.getTo(), RealmsAPI.getLand(event.getTo()), event.getHuman());
            Bukkit.getPluginManager().callEvent(enterEvent);
        } else if (RealmsAPI.getLand(event.getFrom()) != null && RealmsAPI.getLand(event.getTo()) == null) {
            LandExitEvent exitEvent = new LandExitEvent(event.getFrom(), event.getTo(), RealmsAPI.getLand(event.getFrom()), event.getHuman());
            Bukkit.getPluginManager().callEvent(exitEvent);
        }
    }
}
