package main.realms.java.listeners;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.events.LandEnterEvent;
import main.realms.java.Realm.events.RealmClaimEnter;
import main.realms.java.Realm.events.RealmClaimExit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static main.realms.java.RealmsAPI.*;

public class MovementListener implements Listener {

    @EventHandler
    public static void onChunkChange(HumanChunkChangeEvent event) {
        if (getLand(event.getTo()) == null) {
            if (getRealm(event.getTo()) != null && getRealm(event.getFrom()) == null)
                Bukkit.getPluginManager().callEvent(new RealmClaimEnter(event.getTo(), event.getFrom(), getRealm(event.getTo()), event.getHuman()));
            if (getRealm(event.getFrom()) != null && getRealm(event.getTo()) == null)
                Bukkit.getPluginManager().callEvent(new RealmClaimExit(event.getTo(), event.getFrom(), getRealm(event.getFrom()), event.getHuman()));
            //todo integrate this into above if statements
            if (getLand(event.getFrom()) != null)
                Bukkit.getPluginManager().callEvent(new RealmClaimEnter(event.getTo(), event.getFrom(), getRealm(event.getTo()), event.getHuman()));
        } else {
            if (getLand(event.getFrom()) == null)
                Bukkit.getPluginManager().callEvent(new LandEnterEvent(event.getFrom(), event.getTo(), getLand(event.getTo()), event.getHuman()));
        }
    }
}
