package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.yggdrasilsBark.YggdrasilsBark;

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

    public void activateEffect(Player player, PlayerTalisman playerTalisman) {
        if(type.equals(ItemChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 0, "");
        } else if(type.equals(ItemChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 0, "");
        }
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman) {
        if(type.equals(ItemChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 0, "");
        } else if(type.equals(ItemChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 0, "");
        }
    }
}
