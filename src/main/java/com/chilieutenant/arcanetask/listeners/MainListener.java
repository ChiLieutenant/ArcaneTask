package com.chilieutenant.arcanetask.listeners;

import com.chilieutenant.arcanetask.Main;
import com.chilieutenant.arcanetask.handlers.DeadPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class MainListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player player){
            if(event.getFinalDamage() >= player.getHealth() && !DeadPlayer.isPlayerDead(player)){
                new DeadPlayer(player);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player player){
            if(DeadPlayer.isPlayerDead(player)){
                DeadPlayer dp = DeadPlayer.getDeadPlayer(player);
                dp.kill();
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event){
        Entity entity = event.getRightClicked();
        if(entity instanceof Player player){
            if(DeadPlayer.isPlayerDead(player) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_APPLE)){
                DeadPlayer dp = DeadPlayer.getDeadPlayer(player);
                dp.revive();
            }
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player player){
            if(DeadPlayer.isPlayerDead(player)){
                if(player.hasPermission(Main.getInstance().getConfig().getString("bypassPermission"))){
                    DeadPlayer dp = DeadPlayer.getDeadPlayer(player);
                    dp.revive();
                }else{
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (DeadPlayer.isPlayerDead(player)) {
            DeadPlayer dp = DeadPlayer.getDeadPlayer(player);
            dp.kill();
        }
    }

}
