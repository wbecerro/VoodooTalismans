package wbe.voodooTalismans.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import wbe.voodooTalismans.VoodooTalismans;
import wbe.voodooTalismans.effects.magnetEffect;

public class Scheduler {

    private static VoodooTalismans plugin;

    public static void startSchedulers() {
        plugin = VoodooTalismans.getInstance();
        startDataSaveScheduler();
        startMagnetScheduler();
    }

    private static void startDataSaveScheduler() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : VoodooTalismans.playerTalismans.keySet()) {
                    VoodooTalismans.utilities.savePlayerData(player);
                }
            }
        }, 10L, 60 * 5 * 20L);
    }

    private static void startMagnetScheduler() {
        NamespacedKey dropKey = new NamespacedKey(VoodooTalismans.getInstance(), "droppedItem");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : magnetEffect.magnetPlayers.keySet()) {
                    double distance = magnetEffect.magnetPlayers.get(player);
                    for(Entity entity : player.getNearbyEntities(distance, distance, distance)) {
                        if(!(entity instanceof Item item)) {
                            continue;
                        }

                        if(item.getPersistentDataContainer().has(dropKey)) {
                            continue;
                        }

                        ItemStack itemStack = item.getItemStack();
                        boolean canEnterInventory = VoodooTalismans.utilities.canAddItemToInventory(player, itemStack);
                        if(!canEnterInventory) {
                            continue;
                        }

                        Vector velocity = player.getLocation().toVector()
                                .subtract(item.getLocation().toVector());

                        double distanceSquared = velocity.lengthSquared();
                        if(distanceSquared < 0.0001) {
                            continue;
                        }

                        velocity = velocity.normalize().multiply(0.3);

                        item.setVelocity(velocity);
                    }
                }
            }
        }, 1L, 1L);
    }
}
