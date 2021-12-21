package pl.pijok.playerlives.lifecontroller;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.customEvents.LifeAddEvent;
import pl.pijok.playerlives.customEvents.LifeSetEvent;
import pl.pijok.playerlives.customEvents.LifeTakeEvent;
import pl.pijok.playerlives.customEvents.LivesEndEvent;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.essentials.ConfigUtils;

import java.util.HashMap;

public class LifeController {

    private final HashMap<String, LivesAccount> playerLives;

    public LifeController(){
        playerLives = new HashMap<>();
    }

    public void load(){
        YamlConfiguration configuration = ConfigUtils.load("players.yml");

        for(String nickname : configuration.getConfigurationSection("players").getKeys(false)){

            int currentLives = configuration.getInt("players." + nickname + ".currentLives");
            long deathTime = configuration.getLong("players." + nickname + ".deathTime");

            playerLives.put(nickname, new LivesAccount(currentLives, deathTime));
        }
    }

    public void save(){
        YamlConfiguration configuration = ConfigUtils.load("players.yml");

        configuration.set("players", null);

        for(String nickname : playerLives.keySet()){
            LivesAccount livesAccount = playerLives.get(nickname);

            configuration.set("players." + nickname + ".currentLives", livesAccount.getCurrentLives());
            configuration.set("players." + nickname + ".deathTime", livesAccount.getDeathTime());
        }

        ConfigUtils.save(configuration, "players.yml");
    }

    public void addLives(CommandSender sender, String nickname, int amount){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return;
        }

        playerLives.get(nickname).addLives(amount);

        Player target = Bukkit.getPlayer(nickname);
        if(target != null && target.isOnline()){
            if(amount == 1){
                ChatUtils.sendMessage(target, Lang.getLang("ADD_SINGLE_LIFE"));
            }
            else{
                ChatUtils.sendMessage(target, Lang.getLang("ADD_MULTIPLE_LIVES").replace("%amount%", "" + amount));
            }
        }

        if(sender != null){
            ChatUtils.sendMessage(sender, Lang.getLang("ADD_LIVE_SUCCESS").replace("%amount%", "" + amount).replace("%name%", nickname));
        }

        LifeAddEvent lifeAddEvent = new LifeAddEvent(nickname, amount);
        Bukkit.getPluginManager().callEvent(lifeAddEvent);
    }

    public void takeLives(CommandSender sender, String nickname, int amount){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return;
        }

        playerLives.get(nickname).takeLives(amount);

        Player target = Bukkit.getPlayer(nickname);
        if(target != null && target.isOnline()){
            if(amount == 1){
                ChatUtils.sendMessage(target, Lang.getLang("TAKE_SINGLE_LIFE"));
            }
            else{
                ChatUtils.sendMessage(target, Lang.getLang("TAKE_MULTIPLE_LIVES").replace("%amount%", "" + amount));
            }
        }

        if(sender != null){
            ChatUtils.sendMessage(sender, Lang.getLang("TAKE_LIVE_SUCCESS").replace("%amount%", "" + amount).replace("%name%", nickname));
        }

        LifeTakeEvent lifeTakeEvent = new LifeTakeEvent(nickname, amount);
        Bukkit.getPluginManager().callEvent(lifeTakeEvent);
    }

    public void setLives(CommandSender sender, String nickname, int value){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return;
        }

        playerLives.get(nickname).setLives(value);

        Player target = Bukkit.getPlayer(nickname);
        if(target != null && target.isOnline()){
            ChatUtils.sendMessage(target, Lang.getLang("SET_LIVES").replace("%amount%", "" + value));
        }

        if(sender != null){
            ChatUtils.sendMessage(sender, Lang.getLang("SET_LIVES_OTHER").replace("%amount%", "" + value).replace("%name%", nickname));
        }

        LifeSetEvent lifeSetEvent = new LifeSetEvent(nickname, value);
        Bukkit.getPluginManager().callEvent(lifeSetEvent);
    }

    public int getPlayerLives(String nickname){
        if(playerLives.containsKey(nickname)){
            return playerLives.get(nickname).getCurrentLives();
        }
        return -999;
    }

    public void kill(String nickname){
        playerLives.get(nickname).setDeathTime(System.currentTimeMillis());
        playerLives.get(nickname).setLives(Settings.livesAfterResurrection);
        LivesEndEvent livesEndEvent = new LivesEndEvent(nickname, playerLives.get(nickname).getCurrentLives());
        Bukkit.getPluginManager().callEvent(livesEndEvent);
    }

    public boolean joinedBefore(String nickname){
        return playerLives.containsKey(nickname);
    }

    public void createNewAccount(String nickname){
        playerLives.put(nickname, new LivesAccount(Settings.defaultLivesAmount, -1));
    }

    public boolean isStillDead(String nickname){
        long deathTime = playerLives.get(nickname).getDeathTime();
        if(deathTime == -1){
            return false;
        }
        long timeGone = System.currentTimeMillis() - deathTime;
        double timeGoneInHours = (double)((double)timeGone / 3600000);
        return !(timeGoneInHours > Settings.deathTime);
    }

    public void resurrectPlayer(String donator, String nickname){
        playerLives.get(nickname).setDeathTime(-1);
        playerLives.get(nickname).setLives(Settings.livesAfterResurrection);
        if(donator != null){
            playerLives.get(donator).takeLives(Settings.resurrectionCost);
        }
    }
}
