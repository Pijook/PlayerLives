package pl.pijok.playerlives.customItems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import pl.pijok.playerlives.PlayerLives;
import pl.pijok.playerlives.essentials.ConfigUtils;

import java.util.HashMap;

public class ItemController {

    private HashMap<String, ItemStack> customItems;
    private final PlayerLives plugin;

    public ItemController(PlayerLives plugin){
        customItems = new HashMap<>();
        this.plugin = plugin;
    }

    public void load(){
        YamlConfiguration configuration = ConfigUtils.load("customItems.yml");

        for(String itemID : configuration.getConfigurationSection("customItems").getKeys(false)){

            ItemStack result = ConfigUtils.getItemstack(configuration, "customItems." + itemID + ".result");

            //ShapedRecipe shapedRecipe = new ShapedRecipe(result);
            ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(plugin, itemID), result);
            shapedRecipe.shape("abc", "def", "ghi");

            char[] shapeHelper = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

            int index = 0;
            for(String line : configuration.getStringList("customItems." + itemID + ".crafting")){
                String[] ingredients = line.split(":");
                for(int i = 0; i < 3; i++){
                    shapedRecipe.setIngredient(shapeHelper[index], Material.valueOf(ingredients[i]));
                    index++;
                }
            }

            customItems.put(configuration.getString("customItems." + itemID + ".ID"), result);
            plugin.getServer().addRecipe(shapedRecipe);
        }
    }

    public HashMap<String, ItemStack> getCustomItems(){
        return this.customItems;
    }
}
