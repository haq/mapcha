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

import java.util.*;

import static me.affanhaq.mapcha.Mapcha.Config.sendToSuccessServer;
import static me.affanhaq.mapcha.Mapcha.Config.successServerName;
import static org.bukkit.ChatColor.*;

public class Mapcha extends JavaPlugin {

    private final CaptchaPlayerManager playerManager = new CaptchaPlayerManager();
    private final Set<UUID> completedCache = new HashSet<>();

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

    public Set<UUID> getCompletedCache() {
        return completedCache;
    }

    /**
     * Sends a player to a connected server after the captcha is completed.
     *
     * @param player the player to send
     */
    public static void sendPlayerToServer(JavaPlugin javaPlugin, Player player) {
        if (sendToSuccessServer && successServerName != null && !successServerName.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(successServerName);
            player.sendPluginMessage(javaPlugin, "BungeeCord", out.toByteArray());
        }
    }

    public static class Config {
        public static String permission = "mapcha.bypass";

        @ConfigValue("prefix")
        public static String prefix = "[" + GREEN + "Mapcha" + RESET + "]";

        @ConfigValue("commands")
        public static List<String> commands = Arrays.asList("/register", "/login");

        @ConfigValue("captcha.cache")
        public static boolean useCompletedCache = true;

        @ConfigValue("captcha.tries")
        public static int tries = 3;

        @ConfigValue("captcha.time")
        public static int timeLimit = 30;

        @ConfigValue("server.enabled")
        public static boolean sendToSuccessServer = false;

        @ConfigValue("server.name")
        public static String successServerName = "";

        @ConfigValue("messages.success")
        public static String successMessage = "Captcha " + GREEN + "solved!";

        @ConfigValue("messages.retry")
        public static String retryMessage = "Captcha " + YELLOW + "failed, " + RESET + "please try again. ({CURRENT}/{MAX})";

        @ConfigValue("messages.fail")
        public static String failMessage = "Captcha " + RED + "failed!";
    }

}