package pl.pijok.playerlives.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class DeathListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();

        lifeController.takeLives(null, player.getName(), 1);

        if(lifeController.getPlayerLives(player.getName()) <= 0){
            String message = Lang.getLang("DEATH_INFO");
            lifeController.kill(player.getName());
            player.kickPlayer(ChatUtils.fixColor(message));
        }
    }

}
