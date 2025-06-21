package wbe.voodooTalismans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class PlayerQuitListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleTalismanEffectsOnJoin(PlayerQuitEvent event) {
        VoodooTalismans.utilities.savePlayerData(event.getPlayer());
        ArrayList<PlayerTalisman> activeTalismans = VoodooTalismans.activeTalismans.get(event.getPlayer());
        if(activeTalismans == null || activeTalismans.isEmpty()) {
            return;
        }

        for(PlayerTalisman talisman : activeTalismans) {
            for(TalismanEffect effect : talisman.getType().getEffects()) {
                effect.deactivateEffect(event.getPlayer());
            }
        }

        VoodooTalismans.playerTalismans.remove(event.getPlayer());
        VoodooTalismans.activeTalismans.remove(event.getPlayer());
    }
}
