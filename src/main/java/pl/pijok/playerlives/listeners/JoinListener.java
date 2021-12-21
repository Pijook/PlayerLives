package pl.pijok.playerlives.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class JoinListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        if(lifeController.joinedBefore(player.getName())){
            if(lifeController.isStillDead((player.getName()))){
                PlayerLoginEvent.Result result = PlayerLoginEvent.Result.KICK_BANNED;
                event.disallow(result, ChatUtils.fixColor(Lang.getLang("DEATH_INFO")));
            }
        }
        else{
            lifeController.createNewAccount(player.getName());
        }

    }

}
