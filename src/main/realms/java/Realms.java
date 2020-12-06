package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;

import java.util.LinkedList;
import java.util.List;

public class Realms {
    public List<Realm> realms = new LinkedList<>();
    public List<Human> humans = new LinkedList<>();

    // this is a quick class that allows for easy access to a list of realms and humans without having to look through files.
    public List<Human> getHumans() {
        return humans;
    }

    public List<Realm> getRealms() {
        return realms;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }

    public void setRealms(List<Realm> realms) {
        this.realms = realms;
    }

    public void addRealm(Realm realm) {
        realms.add(realm);
    }

    public void removeRealm(Realm realm) {
        realms.remove(realm);
    }

    public void addHuman(Human human) {
        humans.add(human);
    }

    public void removeHuman(Human human) {
        realms.remove(human);
    }
}
