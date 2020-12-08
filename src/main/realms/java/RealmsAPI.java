package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.java.objects.PlayerCache;
import main.realms.utils.exceptions.NotFoundException;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RealmsAPI {

    @Nullable
    public static Human getHuman(Player player) throws NotFoundException {
        return getHuman(player.getUniqueId());
    }

    @Nullable
    public static Human getHuman(UUID uuid) throws NotFoundException {
        for (Human human : Realms.getHumans()) {
            if (human.getUuid() == uuid) {
                return human;
            }
        }
        throw new NotFoundException();
    }

    @Nullable
    public static Human getHuman(String name) throws NotFoundException {
        for (Human human : Realms.getHumans()) {
            if (human.getName().equals(name)) {
                return human;
            }
        }
        throw new NotFoundException();
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
