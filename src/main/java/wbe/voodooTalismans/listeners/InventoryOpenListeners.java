package wbe.voodooTalismans.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.effects.furnaceSmeltEffect;

import java.util.Arrays;
import java.util.List;

public class InventoryOpenListeners implements Listener {

    private List validInventoryTypes = Arrays.asList(InventoryType.FURNACE, InventoryType.BLAST_FURNACE, InventoryType.SMOKER);

    @EventHandler(priority = EventPriority.NORMAL)
    public void addPlayerToFurnacesData(InventoryOpenEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = (Player) event.getPlayer();
        if(!furnaceSmeltEffect.effectPlayers.containsKey(player)) {
            return;
        }

        Inventory inventory = event.getInventory();
        if(!validInventoryTypes.contains(inventory.getType())) {
            return;
        }

        Location furnaceLocation = event.getInventory().getLocation();
        if(furnaceSmeltEffect.playerFurnaces.containsKey(furnaceLocation)) {
           if(!furnaceSmeltEffect.effectPlayers.containsKey(
                   Bukkit.getPlayer(furnaceSmeltEffect.playerFurnaces.get(furnaceLocation)))) {
               furnaceSmeltEffect.playerFurnaces.put(furnaceLocation, player.getUniqueId());
               player.sendMessage(VoodooTalismans.messages.registeredFurnace);
           }

           return;
        }

        furnaceSmeltEffect.playerFurnaces.put(furnaceLocation, player.getUniqueId());
        player.sendMessage(VoodooTalismans.messages.registeredFurnace);
    }
}
