package wbe.voodooTalismans.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import wbe.voodooTalismans.VoodooTalismans;

public class PlayerDropItemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void addTagToDroppedItems(PlayerDropItemEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Item item = event.getItemDrop();

        NamespacedKey dropKey = new NamespacedKey(VoodooTalismans.getInstance(), "droppedItem");
        item.getPersistentDataContainer().set(dropKey, PersistentDataType.BOOLEAN, true);

        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(!item.isValid() || item.isDead()) {
                    return;
                }

                item.getPersistentDataContainer().remove(dropKey);
            }
        }.runTaskLater(VoodooTalismans.getInstance(), 100L);
    }
}
