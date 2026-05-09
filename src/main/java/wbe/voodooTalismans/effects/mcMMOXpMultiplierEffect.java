package wbe.voodooTalismans.effects;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

import java.util.List;

public class mcMMOXpMultiplierEffect extends TalismanEffect {

    private List<String> skills;

    public mcMMOXpMultiplierEffect(double value, String lore, List<String> skills) {
        super(value, lore);
        this.skills = skills;
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(!(event instanceof McMMOPlayerXpGainEvent mcMMOEvent)) {
            return;
        }

        for(String skill : skills) {
            if(!(mcMMOEvent.getSkill().equals(PrimarySkillType.valueOf(skill.toUpperCase())))) {
                continue;
            }

            float xp = mcMMOEvent.getRawXpGained();
            xp = (float) (xp * ((value * playerTalisman.getLevel()) + 1));
            mcMMOEvent.setRawXpGained(xp);
        }
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {

    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", (value * talisman.getLevel()) + 1));
    }
}
