package main.realms.java.Human.events;

import main.realms.java.Human.Human;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HumanChunkChangeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    public Chunk from;
    public Chunk to;
    public Human human;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HumanChunkChangeEvent(Chunk from,
                                 Chunk to,
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

    public Chunk getFrom() {
        return from;
    }

    public Chunk getTo() {
        return to;
    }
}
