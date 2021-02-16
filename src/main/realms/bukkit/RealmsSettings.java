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

package main.realms.bukkit;

import main.realms.bukkit.interfaces.RealmsPlayer;
import main.realms.bukkit.objects.BasePlayer;
import main.realms.utils.exceptions.InvalidConfigurationException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class RealmsSettings {
    public Class<?> PlayerClass;

    public RealmsSettings(FileConfiguration config) {

        // Get the player class
        try {
            PlayerClass = Class.forName(config.getString("player-interface"));

            List<Class<?>> interfaces = Arrays.asList(PlayerClass.getInterfaces());
            if (!interfaces.contains(RealmsPlayer.class))
                throw new InvalidConfigurationException();

        } catch (ClassNotFoundException e) {
            Bukkit.getServer().getLogger().severe("[Realms] Custom player interface was defined in config but not found.");
            PlayerClass = BasePlayer.class;
        } catch (InvalidConfigurationException e) {
            Bukkit.getServer().getLogger().severe("[Realms] Custom player interface does not implement RealmsPlayer");
            PlayerClass = BasePlayer.class;
        }
    }
}
