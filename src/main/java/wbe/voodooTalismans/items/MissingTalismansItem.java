package wbe.voodooTalismans.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.Talisman;

import java.util.ArrayList;
import java.util.List;

public class MissingTalismansItem extends ItemStack {

    private int maxPage;

    public MissingTalismansItem(List<Talisman> talismans, int page) {
        super(VoodooTalismans.config.missingTalismansItemMaterial);
        maxPage = (int) Math.ceil((double) talismans.size() / VoodooTalismans.config.missingTalismansMaxTalismansPerPage);

        if(page > maxPage) {
            page = 1;
        } else if(page < 1) {
            page = maxPage;
        }

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(VoodooTalismans.config.missingTalismansItemMaterial);
        }

        meta.setDisplayName(VoodooTalismans.config.missingTalismansItemName
                .replace("%current%", String.valueOf(page))
                .replace("%max%", String.valueOf(maxPage)));

        ArrayList<String> lore = new ArrayList<>();
        for(String line : VoodooTalismans.config.missingTalismansItemLore) {
            lore.add(line.replace("&", "§")
                    .replace("%length%", String.valueOf(talismans.size())));
        }
        meta.setLore(lore);

        NamespacedKey baseKey = new NamespacedKey(VoodooTalismans.getInstance(), "missingTalismansPage");
        meta.getPersistentDataContainer().set(baseKey, PersistentDataType.INTEGER, page);

        if(VoodooTalismans.config.missingTalismansItemGlow) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        setItemMeta(meta);

        setTalismans(talismans, page);
    }

    private void setTalismans(List<Talisman> talismans, int page) {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();

        int start = VoodooTalismans.config.missingTalismansMaxTalismansPerPage * (page - 1);
        int max = Math.min(VoodooTalismans.config.missingTalismansMaxTalismansPerPage * page, talismans.size());
        for (int i = start;i<max;i++) {
            Talisman talisman = talismans.get(i);
            lore.add(talisman.getName());
        }

        lore.add("");
        lore.add(VoodooTalismans.config.missingTalismansItemNextPage);
        lore.add(VoodooTalismans.config.missingTalismansItemPreviousPage);

        meta.setLore(lore);
        setItemMeta(meta);
    }
}
