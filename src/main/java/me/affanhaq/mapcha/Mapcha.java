package me.affanhaq.mapcha;

import dev.affan.keeper.Keeper;
import me.affanhaq.mapcha.handlers.CaptchaHandler;
import me.affanhaq.mapcha.handlers.MapHandler;
import me.affanhaq.mapcha.handlers.PlayerHandler;
import me.affanhaq.mapcha.managers.CacheManager;
import me.affanhaq.mapcha.managers.CaptchaPlayerManager;
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
        pluginManager.registerEvents(new PlayerHandler(this), this);
        pluginManager.registerEvents(new MapHandler(this), this);
        pluginManager.registerEvents(new CaptchaHandler(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public CaptchaPlayerManager getPlayerManager() {
        return playerManager;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
