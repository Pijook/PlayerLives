package pl.pijok.playerlives.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class DeathListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();

        if(player.hasPermission("playerlives.bypass.death")){
            return;
        }

        lifeController.takeLives(null, player.getName(), 1);

        if(lifeController.getPlayerLives(player.getName()) <= 0){
            lifeController.punishPlayer(player, Settings.punishmentType);
        }
    }

}
