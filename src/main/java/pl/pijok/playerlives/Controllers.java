package pl.pijok.playerlives;

import org.bukkit.plugin.Plugin;
import pl.pijok.playerlives.customItems.ItemController;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class Controllers {

    private static LifeController lifeController;
    private static ItemController itemController;

    public static void create(PlayerLives plugin){

        lifeController = new LifeController();
        itemController = new ItemController(plugin);
    }

    public static LifeController getLifeController() {
        return lifeController;
    }

    public static ItemController getItemController() {
        return itemController;
    }
}
