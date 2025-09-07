package wbe.voodooTalismans.effects;

import io.lumine.mythic.bukkit.events.MythicMobLootDropEvent;
import io.lumine.mythic.core.drops.LootBag;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.tartarusRiches.TartarusRiches;
import wbe.tartarusRiches.utils.Utilities;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.Random;

public class multiplyMythicDrops extends TalismanEffect {

    public multiplyMythicDrops(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(!(event instanceof MythicMobLootDropEvent)) {
            return;
        }

        Random random = new Random();
        if(random.nextDouble(100) <= value * playerTalisman.getLevel()) {
            LootBag lootBag = ((MythicMobLootDropEvent) event).getMobType().getDropTable().generate();
            lootBag.drop(((MythicMobLootDropEvent) event).getMob().getLocation());
        }
    }


    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {

    }
}
