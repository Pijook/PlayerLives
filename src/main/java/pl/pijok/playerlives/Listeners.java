package pl.pijok.playerlives;

import pl.pijok.playerlives.listeners.DeathListener;
import pl.pijok.playerlives.listeners.JoinListener;
import pl.pijok.playerlives.listeners.PlayerInteractListener;

public class Listeners {

    public static void register(PlayerLives plugin){

        plugin.getServer().getPluginManager().registerEvents(new JoinListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DeathListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), plugin);

    }

}
