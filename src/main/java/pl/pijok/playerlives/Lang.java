package pl.pijok.playerlives;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.pijok.playerlives.essentials.ConfigUtils;

import java.util.HashMap;

public class Lang {

    private static HashMap<String, String> lang;

    public static void load(){
        lang = new HashMap<>();

        YamlConfiguration configuration = ConfigUtils.load("lang.yml");

        for(String key : configuration.getConfigurationSection("lang").getKeys(false)){
            lang.put(key, configuration.getString("lang." + key));
        }
    }

    public static String getLang(String a){
        return lang.getOrDefault(a, "NULL");
    }

}
