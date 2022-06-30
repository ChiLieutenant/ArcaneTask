package com.chilieutenant.arcanetask;

import com.chilieutenant.arcanetask.listeners.MainListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.saveDefaultConfig();
        this.getPluginLoader().createRegisteredListeners(new MainListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
