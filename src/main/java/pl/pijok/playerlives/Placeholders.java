package pl.pijok.playerlives;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.pijok.playerlives.lifecontroller.LifeController;

public class Placeholders extends PlaceholderExpansion {

    private final LifeController lifeController = Controllers.getLifeController();
    private final PlayerLives plugin;

    public Placeholders(PlayerLives plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playerlives";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Pijok";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("currentAmount")){
            return String.valueOf(lifeController.getPlayerLives(player.getName()));
        }

        return null;
    }
}
