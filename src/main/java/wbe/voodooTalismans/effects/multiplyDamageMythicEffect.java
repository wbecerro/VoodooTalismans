package wbe.voodooTalismans.effects;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.List;

public class multiplyDamageMythicEffect extends TalismanEffect {

    private List<String> mobs;

    private final MythicBukkit mythicBukkit = MythicBukkit.inst();

    public multiplyDamageMythicEffect(double value, String lore, List<String> mobs) {
        super(value, lore);
        this.mobs = mobs;
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(!(event instanceof EntityDamageByEntityEvent damageEvent)) {
            return;
        }

        Entity entity = damageEvent.getEntity();
        if(!mythicBukkit.getMobManager().isMythicMob(entity)) {
            return;
        }

        double damage = damageEvent.getDamage();
        for(String mob : mobs) {
            if(mythicBukkit.getMobManager().getMythicMobInstance(entity).getType().getInternalName().equalsIgnoreCase(mob)) {
                damage = damage * (value * playerTalisman.getLevel() + 1);
                damageEvent.setDamage(damage);
                return;
            }
        }
    }


    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {

    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.valueOf(value * talisman.getLevel() * 100));
    }
}
