package wbe.voodooTalismans.config;

import wbe.voodooTalismans.effects.TalismanEffect;

import java.util.List;

public class Talisman {

    private String id;

    private String name;

    private List<TalismanEffect> effects;

    public Talisman(String id, String name, List<TalismanEffect> effects) {
        this.id = id;
        this.name = name;
        this.effects = effects;
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
}
