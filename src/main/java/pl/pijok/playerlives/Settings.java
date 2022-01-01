package pl.pijok.playerlives;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.pijok.playerlives.essentials.ConfigUtils;
import pl.pijok.playerlives.essentials.Debug;
import pl.pijok.playerlives.essentials.Utils;
import pl.pijok.playerlives.lifecontroller.PunishmentType;

import java.util.HashMap;
import java.util.List;

public class Settings {

    public static PunishmentType punishmentType = PunishmentType.EXILE;

    //Lives settings
    public static int defaultLivesAmount;
    public static int maxLives;
    public static boolean eatingEnabled;
    public static HashMap<Material, Integer> eatableMaterials;
    public static HashMap<String, Integer> eatableCustomMaterials;
    public static boolean resurrectionEnabled;
    public static int resurrectionCost;
    public static int livesAfterPunishment;
    public static boolean buyableLives;
    public static double liveCost;

    //Exile settings
    public static boolean permanentDeath;
    public static int deathTime;

    //Command settings
    public static List<String> punishmentCommands;

    //Drop items settings
    public static boolean dropToWorld;

    //Money settings
    public static double moneyToTake;
    public static PunishmentType notEnoughMoneyPunishment;


    public static void load(){
        YamlConfiguration configuration = ConfigUtils.load("config.yml");

        eatableMaterials = new HashMap<>();
        eatableCustomMaterials = new HashMap<>();

        String punishment = configuration.getString("deathPunishment");

        if(!Utils.isPunishmentType(punishment)){
            Debug.log("&cWrong death punishment type! Check config.yml deathPunishment!");
            Debug.log("&cDefault value will be set (EXILE)");
        }
        else{
            punishmentType = PunishmentType.valueOf(punishment);
        }

        //Loading exile
        permanentDeath = configuration.getBoolean("permanentDeath");
        deathTime = configuration.getInt("deathTime");

        //Loading command
        punishmentCommands = configuration.getStringList("punishmentCommands");

        //Loading drop items
        dropToWorld = configuration.getBoolean("dropToWorld");

        //Loading money
        moneyToTake = configuration.getDouble("moneyToTake");
        String notEnoughMoneyPunishmentString = configuration.getString("notEnoughMoneyPunishment");

        if(!Utils.isPunishmentType(notEnoughMoneyPunishmentString)){
            Debug.log("&cWrong death punishment type! Check config.yml notEnoughMoneyPunishment!");
            Debug.log("&cDefault value will be set (EXILE)");
            notEnoughMoneyPunishment = PunishmentType.EXILE;
        }
        else{
            notEnoughMoneyPunishment = PunishmentType.valueOf(notEnoughMoneyPunishmentString);
        }

        //Loading lives settings
        defaultLivesAmount = configuration.getInt("defaultLivesAmount");
        maxLives = configuration.getInt("maxLives");
        eatingEnabled = configuration.getBoolean("eatingEnabled");
        resurrectionEnabled = configuration.getBoolean("resurrectionEnabled");
        resurrectionCost = configuration.getInt("resurrectionCost");
        livesAfterPunishment = configuration.getInt("livesAfterPunishment");
        buyableLives = configuration.getBoolean("buyableLives");
        liveCost = configuration.getDouble("lifeCost");

        for(String materialName : configuration.getConfigurationSection("eatableItems").getKeys(false)){
            if(!Utils.isMaterial(materialName)){
                Debug.log("&cWrong material name! (" + materialName + " in config.yml -> eatableItems");
                continue;
            }

            eatableMaterials.put(Material.valueOf(materialName), configuration.getInt("eatableItems." + materialName));
        }

        for(String itemID : configuration.getConfigurationSection("eatableCustomItems").getKeys(false)){
            eatableCustomMaterials.put(itemID, configuration.getInt("eatableCustomItems." + itemID));
        }
    }

}
