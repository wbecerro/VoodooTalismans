package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.utils.Utilities;

public class addGemChanceEffect extends TalismanEffect {

    public addGemChanceEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player) {
        Utilities utilities = TartarusRiches.utilities;
        utilities.addChanceToPlayer(player, value, 0);
    }

    public void deactivateEffect(Player player) {
        Utilities utilities = TartarusRiches.utilities;
        utilities.removeChanceFromPlayer(player, value, 0);
    }
}
