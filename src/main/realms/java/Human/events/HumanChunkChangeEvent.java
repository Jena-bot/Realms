package main.realms.java.Human.events;

import main.realms.java.Human.Human;
import main.realms.java.objects.WorldCoord;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HumanChunkChangeEvent extends Event {
    private final HandlerList handlerList = new HandlerList();
    public WorldCoord from;
    public WorldCoord to;
    public Human human;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public HumanChunkChangeEvent(WorldCoord from,
                                 WorldCoord to,
                                 Human human) {
        this.from = from;
        this.to = to;
        this.human = human;

        //todo config option
        Bukkit.broadcastMessage("run event");
    }

    public Human getHuman() {
        return human;
    }

    public WorldCoord getFrom() {
        return from;
    }

    public WorldCoord getTo() {
        return to;
    }
}
