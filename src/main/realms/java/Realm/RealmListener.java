package main.realms.java.Realm;

import main.realms.java.Human.events.HumanChunkChangeEvent;
import main.realms.java.Land.events.NewLandEvent;
import main.realms.java.Realm.events.RealmClaimEnter;
import main.realms.java.Realm.events.RealmClaimExit;
import main.realms.java.RealmsAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RealmListener implements Listener {

    /*@EventHandler
    public static void onChunk(HumanChunkChangeEvent event) {
        if (RealmsAPI.getLand(event.getTo()) == null && RealmsAPI.getLand(event.getFrom()) == null) {
            // leaving a claim
            if (RealmsAPI.getRealm(event.getTo()) == null && RealmsAPI.getRealm(event.getFrom()) != null) {
                Bukkit.getPluginManager().callEvent(new RealmClaimExit(event.getTo(), event.getFrom(), RealmsAPI.getRealm(event.getFrom()), event.getHuman()));
            }
            // entering a claim
            else if (RealmsAPI.getRealm(event.getTo()) != null && RealmsAPI.getRealm(event.getFrom()) == null) {
                Bukkit.getPluginManager().callEvent(new RealmClaimEnter(event.getTo(), event.getFrom(), RealmsAPI.getRealm(event.getTo()), event.getHuman()));
            }
        }
    }*/

    /* todo move this to the listener, rather than newLand()
    @EventHandler
    public static void onNewLand(NewLandEvent event) {
        if (RealmsAPI.getRealm(event.getHuman().getPlayer().getPlayer().getLocation().getChunk()) != null) {
            RealmsAPI.getRealm(event.getHuman().getPlayer().getPlayer().getLocation().getChunk()).addLand(event.getLand());
        } else {
            Realm.newRealm(event.getLand(), event.getLand().getName());
        }
    }*/
}
