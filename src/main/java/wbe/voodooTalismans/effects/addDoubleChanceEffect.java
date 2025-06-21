package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.tartarusRiches.TartarusRiches;

public class addDoubleChanceEffect extends TalismanEffect {

    public enum DoubleChanceType {
        FISHING,
        WOODCUTTING,
        MINING
    }

    private DoubleChanceType type;

    public addDoubleChanceEffect(double value, String lore, DoubleChanceType type) {
        super(value, lore);
        this.type = type;
    }

    public void activateEffect(Player player) {
        if(type.equals(DoubleChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value, 3, "");
        } else if(type.equals(DoubleChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.addChanceToPlayer(player, value, 3, "");
        } else if(type.equals(DoubleChanceType.MINING)) {
            wbe.tartarusRiches.utils.Utilities utilities = TartarusRiches.utilities;
            utilities.addChanceToPlayer(player, value, 1);
        }
    }

    public void deactivateEffect(Player player) {
        if(type.equals(DoubleChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value, 3, "");
        } else if(type.equals(DoubleChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.removeChanceFromPlayer(player, value, 3, "");
        } else if(type.equals(DoubleChanceType.MINING)) {
            wbe.tartarusRiches.utils.Utilities utilities = TartarusRiches.utilities;
            utilities.removeChanceFromPlayer(player, value, 1);
        }
    }
}
