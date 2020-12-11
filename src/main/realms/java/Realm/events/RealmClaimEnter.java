package main.realms.java.Realm.events;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.java.RealmsAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RealmClaimEnter extends Event {
    public Chunk to;
    public Chunk from;
    public Realm realm;
    public Human human;

    public RealmClaimEnter(Chunk to, Chunk from, Realm realm, Human human) {
        this.to = to;
        this.from = from;
        this.realm = realm;
        this.human = human;
        Bukkit.broadcastMessage("ee");
        human.getPlayer().getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§lWILDERNESS§e claimed by §6§l" + RealmsAPI.getRealm(to).getName().toUpperCase()));
    }

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public static HandlerList getHandlerList() {
        return new HandlerList();
    }

    public Chunk getFrom() {
        return from;
    }

    public Chunk getTo() {
        return to;
    }

    public Realm getRealm() {
        return realm;
    }

    public Human getHuman() {
        return human;
    }
}
