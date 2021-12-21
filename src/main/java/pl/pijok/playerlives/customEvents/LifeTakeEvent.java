package pl.pijok.playerlives.customEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LifeTakeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private String nickname;
    private int amount;

    public LifeTakeEvent(String nickname, int amount){
        this.nickname = nickname;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
