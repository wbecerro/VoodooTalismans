package wbe.voodooTalismans.effects;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import com.gamingmesh.jobs.container.CurrencyType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import wbe.voodooTalismans.config.PlayerTalisman;

public class jobMoneyMultiplierEffect extends TalismanEffect {

    public jobMoneyMultiplierEffect(double value, String lore) {
        super(value, lore);
    }

    public void activateEffect(Player player, PlayerTalisman playerTalisman, Event event) {
        if(!(event instanceof JobsPaymentEvent moneyEvent)) {
            return;
        }

        double money = moneyEvent.get(CurrencyType.MONEY);
        money = money * ((value * playerTalisman.getLevel()) + 1);
        moneyEvent.set(CurrencyType.MONEY, money);
    }

    public void deactivateEffect(Player player, PlayerTalisman playerTalisman, Event event) {

    }

    @Override
    public String calculateLore(PlayerTalisman talisman) {
        return lore.replace("%value%", String.format("%.2f", (value * talisman.getLevel()) + 1));
    }
}
