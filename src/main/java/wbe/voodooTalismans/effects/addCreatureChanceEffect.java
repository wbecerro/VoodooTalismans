package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.acuaticLostWealth.AcuaticLostWealth;

public class addCreatureChanceEffect extends TalismanEffect {

    public enum CreatureChanceType {
        FISHING,
        WOODCUTTING
    }

    private CreatureChanceType type;

    public addCreatureChanceEffect(double value, String lore, CreatureChanceType type) {
        super(value, lore);
        this.type = type;
    }

    public void activateEffect(Player player) {
        if(type.equals(CreatureChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value, 1, "");
        } else if(type.equals(CreatureChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.addChanceToPlayer(player, value, 1, "");
        }
    }

    public void deactivateEffect(Player player) {
        if(type.equals(CreatureChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value, 1, "");
        } else if(type.equals(CreatureChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.removeChanceFromPlayer(player, value, 1, "");
        }
    }
}
