package pl.pijok.playerlives.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pijok.playerlives.Lang;

public class ChatUtils {

    private static String prefix;

    public static void setPrefix(String a){
        prefix = a;
    }

    /**
     * Changes & to minecraft colors
     * @param message Message to fix
     * @return Returns ready message
     */
    public static String fixColor(String message){
        message = message.replace("&","§");
        return message;
    }

    /**
     * Sends colored message to online Players
     * @param message Message to send
     */
    public static void broadcast(String message){
        for(Player player : Bukkit.getOnlinePlayers()){
            ChatUtils.sendMessage(player, message);
        }
    }

    /**
     * Sends colored message to Player
     * @param player Player that receives message
     * @param message Message to send
     */
    public static void sendMessage(Player player, String message){
        player.sendMessage(fixColor(Lang.getLang("PREFIX") + message));
    }

    /**
     * Sends colored message to CommandSender
     * @param player CommandSender that receives message
     * @param message Message to send
     */
    public static void sendMessage(CommandSender player, String message){
        player.sendMessage(fixColor(Lang.getLang("PREFIX") + message));
    }


}
