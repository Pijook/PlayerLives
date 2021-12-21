package pl.pijok.playerlives.essentials;

import org.bukkit.Material;

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

}
