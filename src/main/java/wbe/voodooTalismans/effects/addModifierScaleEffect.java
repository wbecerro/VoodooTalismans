package wbe.voodooTalismans.effects;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlotGroup;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;

public class addModifierScaleEffect extends TalismanEffect {

    private Attribute attribute;

    private NamespacedKey attributeKey;

    public addModifierScaleEffect(double value, String lore, Attribute attribute, String talisman) {
        super(value, lore);
        this.attribute = attribute;
        attributeKey = new NamespacedKey(VoodooTalismans.getInstance(), "talisman" + attribute.getKey().getKey() + talisman);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        double attributeValue = value * playerTalisman.getLevel();
        AttributeModifier attributeModifier = new AttributeModifier(attributeKey, attributeValue,
                AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);

        AttributeModifier oldAttribute = VoodooTalismans.utilities.searchModifier(player.getAttribute(attribute).getModifiers(), attributeKey);
        if(oldAttribute != null) {
            return;
        }
        player.getAttribute(attribute).addModifier(attributeModifier);
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        AttributeModifier attributeModifier = VoodooTalismans.utilities.searchModifier(player.getAttribute(attribute).getModifiers(), attributeKey);
        if(attributeModifier == null) {
            return;
        }

        player.getAttribute(attribute).removeModifier(attributeModifier);
    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.valueOf(Math.round(value * talisman.getLevel() * 100 * 10.0) / 10.0));
    }
}
