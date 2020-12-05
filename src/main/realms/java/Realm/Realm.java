package main.realms.java.Realm;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;

import java.io.File;
import java.util.List;

public class Realm {
    public static Human owner;
    public static List<Land> lands;
    public static List<Realm> vassals;
    public static File data;
}
