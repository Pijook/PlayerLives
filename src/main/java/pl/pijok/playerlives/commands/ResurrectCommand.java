package pl.pijok.playerlives.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class ResurrectCommand implements CommandExecutor {

    private final LifeController lifeController = Controllers.getLifeController();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1){
            if(!checkPermission(sender, "playerlives.resurrect")){
                ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                return true;
            }

            if(!Settings.resurrectionEnabled){
                ChatUtils.sendMessage(sender, Lang.getLang("RESURRECT_DISABLED"));
                return true;
            }

            if(!(sender instanceof Player)){
                ChatUtils.sendMessage(sender, "&cCommand only for players!");
                return true;
            }

            Player player = (Player) sender;

            String nickname = args[0];

            if(!lifeController.joinedBefore(nickname)){
                ChatUtils.sendMessage(sender,Lang.getLang("PLAYER_NOT_FOUND"));
                return true;
            }

            if(!lifeController.isStillDead(nickname)){
                ChatUtils.sendMessage(sender, Lang.getLang("RESURRECT_ALREADY_ALIVE"));
                return true;
            }

            if(lifeController.getPlayerLives(player.getName()) <= Settings.resurrectionCost){
                ChatUtils.sendMessage(sender, Lang.getLang("RESURRECT_NOT_ENOUGH_LIVES"));
                return true;
            }

            lifeController.resurrectPlayer(player.getName(), nickname);
            ChatUtils.sendMessage(sender,Lang.getLang("RESURRECT_SUCCESSFUL"));
            return true;
        }
        else if(args.length == 2){
            if(args[0].equalsIgnoreCase("force")){
                if(!checkPermission(sender, "playerlives.forceresurrect")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                if(!Settings.resurrectionEnabled){
                    ChatUtils.sendMessage(sender, Lang.getLang("RESURRECT_DISABLED"));
                    return true;
                }

                String nickname = args[1];

                if(!lifeController.joinedBefore(nickname)){
                    ChatUtils.sendMessage(sender,Lang.getLang("PLAYER_NOT_FOUND"));
                    return true;
                }

                if(!lifeController.isStillDead(nickname)){
                    ChatUtils.sendMessage(sender, Lang.getLang("RESURRECT_ALREADY_ALIVE"));
                    return true;
                }

                lifeController.resurrectPlayer(null, nickname);
                ChatUtils.sendMessage(sender,Lang.getLang("RESURRECT_SUCCESSFUL").replace("%name%", nickname));
                return true;
            }
        }

        if(checkPermission(sender, "playerlives.resurrect")){
            ChatUtils.sendMessage(sender,"&7/" + label + " <nickname>");
        }
        if(checkPermission(sender, "playerlives.forceresurrect")){
            ChatUtils.sendMessage(sender,"&7/" + label + " force <nickname>");
        }
        if(!checkPermission(sender, "playerlives.resurrect") && !checkPermission(sender, "playerlives.forceresurrect")){
            ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
        }
        return true;
    }

    private boolean checkPermission(CommandSender sender, String permission){
        if(sender instanceof Player){
            Player player = (Player) sender;
            return player.hasPermission(permission);
        }
        return true;
    }
}
