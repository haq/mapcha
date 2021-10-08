package me.affanhaq.mapcha;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.affanhaq.keeper.Keeper;
import me.affanhaq.mapcha.handlers.CaptchaHandler;
import me.affanhaq.mapcha.handlers.MapHandler;
import me.affanhaq.mapcha.handlers.PlayerHandler;
import me.affanhaq.mapcha.managers.CacheManager;
import me.affanhaq.mapcha.managers.CaptchaPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static me.affanhaq.mapcha.Config.SEND_TO_SERVER;
import static me.affanhaq.mapcha.Config.SUCCESS_SERVER;

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

    /**
     * Sends a player to a connected server after the captcha is completed.
     *
     * @param player the player to send
     */
    public void sendPlayerToServer(Player player) {
        if (SEND_TO_SERVER && SUCCESS_SERVER != null && !SUCCESS_SERVER.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(SUCCESS_SERVER);
            player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
        }
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}