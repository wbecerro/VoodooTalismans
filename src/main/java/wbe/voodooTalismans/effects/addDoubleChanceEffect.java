package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.tartarusRiches.TartarusRiches;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.yggdrasilsBark.YggdrasilsBark;

public class addDoubleChanceEffect extends TalismanEffect {

    public enum DoubleChanceType {
        FISHING,
        WOODCUTTING,
        MINING,
        FOOD
    }

    private DoubleChanceType type;

    public addDoubleChanceEffect(double value, String lore, DoubleChanceType type) {
        super(value, lore);
        this.type = type;
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        if(type.equals(DoubleChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 2, "");
        } else if(type.equals(DoubleChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 2, "");
        } else if(type.equals(DoubleChanceType.MINING)) {
            wbe.tartarusRiches.utils.Utilities utilities = TartarusRiches.utilities;
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 1);
        } else if(type.equals(DoubleChanceType.FOOD)) {
            wbe.demetersTeachings.utils.Utilities utilities = DemetersTeachings.utilities;
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 1);
        }
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        if(type.equals(DoubleChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 2, "");
        } else if(type.equals(DoubleChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 2, "");
        } else if(type.equals(DoubleChanceType.MINING)) {
            wbe.tartarusRiches.utils.Utilities utilities = TartarusRiches.utilities;
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 1);
        } else if(type.equals(DoubleChanceType.FOOD)) {
            wbe.demetersTeachings.utils.Utilities utilities = DemetersTeachings.utilities;
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 1);
        }
    }
}
