package wbe.voodooTalismans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.events.PlayerGetTalismanEvent;

import java.util.ArrayList;

public class PlayerGetTalismanListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void giveTalismanOnEvent(PlayerGetTalismanEvent event) {
        if(event.isCancelled()) {
            return;
        }

        ArrayList<PlayerTalisman> talismans = VoodooTalismans.playerTalismans.getOrDefault(event.getPlayer(), new ArrayList<>());
        talismans.add(event.getPlayerTalisman());
        VoodooTalismans.playerTalismans.put(event.getPlayer(), talismans);
    }
}
