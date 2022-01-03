package pl.pijok.playerlives;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.pijok.playerlives.essentials.ConfigUtils;
import pl.pijok.playerlives.essentials.Debug;
import pl.pijok.playerlives.essentials.Utils;
import pl.pijok.playerlives.lifecontroller.LifeType;
import pl.pijok.playerlives.lifecontroller.PunishmentType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Settings {

    //General
    public static int defaultLivesAmount;
    public static int maxLives;
    public static int livesAfterPunishment;
    public static LifeType livesType;
    public static boolean maxLivesPermissionBased;
    public static LinkedHashMap<String, Integer> maxLivesPermissions;

    //Punishment - Type
    public static PunishmentType punishmentType = PunishmentType.EXILE;

    //Punishment - Exile
    public static boolean permanentDeath;
    public static int deathTime;
    public static boolean resurrectionEnabled;
    public static int resurrectionCost;

    //Punishment - Commands
    public static List<String> punishmentCommands;

    //Punishment - Drop items
    public static boolean dropToWorld;

    //Punishment - Money
    public static double moneyToTake;
    public static PunishmentType notEnoughMoneyPunishment;

    //Life steal
    public static boolean lifeSteal;

    //Buying lives
    public static boolean buyableLives;
    public static double liveCost;

    //Gaining lives
    public static boolean eatingEnabled;
    public static HashMap<Material, Integer> eatableMaterials;
    public static HashMap<String, Integer> eatableCustomMaterials;


    public static void load(){
        YamlConfiguration configuration = ConfigUtils.load("config.yml");

        //General
        defaultLivesAmount = configuration.getInt("defaultLivesAmount");
        maxLives = configuration.getInt("maxLives");
        livesAfterPunishment = configuration.getInt("livesAfterPunishment");
        livesType = LifeType.valueOf(configuration.getString("livesType"));
        maxLivesPermissionBased = configuration.getBoolean("maxLivesPermissionBased");

        maxLivesPermissions = new LinkedHashMap<>();
        for(String permission : configuration.getConfigurationSection("maxLivesPermissions").getKeys(false)){
            maxLivesPermissions.put(permission, configuration.getInt("maxLivesPermissions." + permission));
        }

        //Punishment - Type
        String punishment = configuration.getString("deathPunishment");

        if(!Utils.isPunishmentType(punishment)){
            Debug.log("&cWrong death punishment type! Check config.yml deathPunishment!");
            Debug.log("&cDefault value will be set (EXILE)");
        }
        else{
            punishmentType = PunishmentType.valueOf(punishment);
        }

        //Punishment - Exile
        permanentDeath = configuration.getBoolean("permanentDeath");
        deathTime = configuration.getInt("deathTime");
        resurrectionEnabled = configuration.getBoolean("resurrectionEnabled");
        resurrectionCost = configuration.getInt("resurrectionCost");

        //Punishment - Commands
        punishmentCommands = configuration.getStringList("punishmentCommands");

        //Punishment - Drop items
        dropToWorld = configuration.getBoolean("dropToWorld");

        //Punishment - Money
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

        //Life steal
        lifeSteal = configuration.getBoolean("lifeStealEnabled");

        //Buying lives
        buyableLives = configuration.getBoolean("buyableLives");
        liveCost = configuration.getDouble("lifeCost");

        //Gaining lives
        eatingEnabled = configuration.getBoolean("eatingEnabled");

        eatableMaterials = new HashMap<>();
        for(String materialName : configuration.getConfigurationSection("eatableItems").getKeys(false)){
            if(!Utils.isMaterial(materialName)){
                Debug.log("&cWrong material name! (" + materialName + " in config.yml -> eatableItems");
                continue;
            }

            eatableMaterials.put(Material.valueOf(materialName), configuration.getInt("eatableItems." + materialName));
        }

        eatableCustomMaterials = new HashMap<>();
        for(String itemID : configuration.getConfigurationSection("eatableCustomItems").getKeys(false)){
            eatableCustomMaterials.put(itemID, configuration.getInt("eatableCustomItems." + itemID));
        }


    }

}
