package main.realms.java.Realm;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Realm {
    public Human owner;
    public String name;
    public long registered;
    public UUID uuid;
    public List<Land> lands = new ArrayList<>();
    public List<Realm> vassals = new ArrayList<>();
    private File data;

    public Human getOwner() {
        return owner;
    }

    public List<Land> getLands() {
        return lands;
    }

    public List<Realm> getVassals() {
        return vassals;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public void addLand(Land land) {
        lands.add(land);
    }

    public void addVassal(Realm realm) {
        vassals.add(realm);
    }

    public long getRegistered() {
        return registered;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistered(long registered) {
        this.registered = registered;
    }
}
