package wbe.voodooTalismans.listeners;

import io.lumine.mythic.bukkit.events.MythicMobLootDropEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class MythicMobLootDropListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleMythicDropsTalismanEffects(MythicMobLootDropEvent event) {
        if(!(event.getKiller() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getKiller();

        for(PlayerTalisman talisman : VoodooTalismans.activeTalismans.getOrDefault(player, new ArrayList<>())) {
            for(TalismanEffect effect : talisman.getType().getEffects()) {
                effect.activateEffect(player, talisman, event);
            }
        }
    }
}
