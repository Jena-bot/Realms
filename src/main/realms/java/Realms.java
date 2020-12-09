package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import main.realms.java.Realm.Realm;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Realms {
    public static List<Realm> realms = new LinkedList<>();
    public static List<Land> lands = new LinkedList<>();
    private static final File[] dbHuman = new File(RealmsMain.database + "/humans").listFiles();

    // this is a quick class that allows for easy access to a list of realms and humans without having to look through files.
    public static List<Human> getHumans() {
        return RealmsMain.humans;
    }

    public static List<Realm> getRealms() {
        return Realms.realms;
    }

    public static List<Land> getLands() {
        return lands;
    }

    public static void setLands(List<Land> lands) {
        Realms.lands = lands;
    }

    public static void setRealms(List<Realm> realmslist) {
        Realms.realms = realmslist;
    }

    public static void addRealm(Realm realm) {
        Realms.realms.add(realm);
    }

    public static void addLand(Land land) {
        Realms.lands.add(land);
    }

    public static void removeLand(Land land) {
        Realms.lands.remove(land);
    }

    public static void removeRealm(Realm realm) {
        Realms.realms.remove(realm);
    }
}
