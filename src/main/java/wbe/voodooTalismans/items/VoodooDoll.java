package wbe.voodooTalismans.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.voodooTalismans.VoodooTalismans;

import java.util.ArrayList;

public class VoodooDoll extends ItemStack {

    public VoodooDoll() {
        super(VoodooTalismans.config.voodooDollMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(VoodooTalismans.config.voodooDollMaterial);
        }

        meta.setDisplayName(VoodooTalismans.config.voodooDollName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : VoodooTalismans.config.voodooDollLore) {
            lore.add(line.replace("&", "§"));
        }
        meta.setLore(lore);

        NamespacedKey voodooDollKey = new NamespacedKey(VoodooTalismans.getInstance(), "voodooDoll");
        meta.getPersistentDataContainer().set(voodooDollKey, PersistentDataType.BOOLEAN, true);

        if(VoodooTalismans.config.voodooDollGlow) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.setMaxStackSize(64);

        setItemMeta(meta);
    }
}
