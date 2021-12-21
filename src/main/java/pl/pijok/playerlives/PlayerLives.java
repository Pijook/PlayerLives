package pl.pijok.playerlives;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pijok.playerlives.essentials.ConfigUtils;
import pl.pijok.playerlives.essentials.Debug;

public class PlayerLives extends JavaPlugin {

    private static PlayerLives instance;

    @Override
    public void onEnable() {

        instance = this;

        Debug.setPrefix("[PlayerLives] ");
        ConfigUtils.setPlugin(this);

        if(!loadStuff(false)){
            Debug.sendError("Something went wrong while loading plugin " + getDescription().getName() + "! Disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
        else{
            Debug.log("&aEverything loaded!");
            Debug.log("&aHave a nice day :D");
        }

    }

    @Override
    public void onDisable() {

        Controllers.getLifeController().save();

    }

    public boolean loadStuff(boolean reload){
        Debug.log("Loading " + getDescription().getName() + " v" + getDescription().getVersion() + " by " + getDescription().getAuthors());

        try{
            if(!reload){
                Controllers.create();
                Listeners.register(this);
                Commands.register(this);
                Controllers.getLifeController().load();

                if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                    new Placeholders().register();
                    Debug.log("&aFound PlaceholderAPI! Hooking...");
                }
                else{
                    Debug.log("&cPlaceholderAPI not found!");
                }
            }

            Debug.log("Loading lang!");
            Lang.load();

            Debug.log("Loading settings!");
            Settings.load();

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static PlayerLives getInstance() {
        return instance;
    }
}
