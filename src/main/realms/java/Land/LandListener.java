package main.realms.java.Land;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.events.LandEnterEvent;
import main.realms.java.Land.events.LandExitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LandListener implements Listener {

    @EventHandler
    public static void onChunkChange(HumanChunkChangeEvent event) {
        if (event.getTo().hasLand() && !event.getFrom().hasLand()) {
            LandEnterEvent enterEvent = new LandEnterEvent(event.getFrom(), event.getTo().getLand(), event.getHuman());
            Bukkit.getPluginManager().callEvent(enterEvent);
        } else if (event.getFrom().hasLand() && !event.getTo().hasLand()) {
            LandExitEvent exitEvent = new LandExitEvent(event.getFrom().getLand(), event.getTo(), event.getHuman());
            Bukkit.getPluginManager().callEvent(exitEvent);
        }
    }
}
