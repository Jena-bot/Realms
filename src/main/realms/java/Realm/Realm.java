package main.realms.java.Realm;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Realm {
    public static Human owner;
    public static List<Land> lands = new ArrayList<>();
    public static List<Realm> vassals = new LinkedList<>();
    private static File data;

    public static Human getOwner() {
        return owner;
    }

    public static List<Land> getLands() {
        return lands;
    }

    public static List<Realm> getVassals() {
        return vassals;
    }

    public static void setOwner(Human owner) {
        Realm.owner = owner;
    }

    public void addLand(Land land) {
        lands.add(land);
    }

    public void addVassal(Realm realm) {
        vassals.add(realm);
    }
}
