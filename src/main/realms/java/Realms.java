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
        return humans;
    }

    public static List<Realm> getRealms() {
        return realms;
    }

    public static void setHumans(List<Human> humanlist) {
        humans = humanlist;
    }

    public static void setRealms(List<Realm> realmslist) {
        realms = realmslist;
    }

    public static void addRealm(Realm realm) {
        realms.add(realm);
    }

    public static void removeRealm(Realm realm) {
        realms.remove(realm);
    }

    public static void addHuman(Human human) {
        humans.add(human);
    }

    public static void removeHuman(Human human) {
        realms.remove(human);
    }

}
