package wbe.voodooTalismans.effects;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.List;

public class jobXpMultiplierEffect extends TalismanEffect {

    private List<String> jobs;

    public jobXpMultiplierEffect(double value, String lore, List<String> jobs) {
        super(value, lore);
        this.jobs = jobs;
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(!(event instanceof JobsExpGainEvent expGainEvent)) {
            return;
        }

        for(String job : jobs) {
            if(!(expGainEvent.getJob().getName().equalsIgnoreCase(job.toUpperCase()))) {
                continue;
            }

            double xp = expGainEvent.getExp();
            xp = xp * ((value * playerTalisman.getLevel()) + 1);
            expGainEvent.setExp(xp);
            return;
        }
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {

    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", (value * talisman.getLevel()) + 1));
    }
}
