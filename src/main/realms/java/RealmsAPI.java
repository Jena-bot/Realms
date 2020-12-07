package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.java.Realm.Realm;
import main.realms.utils.NotFoundException;
import main.realms.utils.RealmsException;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RealmsAPI {

    @Nullable
    public static Human getHuman(Player player) throws NotFoundException {
        // Attempt to get the human from the database.
        for (Human human : Realms.getHumans()) {
            if (human.getPlayer() == player || human.getUuid() == player.getUniqueId()) {
                return human;
            }
        }

        try {
            return new Human(player, false);
        } catch (RealmsException e) {
            e.printStackTrace();
            try {
                return new Human(player, true);
            } catch (RealmsException e1) {
                e1.printStackTrace();
                throw new NotFoundException();
            }
        }
    }

    @Nullable
    public static Human getHuman(UUID uuid) throws NotFoundException {
        for (var human : Realms.getHumans()) {
            if (human.getUuid() == uuid) return human;
        }

        return getHuman(Bukkit.getPlayer(uuid));
    }

    public static Human getHuman(String name) throws NotFoundException {
        return getHuman(Bukkit.getPlayer(name));
    }

    @Nullable
    public static Realm getRealm(UUID uuid) throws NotFoundException {
        for (var realm : Realms.getRealms()) if (realm.getUuid() == uuid) return realm;
        throw new NotFoundException();
    }

    @Nullable
    public static Realm getRealm(String name) throws NotFoundException {
        for (var realm : Realms.getRealms()) if (realm.getName().equals(name)) return realm;
        throw new NotFoundException();
    }
}
