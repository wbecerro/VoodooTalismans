package wbe.voodooTalismans.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import wbe.voodooTalismans.effects.furnaceSmeltEffect;

import java.util.UUID;

public class FurnaceListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleFurnacesStartSmelting(FurnaceStartSmeltEvent event) {
        Block furnace = event.getBlock();
        if(!furnaceSmeltEffect.playerFurnaces.containsKey(furnace.getLocation())) {
            return;
        }

        UUID uuid = furnaceSmeltEffect.playerFurnaces.get(furnace.getLocation());
        Player player = Bukkit.getPlayer(uuid);
        if(!furnaceSmeltEffect.effectPlayers.containsKey(player)) {
            return;
        }

        double effect = 1 - furnaceSmeltEffect.effectPlayers.get(player);
        event.setTotalCookTime((int) (event.getTotalCookTime() * effect));
    }
}
