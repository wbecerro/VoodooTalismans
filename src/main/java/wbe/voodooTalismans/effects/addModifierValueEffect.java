package wbe.voodooTalismans.effects;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlotGroup;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;

public class addModifierValueEffect extends TalismanEffect {

    private Attribute attribute;

    private NamespacedKey attributeKey;

    public addModifierValueEffect(double value, String lore, Attribute attribute, String talisman) {
        super(value, lore);
        this.attribute = attribute;
        attributeKey = new NamespacedKey(VoodooTalismans.getInstance(), "talisman" + attribute.toString() + talisman);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        AttributeModifier attributeModifier = new AttributeModifier(attributeKey, value * playerTalisman.getLevel(),
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

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
}
