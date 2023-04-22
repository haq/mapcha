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

    @ConfigValue("server_success")
    public static String SERVER_SUCCESS = "";

    @ConfigValue("messages.success")
    public static String MESSAGE_SUCCESS = "Captcha " + GREEN + "solved!";

    @ConfigValue("messages.retry")
    public static String MESSAGE_RETRY = "Captcha " + YELLOW + "failed, " + RESET + "please try again. ({CURRENT}/{MAX})";

    @ConfigValue("messages.fail")
    public static String MESSAGE_FAIL = "Captcha " + RED + "failed!";

    @ConfigValue("captcha.tries")
    public static int TRIES = 3;

    @ConfigValue("captcha.time")
    public static int TIME_LIMIT = 30;

    @ConfigValue("captcha.font")
    public static String FONT = "Arial";

    @ConfigValue("captcha.invert_color")
    public static boolean INVERT_COLOR = false;

    @ConfigValue("captcha.points")
    public static boolean POINTS = true;

    @ConfigValue("captcha.lines")
    public static boolean LINES = true;

    @ConfigValue("other.inventory_slot")
    public static int INVENTORY_SLOT = 4;

    @ConfigValue("other.drop_items")
    public static boolean DROP_ITEMS = false;

    @ConfigValue("other.hide_players")
    public static boolean HIDE_PLAYERS = true;

    @ConfigValue("other.cache")
    public static boolean USE_CACHE = true;
}
