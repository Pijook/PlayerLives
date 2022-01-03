package pl.pijok.playerlives;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private final PlayerLives plugin;
    private final int resourceID;

    public UpdateChecker(PlayerLives plugin, int resourceID){
        this.plugin = plugin;
        this.resourceID = resourceID;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceID).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

    public int getResourceID() {
        return resourceID;
    }
}
