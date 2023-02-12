package dev.affan.mapcha;

import dev.affan.keeper.data.ConfigFile;
import dev.affan.keeper.data.ConfigValue;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

@ConfigFile("config.yml")
public class Config {

    public static String BYPASS_PERMISSION = "mapcha.bypass";

    @ConfigValue("title")
    public static String TITLE = "Captcha";

    @ConfigValue("prefix")
    public static String PREFIX = "[" + GREEN + "Mapcha" + RESET + "]";

    @ConfigValue("commands")
    public static List<String> ALLOWED_COMMANDS = Arrays.asList("/register", "/login");

    @ConfigValue("captcha.cache")
    public static boolean USE_CACHE = true;

    @ConfigValue("captcha.tries")
    public static int TRIES = 3;

    @ConfigValue("captcha.time")
    public static int TIME_LIMIT = 30;

    @ConfigValue("server.enabled")
    public static boolean SEND_TO_SERVER = false;

    @ConfigValue("server.name")
    public static String SUCCESS_SERVER = "";

    @ConfigValue("messages.success")
    public static String MESSAGE_SUCCESS = "Captcha " + GREEN + "solved!";

    @ConfigValue("messages.retry")
    public static String MESSAGE_RETRY = "Captcha " + YELLOW + "failed, " + RESET + "please try again. ({CURRENT}/{MAX})";

    @ConfigValue("messages.fail")
    public static String MESSAGE_FAIL = "Captcha " + RED + "failed!";

    @ConfigValue("styles.invert_color")
    public static boolean INVERT_COLOR = false;

    @ConfigValue("styles.points")
    public static boolean POINTS = true;

    @ConfigValue("styles.lines")
    public static boolean LINES = true;

}
