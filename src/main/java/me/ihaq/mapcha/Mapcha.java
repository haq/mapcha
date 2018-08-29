package me.ihaq.mapcha;

import me.ihaq.keeper.Keeper;
import me.ihaq.keeper.data.ConfigValue;
import me.ihaq.mapcha.events.MapEvent;
import me.ihaq.mapcha.events.PlayerEvent;
import me.ihaq.mapcha.player.CaptchaPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class Mapcha extends JavaPlugin {

    private CaptchaPlayerManager playerManager = new CaptchaPlayerManager();

    @Override
    public void onEnable() {
        new Keeper(this)
                .register(new Config())
                .load();

        // registering events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvent(this), this);
        pluginManager.registerEvents(new MapEvent(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public CaptchaPlayerManager getPlayerManager() {
        return playerManager;
    }

    public static class Config {

        @ConfigValue("prefix")
        public static String prefix = "[" + GREEN + "Mapcha" + RESET + "]";

        public static String permission = "mapcha.bypass";

        @ConfigValue("commands")
        public static List<String> commands = Arrays.asList("/register", "/login");

        @ConfigValue("tries")
        public static int captchaTries = 3;

        @ConfigValue("time_limit")
        public static int captchaTimeLimit = 10;

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