package wbe.voodooTalismans.config;

import org.bukkit.Material;
import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.List;

public class Talisman {

    private String id;

    private String name;

    private List<TalismanEffect> effects;

    private Material material;

    private List<String> lore;

    private boolean glow;

    public Talisman(String id, String name, List<TalismanEffect> effects, Material material, List<String> lore, boolean glow) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.material = material;
        this.lore = lore;
        this.glow = glow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TalismanEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<TalismanEffect> effects) {
        this.effects = effects;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }
}
