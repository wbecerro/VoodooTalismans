package wbe.voodooTalismans.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.voodooTalismans.VoodooTalismans;

public class EventListeners {

    public void initializeListeners() {
        VoodooTalismans plugin = VoodooTalismans.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();
    }
}
