package wbe.voodooTalismans.config;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import wbe.voodooTalismans.effects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public String menuTitle;
    public Material menuBorder;
    public String menuLevel;
    public String menuEffects;
    public String menuActive;
    public String menuUnactive;
    public String menuSelect;
    public String menuDeselect;

    public HashMap<String, Talisman> talismans = new HashMap<>();

    public Config(FileConfiguration config) {
        this.config = config;

        menuTitle = config.getString("Menu.title").replace("&", "§");
        menuBorder = Material.valueOf(config.getString("Menu.borderMaterial"));
        menuLevel = config.getString("Menu.level").replace("&", "§");
        menuEffects = config.getString("Menu.effects").replace("&", "§");
        menuActive = config.getString("Menu.active").replace("&", "§");
        menuUnactive = config.getString("Menu.unactive").replace("&", "§");
        menuSelect = config.getString("Menu.select").replace("&", "§");
        menuDeselect = config.getString("Menu.deselect").replace("&", "§");

        loadTalismans();
    }

    private void loadTalismans() {
        Set<String> configTalismans = config.getConfigurationSection("Talismans").getKeys(false);
        for(String configTalisman : configTalismans) {
            String id = configTalisman;
            String name = config.getString("Talismans." + configTalisman + ".name").replace("&", "§");
            List<TalismanEffect> effects = loadTalismanEffects(configTalisman);
            Material material = Material.valueOf(config.getString("Talismans." + configTalisman + ".material"));
            List<String> lore = config.getStringList("Talismans." + configTalisman + ".lore");
            boolean glow = config.getBoolean("Talismans." + configTalisman + ".glow");
            int maxLevel = config.getInt("Talismans." + configTalisman + ".maxLevel");
            talismans.put(id, new Talisman(id, name, effects, material, lore, glow, maxLevel));
        }
    }

    private List<TalismanEffect> loadTalismanEffects(String talisman) {
        List<TalismanEffect> effects = new ArrayList<>();
        Set<String> configEffects = config.getConfigurationSection("Talismans." + talisman + ".effects").getKeys(false);
        for(String configEffect : configEffects) {
            String lore = config.getString("Talismans." + talisman + ".effects." + configEffect + ".lore").replace("&", "§");
            double value = config.getDouble("Talismans." + talisman + ".effects." + configEffect + ".value");
            if(configEffect.toLowerCase().startsWith("addmodifierscale")) {
                effects.add(new addModifierScaleEffect(value, lore, Attribute.valueOf(config.getString("Talismans." + talisman + ".effects." + configEffect + ".attribute")), talisman));
            } else if(configEffect.toLowerCase().startsWith("addmodifiervalue")) {
                effects.add(new addModifierValueEffect(value, lore, Attribute.valueOf(config.getString("Talismans." + talisman + ".effects." + configEffect + ".attribute")), talisman));
            } else if(configEffect.toLowerCase().startsWith("additemchance")) {
                effects.add(new addItemChanceEffect(value, lore, addItemChanceEffect.ItemChanceType.valueOf(config.getString("Talismans." + talisman + ".effects." + configEffect + ".type").toUpperCase())));
            } else if(configEffect.toLowerCase().startsWith("addcreaturechance")) {
                effects.add(new addCreatureChanceEffect(value, lore, addCreatureChanceEffect.CreatureChanceType.valueOf(config.getString("Talismans." + talisman + ".effects." + configEffect + ".type").toUpperCase())));
            } else if(configEffect.toLowerCase().startsWith("addgemchance")) {
                effects.add(new addGemChanceEffect(value, lore));
            } else if(configEffect.toLowerCase().startsWith("adddoublechance")) {
                effects.add(new addDoubleChanceEffect(value, lore, addDoubleChanceEffect.DoubleChanceType.valueOf(config.getString("Talismans." + talisman + ".effects." + configEffect + ".type").toUpperCase())));
            }
        }

        return effects;
    }
}
