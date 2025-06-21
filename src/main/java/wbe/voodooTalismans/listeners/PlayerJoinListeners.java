package wbe.voodooTalismans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class PlayerJoinListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleTalismanEffectsOnJoin(PlayerJoinEvent event) {
        VoodooTalismans.utilities.loadPlayerData(event.getPlayer());
        ArrayList<PlayerTalisman> activeTalismans = VoodooTalismans.activeTalismans.get(event.getPlayer());
        if(activeTalismans == null || activeTalismans.isEmpty()) {
            return;
        }

        for(PlayerTalisman talisman : activeTalismans) {
            for(TalismanEffect effect : talisman.getType().getEffects()) {
                effect.activateEffect(event.getPlayer());
            }
        }
    }
}
