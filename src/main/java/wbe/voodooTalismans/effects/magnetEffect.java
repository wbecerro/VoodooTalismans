package wbe.voodooTalismans.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.HashMap;

public class magnetEffect extends TalismanEffect {

    public static HashMap<Player, Double> magnetPlayers = new HashMap<>();

    public magnetEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        magnetPlayers.put(player, value * playerTalisman.getLevel());
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(event != null) {
            return;
        }

        magnetPlayers.remove(player);
    }
}
