package wbe.voodooTalismans.config;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.effects.TalismanEffect;
import wbe.voodooTalismans.items.MenuTalisman;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerTalisman {

    private Talisman type;

    private Player player;

    private Boolean active = false;

    public PlayerTalisman(Talisman type, Player player, boolean active) {
        this.type = type;
        this.player = player;
        this.active = active;
    }

    public Talisman getType() {
        return type;
    }

    public void setType(Talisman type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void activate() {
        for(TalismanEffect effect : type.getEffects()) {
            effect.activateEffect(player);
        }

        ArrayList<PlayerTalisman> actives = VoodooTalismans.activeTalismans.get(player);
        active = true;
        if(actives == null) {
            VoodooTalismans.activeTalismans.put(player, new ArrayList<>(Arrays.asList(this)));
        } else {
            actives.add(this);
            VoodooTalismans.activeTalismans.put(player, actives);
        }
    }

    public void deactivate() {
        for(TalismanEffect effect : type.getEffects()) {
            effect.deactivateEffect(player);
        }

        ArrayList<PlayerTalisman> actives = VoodooTalismans.activeTalismans.get(player);
        active = false;
        if(actives != null) {
            actives.remove(this);
            VoodooTalismans.activeTalismans.put(player, actives);
        }
    }

    public ItemStack getMenuItem() {
        return new MenuTalisman(this);
    }
}
