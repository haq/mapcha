package me.ihaq.mapcha;

import me.ihaq.configmanager.ConfigManager;
import me.ihaq.mapcha.events.MapEvent;
import me.ihaq.mapcha.events.PlayerEvent;
import me.ihaq.mapcha.player.CaptchaPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Mapcha extends JavaPlugin {

    private final CaptchaPlayerManager playerManager = new CaptchaPlayerManager();

    @Override
    public void onEnable() {
        new ConfigManager(this).register(this).load();

        // registering events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvent(this), this);
        pluginManager.registerEvents(new MapEvent(this), this);
    }

    public CaptchaPlayerManager getPlayerManager() {
        return playerManager;
    }
}
