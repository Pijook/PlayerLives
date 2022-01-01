package pl.pijok.playerlives;

import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pijok.playerlives.essentials.ConfigUtils;
import pl.pijok.playerlives.essentials.Debug;

public class PlayerLives extends JavaPlugin {

    private static Economy economy;

    //bStats
    private static int pluginID = 13755;
    private static Metrics metrics;

    @Override
    public void onEnable() {

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

        if (!setupEconomy()){
            Debug.log("&cCouldn't find vault! Some functions might be disabled");
        }

        metrics = new Metrics(this, pluginID);
        Debug.log("&aHooked to bStats! Thanks for support!");

    }

    @Override
    public void onDisable() {

        Controllers.getLifeController().save();

    }

    public boolean loadStuff(boolean reload){
        Debug.log("Loading " + getDescription().getName() + " v" + getDescription().getVersion() + " by " + getDescription().getAuthors());

        try{
            if(!reload){
                Controllers.create(this);
                Listeners.register(this);
                Commands.register(this);
                Controllers.getLifeController().load();

                if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                    new Placeholders(this).register();
                    Debug.log("&aFound PlaceholderAPI! Hooking...");
                }
                else{
                    Debug.log("&cPlaceholderAPI not found!");
                }

                Controllers.getItemController().load();
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }
}
