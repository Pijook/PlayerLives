package pl.pijok.playerlives.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.essentials.Debug;
import pl.pijok.playerlives.lifecontroller.LifeController;
import pl.pijok.playerlives.lifecontroller.LifeType;
import pl.pijok.playerlives.lifecontroller.PunishmentType;

public class DeathListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();

        if(player.hasPermission("playerlives.bypass.death")){
            return;
        }

        if(Settings.livesType.equals(LifeType.HEARTS)){
            if(player.getMaxHealth() - 2 <= 0){
                lifeController.setLives(null, player.getName(), Settings.livesAfterPunishment);
                lifeController.punishPlayer(player, Settings.punishmentType);
            }
            else{
                lifeController.takeLives(null, player.getName(), 1);
            }
        }
        else{
            lifeController.takeLives(null, player.getName(), 1);
        }

        if(lifeController.getPlayerLives(player.getName()) <= 0){
            lifeController.punishPlayer(player, Settings.punishmentType);
        }
        else{
            if(Settings.punishmentType.equals(PunishmentType.DROP_ITEMS)){
                event.setKeepInventory(true);
            }
        }

        if(Settings.lifeSteal){
            if(event.getEntity().getKiller() != null){
                Debug.log("Life steal enabled!");
                Player killer = event.getEntity().getKiller();

                if(lifeController.canGetAnotherLive(killer)){
                    lifeController.addLives(null, killer.getName(), 1);
                }
                else{
                    ChatUtils.sendMessage(killer, Lang.getLang("MAX_LIVES"));
                }

            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
    }

}
