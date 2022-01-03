package pl.pijok.playerlives.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.PlayerLives;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.lifecontroller.LifeController;
import pl.pijok.playerlives.lifecontroller.LifeType;

public class JoinListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();
    private final PlayerLives plugin;

    public JoinListener(PlayerLives plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        if(lifeController.joinedBefore(player.getName())){
            if(lifeController.isStillDead((player.getName()))){
                PlayerLoginEvent.Result result = PlayerLoginEvent.Result.KICK_BANNED;
                event.disallow(result, ChatUtils.fixColor(Lang.getLang("DEATH_INFO")));
            }

            if(Settings.livesType.equals(LifeType.HEARTS)){
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.setMaxHealth(lifeController.getPlayerLives(player.getName()) * 2);
                    }
                }, 5L);

            }
        }
        else{
            lifeController.createNewAccount(player.getName());
        }

    }

}
