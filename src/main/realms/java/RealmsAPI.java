package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Land.Land;
import main.realms.java.Realm.Realm;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RealmsAPI {

    @Nullable
    private static Human getExactHuman(String uuid) throws NotFoundException {
        for (Human human : RealmsMain.humans) if (human.getUuid().toString().equalsIgnoreCase(uuid)) return human;
        throw new NotFoundException();
    }

    @Nullable
    public static Human getHuman(Player player) throws RealmsException {
        return getExactHuman(player.getUniqueId().toString());
    }

    @Nullable
    public static Human getHuman(UUID uuid) throws RealmsException {
        return getExactHuman(uuid.toString());
    }

    @Nullable
    public static Human getHuman(String name) throws RealmsException {
        return getExactHuman(Bukkit.getOfflinePlayer(name).getUniqueId().toString());
    }

    // Land
    @Nullable
    public static Land getLand(Chunk chunk) {
        for (Land land : RealmsMain.lands) if (land.chunks.contains(chunk)) return land;
        return null;
    }

    @Nullable
    public static Land getLand(UUID uuid) throws NotFoundException {
        for (Land land : RealmsMain.lands) if (land.uuid == uuid) return land;
        throw new NotFoundException();
    }

    @Nullable
    public static Land getLand(String name) throws NotFoundException {
        for (Land land : RealmsMain.lands) if (land.name.equalsIgnoreCase(name)) return land;
        throw new NotFoundException();
    }

    @Nullable
    public static List<Land> getOwnedLand(Human human) throws NotFoundException {
        List<Land> lands = new ArrayList<>();
        for (Land land : RealmsMain.lands) if (land.getOwner() == human) lands.add(land);
        if (lands.toArray().length == 0) throw new NotFoundException();
        else return lands;
    }

    @Nullable
    public static Realm getRealmOwner(Human human) throws NotFoundException {
        for (Realm realm : RealmsMain.realms) if (realm.getOwner().getUuid().toString().equalsIgnoreCase(human.getUuid().toString())) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static Realm getRealm(UUID uuid) throws NotFoundException {
        for (Realm realm : RealmsMain.realms) if (realm.getUuid().toString().equalsIgnoreCase(uuid.toString())) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static Realm getRealm(String name) throws NotFoundException {
        for (Realm realm : RealmsMain.realms) if (realm.getName().equalsIgnoreCase(name)) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static Realm getRealm(Human human) throws NotFoundException {
        for (Realm realm : RealmsMain.realms) for (Land land : realm.lands) if (land.getOwner() == human) return realm;
        return null;
    }

    @Nullable
    public static Realm getRealm(Chunk chunk) {
        for (Realm realm : RealmsMain.realms) if (realm.getClaims().contains(chunk)) return realm;
        return null;
    }
}
