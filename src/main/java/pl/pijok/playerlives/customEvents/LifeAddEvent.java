package pl.pijok.playerlives.customEvents;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LifeAddEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private OfflinePlayer player;
    private int addedAmount;
    private int newAmount;

    public LifeAddEvent(OfflinePlayer player, int addedAmount, int newAmount) {
        this.player = player;
        this.addedAmount = addedAmount;
        this.newAmount = newAmount;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public int getAddedAmount() {
        return addedAmount;
    }

    public int getNewAmount() {
        return newAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

}
