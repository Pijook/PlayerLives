package pl.pijok.playerlives.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.PlayerLives;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.essentials.Utils;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class LivesCommand implements CommandExecutor {

    private final LifeController lifeController = Controllers.getLifeController();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1){
            //Reload
            if(args[0].equalsIgnoreCase("reload")){
                if(!checkPermission(sender, "playerlives.reload")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                ChatUtils.sendMessage(sender, "&eReloading!");
                if(PlayerLives.getInstance().loadStuff(true)){
                    ChatUtils.sendMessage(sender, "&aReloaded!");
                }
                else{
                    ChatUtils.sendMessage(sender, "&cSomething went wrong while reloading! Check console please");
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("check")){
                if(!checkPermission(sender, "playerlives.check.self")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                int lives = lifeController.getPlayerLives(((Player) sender).getName());
                ChatUtils.sendMessage(sender, Lang.getLang("CURRENT_LIVES").replace("%amount%", "" + lives));
                return true;
            }
        }
        else if(args.length == 2){
            if(args[0].equalsIgnoreCase("check")){
                if(!checkPermission(sender, "playerlives.check.other")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                int lives = lifeController.getPlayerLives(args[1]);
                if(lives != -999){
                    ChatUtils.sendMessage(sender, Lang.getLang("CURRENT_LIVES_OTHER").replace("%name%", args[1]).replace("%amount%", "" + lives));
                }
                else{
                    ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
                }
                return true;
            }
        }
        else if(args.length == 3){
            //Add lives
            if(args[0].equalsIgnoreCase("add")){
                if(!checkPermission(sender, "playerlives.add")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                String nickname = args[1];
                if(!Utils.isInteger(args[2])){
                    ChatUtils.sendMessage(sender, "&cAmount must be number!");
                    return true;
                }

                lifeController.addLives(sender, nickname, Integer.parseInt(args[2]));
                return true;
            }
            //Take lives
            else if(args[0].equalsIgnoreCase("take")){
                if(!checkPermission(sender, "playerlives.take")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                String nickname = args[1];
                if(!Utils.isInteger(args[2])){
                    ChatUtils.sendMessage(sender, "&cAmount must be number!");
                    return true;
                }

                lifeController.takeLives(sender, nickname, Integer.parseInt(args[2]));
                return true;
            }
            //Set lives
            else if(args[0].equalsIgnoreCase("set")){
                if(!checkPermission(sender, "playerlives.set")){
                    ChatUtils.sendMessage(sender, Lang.getLang("PERMISSION_DENIED"));
                    return true;
                }

                String nickname = args[1];
                if(!Utils.isInteger(args[2])){
                    ChatUtils.sendMessage(sender, "&cValue must be number!");
                    return true;
                }

                lifeController.setLives(sender, nickname, Integer.parseInt(args[2]));
                return true;
            }
        }

        if(checkPermission(sender, "playerlives.check.self")){
            ChatUtils.sendMessage(sender, "&7/" + label + " check");
        }
        if(checkPermission(sender, "playerlives.check.other")){
            ChatUtils.sendMessage(sender, "&7/" + label + " check <nickname>"); //Done
        }
        if(checkPermission(sender, "playerlives.add")){
            ChatUtils.sendMessage(sender, "&7/" + label + " add <nickname> <amount>"); //Done
        }
        if(checkPermission(sender, "playerlives.take")){
            ChatUtils.sendMessage(sender, "&7/" + label + " take <nickname> <amount>"); //Done
        }
        if(checkPermission(sender, "playerlives.set")){
            ChatUtils.sendMessage(sender, "&7/" + label + " set <nickname> <value>"); //Done
        }
        if(checkPermission(sender, "playerlives.reload")){
            ChatUtils.sendMessage(sender, "&7/" + label + " reload"); //Done
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
