package main.realms.java.Land.events;

import main.realms.java.Human.Human;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeleteLandEvent extends Event {
    public String land;
    public Human human;

    public DeleteLandEvent(String land, Human human) {
        this.land = land;
        this.human = human;
    }

    // bukkit stuff

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public static HandlerList getHandlerList() {
        return new HandlerList();
    }

    public Human getHuman() {
        return human;
    }

    public String getLand() {
        return land;
    }
}
