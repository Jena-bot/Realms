/*
 * MIT License
 *
 * Copyright (c) Hafixion 2021.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.realms.bukkit.interfaces;

import com.serializer.java.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * An Interface to hold custom PlayerData of Players.
 *
 * @author Hafixion
 * @since 01b
 */
public interface RealmsPlayer extends Serializable {

    UUID getUUID();

    /**
    * Method used to get a Player's permissions.
    * defaults to null if not assigned, meaning they only have permissions in their realms.
    *
     * @return A Hashmap of permissions and realms.
     */
    default HashMap<Realm, List<Perm>> getPerms() {
        return null;
    }


    /**
     * Method used to check whether or not a player has permissions in a specific area
     * depends on {@link #getPerms()}
     *
     * @param action Action the player has done.
     * @param realm The realm in which the player has done the action
     * @return whether or not the player has permissions to do the specified action.
     */
    default boolean hasPerm(Perm.Action action, Realm realm) {
        for (Map.Entry<Realm, List<Perm>> entry : getPerms().entrySet()) {
            Realm realm1 = entry.getKey();
            List<Perm> permList = entry.getValue();

            for (Perm perm : permList) if (perm.getActions().contains(action)) if (realm1.equals(realm)) return true;
        }
        return false;
    }

    /**
     * Set Permissions, Used for permission utilities
     * @param hashMap New Permission HashMap
     */
    default void setPerm(HashMap<Realm, List<Perm>> hashMap) {}

    /**
     * Getting a player's name, used widely in commands.
     * @return the player's name
     */
    String getName();

    /**
     * Utility method used by some parts of the plugin, relies on {@link #getUUID()}
     * @return the offline player
     */
     default OfflinePlayer getOfflinePlayer() {
         return Bukkit.getOfflinePlayer(getUUID());
     }

    /**
     * @return Whether or not the player is online.
     */
     boolean isOnline();

    /**
     * @return the player's realm
     */
    Realm getRealm();

    default boolean hasRealm() {
        return getRealm() != null;
    }

    boolean equals(RealmsPlayer player);

    /**
     * @return Serializes the data inside the object.
     * @throws IOException the hell happened
     */
    default String serialize() throws IOException {
        return Serializer.serialize(this);
    }

    /**
     * Used to generate a player-data from a Bukkit Player, used when a new player joins and in other cases
     * @param player The Bukkit player get serialized.
     */
    void fromPlayer(Player player);
}
