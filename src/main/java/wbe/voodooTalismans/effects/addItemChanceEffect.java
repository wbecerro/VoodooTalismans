package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.acuaticLostWealth.AcuaticLostWealth;

public class addItemChanceEffect extends TalismanEffect {

    public enum ItemChanceType {
        FISHING,
        WOODCUTTING
    }

    private ItemChanceType type;

    public addItemChanceEffect(double value, String lore, ItemChanceType type) {
        super(value, lore);
        this.type = type;
    }

    public void activateEffect(Player player) {
        if(type.equals(ItemChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value, 0, "");
        } else if(type.equals(ItemChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.addChanceToPlayer(player, value, 0, "");
        }
    }

    public void deactivateEffect(Player player) {
        if(type.equals(ItemChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value, 0, "");
        } else if(type.equals(ItemChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = new wbe.yggdrasilsBark.utils.Utilities();
            utilities.removeChanceFromPlayer(player, value, 0, "");
        }
    }
}
