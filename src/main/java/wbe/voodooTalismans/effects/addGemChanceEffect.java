package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.utils.Utilities;
import wbe.voodooTalismans.config.PlayerTalisman;

public class addGemChanceEffect extends TalismanEffect {

    public addGemChanceEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman) {
        Utilities utilities = TartarusRiches.utilities;
        utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 0);
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman) {
        Utilities utilities = TartarusRiches.utilities;
        utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 0);
    }
}
