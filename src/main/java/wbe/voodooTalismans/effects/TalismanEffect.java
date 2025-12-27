package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

public abstract class TalismanEffect {

    protected double value;

    protected String lore;

    public TalismanEffect(double value, String lore) {
        this.value = value;
        this.lore = lore;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public abstract void activateEffect(Player player, PlayerTalisman playerTalisman, Event event);

    public abstract void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event);

    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", value * talisman.getLevel()));
    }
}
