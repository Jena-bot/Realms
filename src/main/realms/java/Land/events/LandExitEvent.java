package main.realms.java.Land.events;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LandExitEvent extends Event {
    public Land land;
    public Chunk from;
    public Chunk to;
    public Human human;

    public LandExitEvent(Chunk from, Chunk to, Land land, Human human) {
        this.from = from;
        this.to = to;
        this.land = land;
        this.human = human;
        human.getPlayer().getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§2Wilderness"));
        Bukkit.broadcastMessage("e");
    }

    public Land getLand() {
        return land;
    }

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public HandlerList getHandlerList() {
        return new HandlerList();
    }
}
