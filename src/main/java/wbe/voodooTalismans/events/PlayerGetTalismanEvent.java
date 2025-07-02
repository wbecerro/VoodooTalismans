package wbe.voodooTalismans.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.voodooTalismans.config.PlayerTalisman;

public class PlayerGetTalismanEvent extends Event implements Cancellable {

    private Player player;

    private PlayerTalisman playerTalisman;

    private int currentTalismanCount;

    private int newTalismanCount;

    private boolean isCancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public PlayerGetTalismanEvent(Player player, PlayerTalisman playerTalisman, int currentTalismanCount, int newTalismanCount) {
        this.player = player;
        this.playerTalisman = playerTalisman;
        this.currentTalismanCount = currentTalismanCount;
        this.newTalismanCount = newTalismanCount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerTalisman getPlayerTalisman() {
        return playerTalisman;
    }

    public void setPlayerTalisman(PlayerTalisman playerTalisman) {
        this.playerTalisman = playerTalisman;
    }

    public int getCurrentTalismanCount() {
        return currentTalismanCount;
    }

    public void setCurrentTalismanCount(int currentTalismanCount) {
        this.currentTalismanCount = currentTalismanCount;
    }

    public int getNewTalismanCount() {
        return newTalismanCount;
    }

    public void setNewTalismanCount(int newTalismanCount) {
        this.newTalismanCount = newTalismanCount;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
