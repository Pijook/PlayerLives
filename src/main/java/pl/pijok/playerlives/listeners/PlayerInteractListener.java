package pl.pijok.playerlives.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.pijok.playerlives.Controllers;
import pl.pijok.playerlives.Lang;
import pl.pijok.playerlives.Settings;
import pl.pijok.playerlives.essentials.ChatUtils;
import pl.pijok.playerlives.lifecontroller.LifeController;

import java.util.HashMap;

public class PlayerInteractListener implements Listener {

    private final LifeController lifeController = Controllers.getLifeController();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if(itemStack == null || itemStack.getType().equals(Material.AIR)){
                return;
            }

            HashMap<String, ItemStack> customItems = Controllers.getItemController().getCustomItems();

            int lives = lifeController.getPlayerLives(player.getName());
            int toAdd = -1;


            for(String id : customItems.keySet()){
                ItemStack customItem = customItems.get(id);

                if(customItem.isSimilar(itemStack)){
                    toAdd = Settings.eatableCustomMaterials.get(id);
                }
            }

            if(toAdd == -1){
                if(Settings.eatableMaterials.containsKey(itemStack.getType())){
                    Settings.eatableMaterials.get(itemStack.getType());
                }
            }

            if(toAdd != -1){
                /*if(lives >= Settings.maxLives){
                    ChatUtils.sendMessage(player, Lang.getLang("MAX_LIVES"));
                    event.setCancelled(true);
                }*/
                if(checkMaxLives(player, lives)){
                    ChatUtils.sendMessage(player, Lang.getLang("MAX_LIVES"));
                    event.setCancelled(true);
                }
                else{
                    if(toAdd + lives > Settings.maxLives){
                        toAdd = Settings.maxLives - lives;
                    }
                    lifeController.addLives(null, player.getName(), toAdd);
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }

        }
    }

    private boolean checkMaxLives(Player player, int currentLives){
        if(Settings.maxLivesPermissionBased){
            for(String permission : Settings.maxLivesPermissions.keySet()){
                if(player.hasPermission(permission)){
                    int amount = Settings.maxLivesPermissions.get(permission);
                    return currentLives >= amount;
                }
            }
        }
        return currentLives >= Settings.maxLives;
    }

}
