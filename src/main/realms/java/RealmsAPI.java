package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.java.objects.PlayerCache;
import main.realms.utils.exceptions.NotFoundException;
import main.realms.utils.exceptions.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RealmsAPI {

    @Nullable
    private static Human getExactHuman(String uuid) throws NotFoundException {
        for (Human human : RealmsMain.humans) {
            if (human.getUuid().toString().equalsIgnoreCase(uuid)) return human;
        }
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

    @Nullable
    public static Realm getRealm(UUID uuid) throws NotFoundException {
        for (Realm realm : Realms.getRealms()) if (realm.getUuid() == uuid) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static Realm getRealm(String name) throws NotFoundException {
        for (Realm realm : Realms.getRealms()) if (realm.getName().equals(name)) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static PlayerCache getPlayerCache(Player player) throws NotFoundException {
        for (PlayerCache cache : Realms.getCaches()) {
            if (cache.getPlayer() == player.getUniqueId()) return cache;
        }
        throw new NotFoundException();
    }
}
