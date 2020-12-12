package main.realms.java.Land.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeleteLandEvent extends Event {
    public String land;

    public DeleteLandEvent(String land) {
        this.land = land;
    }

    // bukkit stuff

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public static HandlerList getHandlerList() {
        return new HandlerList();
    }

    public String getLand() {
        return land;
    }
}
