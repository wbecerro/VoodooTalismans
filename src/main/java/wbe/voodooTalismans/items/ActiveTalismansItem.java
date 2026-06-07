package wbe.voodooTalismans.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.config.Talisman;

import java.util.ArrayList;
import java.util.List;

public class ActiveTalismansItem extends ItemStack {

    public ActiveTalismansItem(List<PlayerTalisman> talismans) {
        super(VoodooTalismans.config.activeTalismansItemMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(VoodooTalismans.config.activeTalismansItemMaterial);
        }

        meta.setDisplayName(VoodooTalismans.config.activeTalismansItemName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : VoodooTalismans.config.activeTalismansItemLore) {
            lore.add(line.replace("&", "§")
                    .replace("%length%", String.valueOf(talismans.size())));
        }

        for(PlayerTalisman talisman : talismans) {
            lore.add(VoodooTalismans.config.activeTalismansTalismanLine
                    .replace("%name%", talisman.getType().getName())
                    .replace("%level%", String.valueOf(talisman.getLevel())));
        }

        lore.add("");
        lore.add(VoodooTalismans.config.activeTalismansItemDeactivateAll);
        meta.setLore(lore);

        NamespacedKey baseKey = new NamespacedKey(VoodooTalismans.getInstance(), "activeTalismans");
        meta.getPersistentDataContainer().set(baseKey, PersistentDataType.BOOLEAN, true);

        if(VoodooTalismans.config.activeTalismansItemGlow) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
