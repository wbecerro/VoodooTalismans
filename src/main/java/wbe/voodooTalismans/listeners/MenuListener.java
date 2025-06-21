package wbe.voodooTalismans.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.config.Talisman;

import java.util.ArrayList;

public class MenuListener implements Listener {

    private static void fillBorders(Inventory inventory, int page) {
        ItemStack borde = new ItemStack(VoodooTalismans.config.menuBorder);
        ItemMeta bordeMeta = borde.getItemMeta();
        NamespacedKey currentPage = new NamespacedKey(VoodooTalismans.getInstance(), "currentPage");
        bordeMeta.setDisplayName(" ");
        bordeMeta.getPersistentDataContainer().set(currentPage, PersistentDataType.INTEGER, page);
        borde.setItemMeta(bordeMeta);

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                inventory.setItem(i, borde);
            }
            // Columna izquierda
            if(i % 9 == 0) {
                inventory.setItem(i, borde);
            }
            // Columna derecha
            if(i % 9 == 8) {
                inventory.setItem(i, borde);
            }
            // Última fila
            if(i >= 45){
                inventory.setItem(i, borde);
            }
        }
    }

    public static void fillTalismans(Inventory inventory, int page, ArrayList<PlayerTalisman> talismans) {
        int maxTalismanToShow = 28*page;
        int talismanIndex = 28*(page-1);
        if(28*page > talismans.size()) {
            maxTalismanToShow = talismans.size();
        }

        for(int i = 0; i < inventory.getSize(); i++) {
            // Primera fila
            if(i<9) {
                continue;
            }
            // Columna izquierda
            if(i % 9 == 0) {
                continue;
            }
            // Columna derecha
            if(i % 9 == 8) {
                continue;
            }
            // Última fila
            if(i >= 45){
                continue;
            }

            if(talismanIndex < maxTalismanToShow) {
                PlayerTalisman talisman = talismans.get(talismanIndex);
                ItemStack petItem = talisman.getMenuItem();
                inventory.setItem(i, petItem);
                talismanIndex++;
            }
        }
    }

    public static void openMenu(Player player, int page) throws Exception {
        ArrayList<PlayerTalisman> playerTalismans = VoodooTalismans.playerTalismans.get(player);
        if(playerTalismans.size() == 0) {
            throw new Exception(VoodooTalismans.messages.noTalismansFound);
        }

        int necesaryPages = (int) Math.ceil((double) playerTalismans.size() / 28);
        if(page > necesaryPages) {
            throw new Exception(VoodooTalismans.messages.pageNotFound);
        }
        NamespacedKey goToPage = new NamespacedKey(VoodooTalismans.getInstance(), "goToPage");

        Inventory inventory = Bukkit.createInventory(null, 54, VoodooTalismans.config.menuTitle);
        fillBorders(inventory, page);
        fillTalismans(inventory, page, playerTalismans);
        if(necesaryPages > page) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName(
                VoodooTalismans.messages.menuNextPage.replace("%next_page%", String.valueOf(page+1))
            );
            nextPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page+1);
            nextPage.setItemMeta(nextPageMeta);
            inventory.setItem(53, nextPage);
        }

        if(page > 1) {
            ItemStack backPage = new ItemStack(Material.ARROW);
            ItemMeta backPageMeta = backPage.getItemMeta();
            backPageMeta.setDisplayName(
                VoodooTalismans.messages.menuPreviousPage.replace("%previous_page%", String.valueOf(page-1))
            );
            backPageMeta.getPersistentDataContainer().set(goToPage, PersistentDataType.INTEGER, page-1);

            backPage.setItemMeta(backPageMeta);
            inventory.setItem(45, backPage);
        }

        player.openInventory(inventory);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack bordeItem = event.getInventory().getItem(0);
        if(bordeItem == null) {
            return;
        }
        NamespacedKey currentPageKey = new NamespacedKey(VoodooTalismans.getInstance(), "currentPage");

        if(!bordeItem.getItemMeta().getPersistentDataContainer().has(currentPageKey)) {
            return;
        }

        int currentPage = bordeItem.getItemMeta().getPersistentDataContainer().get(
            currentPageKey, PersistentDataType.INTEGER
        );

        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) {
            return;
        }
        NamespacedKey goToPage = new NamespacedKey(VoodooTalismans.getInstance(), "goToPage");
        NamespacedKey talismanKey = new NamespacedKey(VoodooTalismans.getInstance(), "talismanType");

        // Clic en flecha de cambio de página
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(goToPage)) {
            int page = meta.getPersistentDataContainer().get(goToPage, PersistentDataType.INTEGER);
            try{
                openMenu(player, page);
            } catch(Exception e){
                player.sendMessage(VoodooTalismans.messages.pageNotFound);
            }
        }

        // Clic en talismán
        int maxTalismanCount = VoodooTalismans.utilities.getMaxTalismanCount(player);
        if(meta.getPersistentDataContainer().has(talismanKey)) {
            String talismanId = meta.getPersistentDataContainer().get(talismanKey, PersistentDataType.STRING);
            Talisman talisman = VoodooTalismans.config.talismans.get(talismanId);
            PlayerTalisman clickedTalisman = VoodooTalismans.utilities.getPlayerTalisman(player, talisman);
            if(clickedTalisman == null) {
                event.setCancelled(true);
                return;
            }

            // Desactivar un talismán activo
            if(clickedTalisman.isActive()) {
                clickedTalisman.deactivate();
                try{
                    openMenu(player, currentPage);
                } catch(Exception e){
                    player.sendMessage(VoodooTalismans.messages.pageNotFound);
                }
                event.setCancelled(true);
                return;
            }

            // Activar un talismán desactivado
            if(VoodooTalismans.utilities.getActiveTalismansCount(player) < maxTalismanCount) {
                clickedTalisman.activate();
                try{
                    openMenu(player, currentPage);
                } catch(Exception e){
                    player.sendMessage(VoodooTalismans.messages.pageNotFound);
                }
                event.setCancelled(true);
                return;
            } else {
                event.setCancelled(true);
                player.sendMessage(VoodooTalismans.messages.cannotActivateMore.replace("%amount%",
                        String.valueOf(maxTalismanCount)));
            }
        }
        event.setCancelled(true);
    }
}
