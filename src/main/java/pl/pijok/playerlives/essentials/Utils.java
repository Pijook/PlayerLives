package pl.pijok.playerlives.essentials;

import org.bukkit.Material;
import pl.pijok.playerlives.lifecontroller.PunishmentType;

public class Utils {

    public static boolean isMaterial(String a){
        try{
            Material.valueOf(a);
            return true;
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }

    public static boolean isInteger(String a){
        try{
            Integer.valueOf(a);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isPunishmentType(String a){
        try{
            PunishmentType.valueOf(a);
            return true;
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }

}
