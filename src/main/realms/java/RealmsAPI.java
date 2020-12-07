package main.realms.java;

import main.realms.java.Human.Human;
import main.realms.utils.RealmsException;
import org.bukkit.entity.Player;

public class RealmsAPI {

    public static Human getHuman(Player player) throws RealmsException {
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
                throw new RealmsException("102");
            }
        }
    }
}
