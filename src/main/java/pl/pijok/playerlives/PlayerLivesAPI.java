package pl.pijok.playerlives;

import org.bukkit.entity.Player;

public class PlayerLivesAPI {

    /**
     * Adds given amount of lives to player
     * @param player Receiver of lives
     * @param amount Amount of lives to add
     * @return True if lives were given successfully
     */
    public static boolean addPlayerLives(Player player, int amount){
        return Controllers.getLifeController().addLives(null, player.getName(), amount);
    }

    /**
     * Takes given amount of lives to player
     * @param player Owner of lives
     * @param amount Amount of lives to take
     * @return True if lives were take successfully
     */
    public static boolean takePlayerLives(Player player, int amount){
        return Controllers.getLifeController().takeLives(null, player.getName(), amount);
    }

    /**
     * Sets given amount of lives to player
     * @param player Receiver of lives
     * @param amount Amount of lives to set
     * @return True if lives were set successfully
     */
    public static boolean setPlayerLives(Player player, int amount){
        return Controllers.getLifeController().setLives(null, player.getName(), amount);
    }

    /**
     * Returns amount of player lives
     * @param player Owner of lives
     * @return Returns -999 if plugin couldn't find player
     */
    public static int getPlayerLives(Player player){
        return Controllers.getLifeController().getPlayerLives(player.getName());
    }

    public static boolean canHaveAnotherLive(Player player){
        return Controllers.getLifeController().canGetAnotherLive(player);
    }

}
