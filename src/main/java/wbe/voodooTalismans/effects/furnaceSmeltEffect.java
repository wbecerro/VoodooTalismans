package wbe.voodooTalismans.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.HashMap;
import java.util.UUID;

public class furnaceSmeltEffect extends TalismanEffect {

    public static HashMap<Player, Double> effectPlayers = new HashMap<>();
    public static HashMap<Location, UUID> playerFurnaces = new HashMap<>();

    public furnaceSmeltEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        effectPlayers.put(player, value * playerTalisman.getLevel());
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        effectPlayers.remove(player);
    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", value * talisman.getLevel() * 100));
    }
}
