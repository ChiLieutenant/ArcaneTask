package com.chilieutenant.arcanetask.listeners;

import com.chilieutenant.arcanetask.handlers.DeadPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

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
            if(DeadPlayer.isPlayerDead(player)){
                DeadPlayer dp = DeadPlayer.getDeadPlayer(player);
                dp.revive();
            }
        }
    }

}
