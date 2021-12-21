package pl.pijok.playerlives.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Debug {

    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private static String prefix = "[]";

    public static void setPrefix(String a){
        prefix = a;
    }

    /**
     * Sends string to console
     * @param a Message to send
     */
    public static void log(String a) {
        a = prefix + a;
        console.sendMessage(a.replace("&", "§"));
    }

    /**
     * Sends red error to console
     * @param a Error to send
     */
    public static void sendError(String a){
        Debug.log("&c============");
        Debug.log("&c" + a);
        Debug.log("&c============");
    }

    /**
     * Sends object to console
     * @param a Object to send
     */
    public static void log(Object a) {
        a = prefix + a;
        console.sendMessage(String.valueOf(a).replace("&", "§"));
    }


}
