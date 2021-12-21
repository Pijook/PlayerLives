package pl.pijok.playerlives;

import org.bukkit.plugin.Plugin;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class Controllers {

    private static LifeController lifeController;

    public static void create(){

        lifeController = new LifeController();

    }

    public static LifeController getLifeController() {
        return lifeController;
    }
}
