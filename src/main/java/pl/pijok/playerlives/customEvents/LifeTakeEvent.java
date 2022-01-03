package pl.pijok.playerlives.customEvents;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LifeTakeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private OfflinePlayer player;
    private int takenAmount;
    private int newAmount;

    public LifeTakeEvent(OfflinePlayer player, int takenAmount, int newAmount){
        this.player = player;
        this.takenAmount = takenAmount;
        this.newAmount = newAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public int getTakenAmount() {
        return takenAmount;
    }

    public int getNewAmount() {
        return newAmount;
    }
}
