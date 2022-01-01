package pl.pijok.playerlives.lifecontroller;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.PlayerLives;
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

    public boolean addLives(CommandSender sender, String nickname, int amount){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return false;
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
        return true;
    }

    public boolean takeLives(CommandSender sender, String nickname, int amount){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return false;
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
        return true;
    }

    public boolean setLives(CommandSender sender, String nickname, int value){
        if(!playerLives.containsKey(nickname)){
            if(sender != null){
                ChatUtils.sendMessage(sender, Lang.getLang("PLAYER_NOT_FOUND"));
            }
            return false;
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
        return true;
    }

    public int getPlayerLives(String nickname){
        if(playerLives.containsKey(nickname)){
            return playerLives.get(nickname).getCurrentLives();
        }
        return -999;
    }

    public void punishPlayer(Player player, PunishmentType punishmentType){
        String nickname = player.getName();
        String message = Lang.getLang("DEATH_INFO");

        if(punishmentType.equals(PunishmentType.EXILE)){
            playerLives.get(nickname).setDeathTime(System.currentTimeMillis());
        }
        else if(punishmentType.equals(PunishmentType.COMMAND)){
            for(String command : Settings.punishmentCommands){
                command = command.replace("%player%", nickname);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
        else if(punishmentType.equals(PunishmentType.MONEY)){
            if(PlayerLives.getEconomy().getBalance(player) < Settings.moneyToTake){
                punishPlayer(player, Settings.notEnoughMoneyPunishment);
            }
            else{
                PlayerLives.getEconomy().withdrawPlayer(player, Settings.moneyToTake);
            }
        }
        else if(punishmentType.equals(PunishmentType.DROP_ITEMS)){
            if(Settings.dropToWorld){
                for(ItemStack itemStack : player.getInventory()){
                    if(itemStack == null || itemStack.getType().equals(Material.AIR)){
                        continue;
                    }

                    player.getWorld().dropItem(player.getLocation(), itemStack);
                }
            }
            else{
                player.getInventory().clear();
            }
        }
        else if(punishmentType.equals(PunishmentType.NONE)){
            //Nothing
        }

        if(punishmentType.equals(PunishmentType.EXILE)) {
            player.kickPlayer(ChatUtils.fixColor(message));
        }
        else{
            ChatUtils.sendMessage(player, message);
        }

        playerLives.get(nickname).setLives(Settings.livesAfterPunishment);
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
        playerLives.get(nickname).setLives(Settings.livesAfterPunishment);
        if(donator != null){
            playerLives.get(donator).takeLives(Settings.resurrectionCost);
        }
    }
}
