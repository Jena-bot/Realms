package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;

import java.util.LinkedList;
import java.util.List;

public class Realms {
    public static List<Realm> realms = new LinkedList<>();
    public static List<Human> humans = new LinkedList<>();

    // this is a quick class that allows for easy access to a list of realms and humans without having to look through files.
    public static List<Human> getHumans() {
        return Realms.humans;
    }

    public static List<Realm> getRealms() {
        return Realms.realms;
    }

    public static void setHumans(List<Human> humanlist) {
        Realms.humans = humanlist;
    }

    public static void setRealms(List<Realm> realmslist) {
        Realms.realms = realmslist;
    }

    public static void addRealm(Realm realm) {
        Realms.realms.add(realm);
    }

    public static void removeRealm(Realm realm) {
        Realms.realms.remove(realm);
    }

    public static void addHuman(Human human) {
        Realms.humans.add(human);
    }

    public static void removeHuman(Human human) {
        Realms.humans.remove(human);
    }

}
