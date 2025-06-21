package wbe.voodooTalismans.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;

import java.util.Collection;

public class Utilities {

    public AttributeModifier searchModifier(Collection<AttributeModifier> modifiers, NamespacedKey key) {
        for(AttributeModifier modifier : modifiers) {
            if(modifier.getKey().equals(key)) {
                return modifier;
            }
        }

        return null;
    }
}
