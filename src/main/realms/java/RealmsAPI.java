package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import main.realms.java.Realm.Realm;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealmsAPI {

    @Nullable
    private static Human getExactHuman(String uuid) {
        for (Human human : RealmsMain.humans) if (human.getUuid().toString().equalsIgnoreCase(uuid)) return human;
        return null;
    }

    @Nullable
    public static Human getHuman(Player player) {
        return getExactHuman(player.getUniqueId().toString());
    }

    @Nullable
    public static Human getHuman(UUID uuid) {
        return getExactHuman(uuid.toString());
    }

    @Nullable
    public static Human getHuman(String name) {
        return getExactHuman(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
    }

    // Land
    @Nullable
    public static Land getLand(Chunk chunk) {
        for (Land land : RealmsMain.lands) if (land.chunks.contains(chunk)) return land;
        return null;
    }

    @Nullable
    public static Land getLand(UUID uuid) {
        return getExactLand(uuid.toString());
    }

    @Nullable
    public static Land getExactLand(String uuid) {
        for (Land land : RealmsMain.lands) if (land.getUuid().toString().equalsIgnoreCase(uuid)) return land;
        return null;
    }

    @Nullable
    public static Land getLand(String name) {
        for (Land land : RealmsMain.lands) if (land.name.equalsIgnoreCase(name)) return land;
        return null;
    }

    @Nullable
    public static List<Land> getOwnedLand(Human human) {
        List<Land> lands = new ArrayList<>();
        for (Land land : RealmsMain.lands) if (land.getOwnerUUID().toString() == human.getUuid().toString()) lands.add(land);
        return lands;
    }

    @Nullable
    public static Realm getRealmOwner(Human human) {
        for (Realm realm : RealmsMain.realms) if (realm.getOwner().getUuid().toString().equalsIgnoreCase(human.getUuid().toString())) return realm;
        return null;
    }

    @Nullable
    public static Realm getRealm(UUID uuid) {
        for (Realm realm : RealmsMain.realms) if (realm.getUuid().toString().equalsIgnoreCase(uuid.toString())) return realm;
        return null;
    }

    @Nullable
    public static Realm getRealm(String name) {
        for (Realm realm : RealmsMain.realms) if (realm.getName().equalsIgnoreCase(name)) return realm;
        return null;
    }

    @Nullable
    public static Realm getRealm(Human human) {
        for (Realm realm : RealmsMain.realms) for (Human player : realm.getMembers()) if (player == human) return realm;
        return null;
    }

    @Nullable
    public static Realm getRealm(Chunk chunk) {
        for (Realm realm : RealmsMain.realms) if (realm.getClaims().contains(chunk)) return realm;
        return null;
    }
}
