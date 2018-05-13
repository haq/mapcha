package me.ihaq.mapcha;

import me.ihaq.configmanager.data.ConfigValue;
import org.bukkit.ChatColor;

public class MapchaConfig {

    @ConfigValue("captcha_tries")
    public static int captchaTries = 3;

    @ConfigValue("captcha_time_limit")
    public static int captchaTimeLimit = 8;

    @ConfigValue()
    public static String CAPTCHA_SUCCESS = "Captcha " + ChatColor.GREEN + "solved!";

    @ConfigValue()
    public static String CAPTCHA_RETRY = "Captcha " + ChatColor.YELLOW + "failed, please try again. ({CURRENT_TRIES}/{MAX_TRIES})";

    @ConfigValue()
    public static String CAPTCHA_FAIL = "Captcha " + ChatColor.RED + "failed!";

    @ConfigValue("prefix")
    public static String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "Mapcha" + ChatColor.WHITE + "]";

}