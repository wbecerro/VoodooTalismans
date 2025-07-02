package wbe.voodooTalismans.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.voodooTalismans.VoodooTalismans;

public class EventListeners {

    public void initializeListeners() {
        VoodooTalismans plugin = VoodooTalismans.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListeners(), plugin);
        pluginManager.registerEvents(new PlayerQuitListeners(), plugin);
        pluginManager.registerEvents(new MenuListener(), plugin);
        pluginManager.registerEvents(new PlayerGetTalismanListeners(), plugin);
    }
}
