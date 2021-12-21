package pl.pijok.playerlives;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.pijok.playerlives.essentials.ConfigUtils;
import pl.pijok.playerlives.essentials.Debug;
import pl.pijok.playerlives.essentials.Utils;

import java.util.HashMap;

public class Settings {

    public static int defaultLivesAmount;
    public static int maxLives;
    public static boolean eatingEnabled;
    public static boolean permanentDeath;
    public static int deathTime;
    public static HashMap<Material, Integer> eatableMaterials;
    public static boolean resurrectionEnabled;
    public static int resurrectionCost;
    public static int livesAfterResurrection;

    public static void load(){
        YamlConfiguration configuration = ConfigUtils.load("config.yml");

        eatableMaterials = new HashMap<>();

        defaultLivesAmount = configuration.getInt("defaultLivesAmount");
        maxLives = configuration.getInt("maxLives");
        eatingEnabled = configuration.getBoolean("eatingEnabled");
        permanentDeath = configuration.getBoolean("permanentDeath");
        deathTime = configuration.getInt("deathTime");
        resurrectionEnabled = configuration.getBoolean("resurrectionEnabled");
        resurrectionCost = configuration.getInt("resurrectionCost");
        livesAfterResurrection = configuration.getInt("livesAfterResurrection");

        for(String materialName : configuration.getConfigurationSection("eatableItems").getKeys(false)){
            if(!Utils.isMaterial(materialName)){
                Debug.log("&cWrong material name! (" + materialName + " in config.yml -> eatableItems");
                continue;
            }

            eatableMaterials.put(Material.valueOf(materialName), configuration.getInt("eatableItems." + materialName));
        }
    }

}
