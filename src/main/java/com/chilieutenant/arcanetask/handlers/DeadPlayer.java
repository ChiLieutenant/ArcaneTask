package com.chilieutenant.arcanetask.handlers;

import com.chilieutenant.arcanetask.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DeadPlayer {

    private final Player player;
    private final ArmorStand armorStand;

    public static HashMap<Player, DeadPlayer> deadPlayers = new HashMap<>();

    public DeadPlayer(Player player){
        this.player = player;
        this.armorStand = createArmorStand();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 1));
        startTimer();
        deadPlayers.put(player, this);
    }

    public static boolean isPlayerDead(Player player){
        return deadPlayers.containsKey(player);
    }

    public static DeadPlayer getDeadPlayer(Player player){
        return deadPlayers.get(player);
    }

    public Player getPlayer() {
        return player;
    }

    public ArmorStand createArmorStand(){
        ArmorStand as = getPlayer().getWorld().spawn(getPlayer().getLocation().add(0, -1, 0), ArmorStand.class);
        as.setVisible(false);
        as.setGravity(false);
        as.addPassenger(getPlayer());
        return as;
    }

    public void startTimer(){
        long time = System.currentTimeMillis();
        new BukkitRunnable(){
            @Override
            public void run() {
                getPlayer().setGlowing(true);
                if(System.currentTimeMillis() > time + (Main.getInstance().getConfig().getInt("reviveTime") * 1000L)){
                    //kill player
                    kill();
                }
                if(!deadPlayers.containsKey(getPlayer())){
                    armorStand.remove();
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public void kill(){
        getPlayer().setHealth(0);
        deadPlayers.remove(getPlayer());
    }

    public void revive(){
        getPlayer().setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        deadPlayers.remove(getPlayer());
    }
}
