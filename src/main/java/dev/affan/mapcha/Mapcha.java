package dev.affan.mapcha;

import dev.affan.keeper.Keeper;
import dev.affan.mapcha.handlers.*;
import dev.affan.mapcha.managers.CacheManager;
import dev.affan.mapcha.managers.CaptchaPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Mapcha extends JavaPlugin {

    private final CaptchaPlayerManager playerManager = new CaptchaPlayerManager();
    private final CacheManager cacheManager = new CacheManager();

    @Override
    public void onEnable() {
        new Keeper(this)
                .register(new Config())
                .load();

        // registering events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CaptchaHandler(this), this);
        pluginManager.registerEvents(new ChatHandler(this), this);
        pluginManager.registerEvents(new CommandHandler(this), this);
        pluginManager.registerEvents(new JoinAndQuitHandler(this), this);
        pluginManager.registerEvents(new MapHandler(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public CaptchaPlayerManager getPlayerManager() {
        return playerManager;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
