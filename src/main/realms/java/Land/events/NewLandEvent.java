package main.realms.java.Land.events;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NewLandEvent extends Event {
    public Land land;
    public Human human;

    public NewLandEvent(Land land, Human human) {
        this.land = land;
        this.human = human;
    }

    // bukkit stuff

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public HandlerList getHanderlist() {
        return new HandlerList();
    }
}
