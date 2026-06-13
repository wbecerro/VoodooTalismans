package wbe.voodooTalismans.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class DamageEntityListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleDamageTalismanEffects(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) {
            return;
        }

        if(event.getDamage() <= 1) {
            return;
        }

        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        if(!(event.getDamageSource().getCausingEntity() instanceof Player player)) {
            return;
        }

        for(PlayerTalisman talisman : VoodooTalismans.activeTalismans.getOrDefault(player, new ArrayList<>())) {
            for(TalismanEffect effect : talisman.getType().getEffects()) {
                effect.activateEffect(player, talisman, event);
            }
        }
    }
}
