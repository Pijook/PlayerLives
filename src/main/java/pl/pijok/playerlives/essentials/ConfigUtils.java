package pl.pijok.playerlives.essentials;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private static Plugin plugin;

    /**
     * Loads specified config from resources or plugin folder
     * @param configName Name of a config
     * @return Returns loaded config file or error if it doesn't exist
     */
    public static YamlConfiguration load(String configName){
        YamlConfiguration config;
        File file = new File(plugin.getDataFolder() + File.separator + configName);
        if (!file.exists())
            plugin.saveResource(configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    public static YamlConfiguration load(String configName, String folder){
        YamlConfiguration config;
        File file = new File(plugin.getDataFolder() + File.separator + folder + File.separator + configName);
        if (!file.exists())
            plugin.saveResource(folder + File.separator + configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    /**
     * Saves given config file
     * @param c Yaml file
     * @param file File name
     */
    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(plugin.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(YamlConfiguration c, String folder, String file){
        try {
            c.save(new File(plugin.getDataFolder() + File.separator + folder, file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets location from config
     * @param configuration Configuration file
     * @param path Path to location
     * @return Returns ready location
     */
    public static Location getLocationFromConfig(YamlConfiguration configuration, String path){
        double locationX = configuration.getDouble(path + ".x");
        double locationY = configuration.getDouble(path + ".y");
        double locationZ = configuration.getDouble(path + ".z");
        String worldName = configuration.getString(path + ".world");

        Location location = new Location(Bukkit.getWorld(worldName), locationX, locationY, locationZ);

        return location;
    }

    /**
     * Saves location to config
     * @param configuration Configuration file
     * @param path Path to location where to save
     * @param location Location to save
     */
    public static void saveLocationToConfig(YamlConfiguration configuration, String path, Location location){
        configuration.set(path + ".x", location.getX());
        configuration.set(path + ".y", location.getY());
        configuration.set(path + ".z", location.getZ());
        configuration.set(path + ".world", location.getWorld().getName());
    }

    /**
     * Gets itemstack from config
     * @param configuration
     * @param path
     * @return
     */
    public static ItemStack getItemstack(YamlConfiguration configuration, String path){

        Material material = Material.valueOf(configuration.getString(path + ".material"));

        List<String> lore = new ArrayList<String>();

        if(configuration.contains(path + ".lore")){
            for(String a : configuration.getStringList(path + ".lore")){
                lore.add(ChatUtils.fixColor(a));
            }
        }

        int amount = 1;

        if(configuration.contains(path + ".amount")){
            configuration.getInt(path + ".amount");
        }

        String itemName = material.name();

        if(configuration.contains(path + ".name")){
            itemName = ChatUtils.fixColor(configuration.getString(path + ".name"));
        }

        ItemCreator creator = new ItemCreator(material, amount).setName(itemName).setLore(lore);

        if(configuration.contains(path + ".enchants")){
            for(String enchant : configuration.getConfigurationSection(path + ".enchants").getKeys(false)){
                //itemStack.addUnsafeEnchantment(Enchantment.getByName(enchant), configuration.getInt(path + ".enchants." + enchant));
                creator.addUnsafeEnchantment(Enchantment.getByName(enchant), configuration.getInt(path + ".enchants." + enchant));
            }
        }

        return creator.toItemStack();
    }


    public static void setPlugin(Plugin plugin) {
        ConfigUtils.plugin = plugin;
    }
}
