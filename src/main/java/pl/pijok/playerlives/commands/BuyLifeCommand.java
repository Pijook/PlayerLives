package pl.pijok.playerlives.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.PlayerLives;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.essentials.Debug;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class BuyLifeCommand implements CommandExecutor {

    private final LifeController lifeController = Controllers.getLifeController();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)){
            Debug.log("&cCommand only for players!");
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0){

            if(!player.hasPermission("playerlives.buylife")){
                ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                return true;
            }

            if(!Settings.buyableLives || PlayerLives.getEconomy() == null){
                ChatUtils.sendMessage(player, Lang.getLang("BUYING_LIVES_DISABLED"));
                return true;
            }

            if(lifeController.getPlayerLives(player.getName()) >= Settings.maxLives){
                ChatUtils.sendMessage(player, Lang.getLang("MAX_LIVES"));
                return true;
            }

            if(PlayerLives.getEconomy().getBalance(player) < Settings.liveCost){
                ChatUtils.sendMessage(player, Lang.getLang("NOT_ENOUGH_MONEY"));
                return true;
            }

            ChatUtils.sendMessage(player, Lang.getLang("LIFE_BOUGHT"));
            PlayerLives.getEconomy().withdrawPlayer(player, Settings.liveCost);
            lifeController.addLives(sender, player.getName(), 1);
            return true;
        }

        ChatUtils.sendMessage(player, "&7/" + label);
        return true;
    }
}
