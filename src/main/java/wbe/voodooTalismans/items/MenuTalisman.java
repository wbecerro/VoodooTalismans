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
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.ArrayList;

public class MenuTalisman extends ItemStack {

    public MenuTalisman(PlayerTalisman talisman) {
        super(talisman.getType().getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(talisman.getType().getMaterial());
        }

        meta.setDisplayName(talisman.getType().getName());

        ArrayList<String> lore = new ArrayList<>();
        if(talisman.getLevel() >= talisman.getType().getMaxLevel()) {
            lore.add(VoodooTalismans.config.menuMaxLevel.replace("%level%", String.valueOf(talisman.getLevel())));
        } else {
            lore.add(VoodooTalismans.config.menuLevel.replace("%level%", String.valueOf(talisman.getLevel())));
        }
        for(String loreLine : talisman.getType().getLore()) {
            lore.add(loreLine.replace("&", "§"));
        }
        lore.add("");
        lore.add(VoodooTalismans.config.menuEffects);
        for(TalismanEffect effect : talisman.getType().getEffects()) {
            lore.add(effect.calculateLore(talisman).replace("&", "§"));
        }
        lore.add("");
        lore.add(talisman.isActive() ? VoodooTalismans.config.menuActive : VoodooTalismans.config.menuUnactive);
        lore.add(talisman.isActive() ? VoodooTalismans.config.menuDeselect : VoodooTalismans.config.menuSelect);
        meta.setLore(lore);

        NamespacedKey petKey = new NamespacedKey(VoodooTalismans.getInstance(), "talismanType");
        meta.getPersistentDataContainer().set(petKey, PersistentDataType.STRING, talisman.getType().getId());

        if(talisman.getType().isGlow()) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);
    }
}
