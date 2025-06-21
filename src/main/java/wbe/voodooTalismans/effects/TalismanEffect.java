package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;

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

    public abstract void activateEffect(Player player);

    public abstract void deactivateEffect(Player player);

    public String calculateLore() {
        return lore.replace("%value%", String.valueOf(value));
    }
}
