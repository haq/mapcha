package me.affanhaq.mapcha;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.affanhaq.mapcha.handlers.CaptchaHandler;
import me.affanhaq.mapcha.handlers.MapHandler;
import me.affanhaq.mapcha.handlers.PlayerHandler;
import me.affanhaq.mapcha.player.CaptchaPlayerManager;
import me.ihaq.keeper.Keeper;
import me.ihaq.keeper.data.ConfigValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static me.affanhaq.mapcha.Mapcha.Config.successServer;
import static org.bukkit.ChatColor.*;

public class Mapcha extends JavaPlugin {

    private final CaptchaPlayerManager playerManager = new CaptchaPlayerManager();

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
    public static void sendPlayerToServer(JavaPlugin javaPlugin, Player player) {
        if (successServer != null && !successServer.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(successServer);
            player.sendPluginMessage(javaPlugin, "BungeeCord", out.toByteArray());
        }
    }

    public static class Config {

        public static String permission = "mapcha.bypass";

        @ConfigValue("prefix")
        public static String prefix = "[" + GREEN + "Mapcha" + RESET + "]";

        @ConfigValue("commands")
        public static List<String> commands = Arrays.asList("/register", "/login");

        @ConfigValue("tries")
        public static int captchaTries = 3;

        @ConfigValue("time_limit")
        public static int captchaTimeLimit = 30;

        @ConfigValue("success_server")
        public static String successServer = "";

        @ConfigValue("messages.success")
        public static String captchaSuccessMessage = "Captcha " + GREEN + "solved!";

        @ConfigValue("messages.retry")
        public static String captchaRetryMessage = "Captcha " + YELLOW + "failed, " + RESET + "please try again. ({CURRENT}/{MAX})";

        @ConfigValue("messages.fail")
        public static String captchaFailMessage = "Captcha " + RED + "failed!";
    }

}