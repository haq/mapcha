package me.ihaq.mapcha;

import me.ihaq.configmanager.ConfigManager;
import me.ihaq.configmanager.data.ConfigValue;
import me.ihaq.mapcha.events.MapEvent;
import me.ihaq.mapcha.events.PlayerEvent;
import me.ihaq.mapcha.player.CaptchaPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.ChatColor.*;

public class Mapcha extends JavaPlugin {

    private CaptchaPlayerManager playerManager = new CaptchaPlayerManager();

    @Override
    public void onEnable() {
        new ConfigManager(this)
                .register(new Config())
                .load();

        // registering events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvent(this), this);
        pluginManager.registerEvents(new MapEvent(this), this);
    }

    public CaptchaPlayerManager getPlayerManager() {
        return playerManager;
    }

    public static class Config {

        public static String prefix = "[" + GREEN + "Mapcha" + RESET + "]";
        public static String permission = "mapcha.bypass";

        @ConfigValue("tries")
        public static int captchaTries = 3;

        @ConfigValue("time_limit")
        public static int captchaTimeLimit = 10;

        @ConfigValue("messages.success")
        public static String captchaSuccessMessage = "Captcha " + GREEN + "solved!";

        @ConfigValue("messages.retry")
        public static String captchaRetryMessage = "Captcha " + YELLOW + "failed, " + RESET + "please try again. ({CURRENT}/{MAX})";

        @ConfigValue("messages.fail")
        public static String captchaFailMessage = "Captcha " + RED + "failed!";
    }

}