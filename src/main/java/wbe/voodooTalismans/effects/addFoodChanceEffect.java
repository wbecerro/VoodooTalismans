package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.utils.Utilities;
import wbe.voodooTalismans.config.PlayerTalisman;

public class addFoodChanceEffect extends TalismanEffect {

    public addFoodChanceEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        Utilities utilities = DemetersTeachings.utilities;
        utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 0);
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        Utilities utilities = DemetersTeachings.utilities;
        utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 0);
    }
}
