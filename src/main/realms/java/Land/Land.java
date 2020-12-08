package main.realms.java.Land;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.java.objects.WorldCoord;

import java.io.File;
import java.util.UUID;

public class Land {
    public UUID uuid;
    public WorldCoord coord;
    public Realm realm;
    public Human owner;
    private File data;

    //todo contructors
    public Land(WorldCoord coord, Human owner) {
        this.coord = coord;
        this.owner = owner;
        this.realm = owner.getRealm();
    }

    // getters and setters

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Realm getRealm() {
        return realm;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public void setCoord(WorldCoord coord) {
        this.coord = coord;
    }

    public WorldCoord getCoord() {
        return coord;
    }
}
