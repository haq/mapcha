package me.ihaq.mapcha;

import me.ihaq.configmanager.data.ConfigValue;
import org.bukkit.ChatColor;

public class MapchaConfig {

    public static String prefix = "[" + ChatColor.GREEN + "Mapcha" + ChatColor.RESET + "]";

    @ConfigValue("captcha_tries")
    public static int captchaTries;

    @ConfigValue("captcha_time_limit")
    public static int captchaTimeLimit;

    @ConfigValue("captcha_success_message")
    public static String captchaSuccessMessage;

    @ConfigValue("captcha_retry_message")
    public static String captchaRetryMessage;

    @ConfigValue("captcha_fail_message")
    public static String captchaFailMessage;

}