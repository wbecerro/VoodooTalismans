package wbe.voodooTalismans.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wbe.voodooTalismans.VoodooTalismans;

public class Scheduler {

    private static VoodooTalismans plugin;

    public static void startSchedulers() {
        plugin = VoodooTalismans.getInstance();
        startDataSaveScheduler();
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
}
