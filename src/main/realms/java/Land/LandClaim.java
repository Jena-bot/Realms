package main.realms.java.Land;

import main.realms.utils.ChatInfo;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LandClaim implements Listener {
    public static Block block1;
    public static Block block2;
    public static boolean done;

    @SuppressWarnings("deprecation")
    @EventHandler
    public static void onGoldenShovel(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getType().equals(Material.GOLDEN_SHOVEL)) {
            if (block1 == null) {
                block1 = event.getClickedBlock();
                event.getPlayer().sendMessage(ChatInfo.prefix("&6Corner 1 has been set on " + block1.getLocation().getBlockX() + ", " + block1.getLocation().getBlockZ()));
                done = false;
            }
            else if (block2 == null) {
                block2 = event.getClickedBlock();
                event.getPlayer().sendMessage(ChatInfo.prefix("&6Corner 2 has been set on " + block2.getLocation().getBlockX() + ", " + block2.getLocation().getBlockZ()));
                done = true;
            } else {
                done = true;
                event.getPlayer().sendMessage(ChatInfo.prefix("&cBoth corners have been set, do /cancel to redo your selection."));
            }
        }
    }
}
