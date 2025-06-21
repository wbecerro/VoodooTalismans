package wbe.voodooTalismans;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.voodooTalismans.commands.CommandListener;
import wbe.voodooTalismans.commands.TabListener;
import wbe.voodooTalismans.config.Config;
import wbe.voodooTalismans.config.Messages;
import wbe.voodooTalismans.config.PlayerTalisman;
import wbe.voodooTalismans.listeners.EventListeners;
import wbe.voodooTalismans.utils.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public final class VoodooTalismans extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private TabListener tabListener;

    private EventListeners eventListeners;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    public static HashMap<Player, ArrayList<PlayerTalisman>> playerTalismans = new HashMap<>();

    public static HashMap<Player, ArrayList<PlayerTalisman>> activeTalismans = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("VoodooTalismans enabled correctly.");
        reloadConfiguration();

        commandListener = new CommandListener();
        getCommand("voodootalismans").setExecutor(commandListener);
        tabListener = new TabListener();
        getCommand("voodootalismans").setTabCompleter(tabListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        for(Player player : playerTalismans.keySet()) {
            utilities.savePlayerData(player);
        }
        getLogger().info("Voodoo Talismans disabled correctly.");
    }

    public static VoodooTalismans getInstance() {
        return getPlugin(VoodooTalismans.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        new File(getDataFolder(), "saves").mkdir();
        for(Player player : playerTalismans.keySet()) {
            utilities.savePlayerData(player);
        }

        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
        utilities = new Utilities();
        for (Player player : Bukkit.getOnlinePlayers()) {
            utilities.loadPlayerData(player);
        }
    }
}
