package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.acuaticLostWealth.AcuaticLostWealth;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.yggdrasilsBark.YggdrasilsBark;

public class addBoostRarityChanceEffect extends TalismanEffect {

    public enum BoostRarityChanceType {
        FISHING,
        WOODCUTTING,
    }

    private BoostRarityChanceType type;

    private String rarity;

    public addBoostRarityChanceEffect(double value, String lore, BoostRarityChanceType type, String rarity) {
        super(value, lore);
        this.type = type;
        this.rarity = rarity;
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        if(type.equals(BoostRarityChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 3, rarity);
        } else if(type.equals(BoostRarityChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.addChanceToPlayer(player, value * playerTalisman.getLevel(), 3, rarity);
        }
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        if(type.equals(BoostRarityChanceType.FISHING)) {
            wbe.acuaticLostWealth.util.Utilities utilities = new wbe.acuaticLostWealth.util.Utilities(AcuaticLostWealth.getPlugin(AcuaticLostWealth.class));
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 3, rarity);
        } else if(type.equals(BoostRarityChanceType.WOODCUTTING)) {
            wbe.yggdrasilsBark.utils.Utilities utilities = YggdrasilsBark.utilities;
            utilities.removeChanceFromPlayer(player, value * playerTalisman.getLevel(), 3, rarity);
        }
    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", value * talisman.getLevel() * 100));
    }
}
