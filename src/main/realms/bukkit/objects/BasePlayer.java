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

package main.realms.bukkit.objects;

import main.realms.bukkit.RealmsPlugin;
import main.realms.bukkit.interfaces.Perm;
import main.realms.bukkit.interfaces.Realm;
import main.realms.bukkit.interfaces.RealmsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * The default player implementation used in Realms.
 *
 * @author Hafixion
 * @since 01b
 */
public class BasePlayer implements RealmsPlayer {
    private UUID uuid;
    private long played;
    private long online;
    private HashMap<Perm, Realm> perms = new HashMap<>();

    public BasePlayer(Player player) {
        uuid = player.getUniqueId();
        played = player.getFirstPlayed();
        online = player.getLastPlayed();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return getOfflinePlayer().getName();
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
    public Realm getRealm() {

        // Loop through all realms and check to see if any of their owners or members are equal to this.
        for (var realm : RealmsPlugin.getInstance().getRealms())
            if (realm.getOwner() != this) {
                for (RealmsPlayer player : realm.getMembers())
                    if (player.equals(realm)) return realm;
            } else return realm;

        return null;
    }

    @Override
    public boolean equals(RealmsPlayer player) {
        return player.getUUID().equals(this.getUUID());
    }
}
