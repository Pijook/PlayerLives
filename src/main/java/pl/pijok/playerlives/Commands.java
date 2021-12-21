package pl.pijok.playerlives;

import pl.pijok.playerlives.commands.LivesCommand;
import pl.pijok.playerlives.commands.ResurrectCommand;

public class Commands {

    public static void register(PlayerLives plugin){

        plugin.getCommand("playerlives").setExecutor(new LivesCommand());
        plugin.getCommand("resurrect").setExecutor(new ResurrectCommand());

    }

}
