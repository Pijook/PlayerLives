package pl.pijok.playerlives.customEvents;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LifeSetEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private OfflinePlayer player;
    private int currentAmount;

    public LifeSetEvent(OfflinePlayer player, int currentAmount){
        this.player = player;
        this.currentAmount = currentAmount;
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

    public int getCurrentAmount() {
        return currentAmount;
    }
}
