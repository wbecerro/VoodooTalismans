package wbe.voodooTalismans.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class McMMOPlayerXpGainListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleMcMMOXpTalismanEffects(McMMOPlayerXpGainEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        for(PlayerTalisman talisman : VoodooTalismans.activeTalismans.getOrDefault(player, new ArrayList<>())) {
            for(TalismanEffect effect : talisman.getType().getEffects()) {
                effect.activateEffect(player, talisman, event);
            }
        }
    }
}
