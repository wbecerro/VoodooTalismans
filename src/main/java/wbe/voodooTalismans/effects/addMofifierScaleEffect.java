package wbe.voodooTalismans.effects;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import wbe.voodooTalismans.VoodooTalismans;

public class addMofifierScaleEffect extends TalismanEffect {

    private Attribute attribute;

    private NamespacedKey attributeKey;

    public addMofifierScaleEffect(double value, String lore, Attribute attribute) {
        super(value, lore);
        this.attribute = attribute;
        attributeKey = new NamespacedKey(VoodooTalismans.getInstance(), "talisman" + attribute.toString());
    }

    public void activateEffect(Player player) {
        double attributeValue = value - 1;
        AttributeModifier attributeModifier = new AttributeModifier(attributeKey, attributeValue,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);

        AttributeModifier oldAttribute = VoodooTalismans.utilities.searchModifier(player.getAttribute(attribute).getModifiers(), attributeKey);
        if(oldAttribute != null) {
            return;
        }
        player.getAttribute(attribute).addModifier(attributeModifier);
    }

    public void deactivateEffect(Player player) {
        AttributeModifier attributeModifier = VoodooTalismans.utilities.searchModifier(player.getAttribute(attribute).getModifiers(), attributeKey);
        if(attributeModifier == null) {
            return;
        }

        player.getAttribute(attribute).removeModifier(attributeModifier);
    }
}
