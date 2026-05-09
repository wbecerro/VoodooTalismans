package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.gaiasFindings.GaiasFindings;
import wbe.gaiasFindings.utils.Utilities;
import wbe.voodooTalismans.config.PlayerTalisman;

public class addRuneChanceEffect extends TalismanEffect {

    public addRuneChanceEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        Utilities utilities = GaiasFindings.utilities;
        utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 0);
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        Utilities utilities = GaiasFindings.utilities;
        utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 0);
    }
}
