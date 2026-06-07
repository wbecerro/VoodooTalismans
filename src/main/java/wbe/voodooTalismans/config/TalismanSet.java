package wbe.voodooTalismans.config;

import org.bukkit.entity.Player;
import wbe.voodooTalismans.VoodooTalismans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TalismanSet {

    private String id;

    private Set<PlayerTalisman> talismans;

    public TalismanSet(String id, Set<PlayerTalisman> talismans) {
        this.id = id;
        this.talismans = talismans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<PlayerTalisman> getTalismans() {
        return talismans;
    }

    public void setTalismans(Set<PlayerTalisman> talismans) {
        this.talismans = talismans;
    }

    public void activate(Player player) {
        VoodooTalismans.utilities.deactivateAllTalismans(player);

        for(PlayerTalisman talisman : talismans) {
            talisman.activate();
        }
    }

    public List<String> getTalismanIds() {
        List<String> talismanIds = new ArrayList<>();
        for(PlayerTalisman playerTalisman : talismans) {
            talismanIds.add(playerTalisman.getType().getId());
        }

        return talismanIds;
    }
}
