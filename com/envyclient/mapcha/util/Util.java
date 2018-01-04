package com.envyclient.mapcha.util;

import com.envyclient.mapcha.Mapcha;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;

import java.io.File;

public class Util {

    public static String MAIN_COLOR = ChatColor.COLOR_CHAR + "a";
    public static String SECONDARY_COLOR = ChatColor.COLOR_CHAR + "f";

    public static String NAME = "Mapcha";
    public static String PREFIX = SECONDARY_COLOR + "[" + MAIN_COLOR + NAME + SECONDARY_COLOR + "]";

    public static int CAPTCHA_TRIES = 3;
    public static int CAPTCHA_LENGTH = 5;
    public static String CAPTCHA_SUCESS = "Captcha solved!";
    public static String CAPTCHA_RETRY = "Captcha failed, please try again. ({TRIES}/{MAX_TRIES})";
    public static String CAPTCHA_FAIL = "Wrong captcha!";

    public static Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static File PLUGIN_DIRECTORY = new File(Mapcha.INSTANCE.getDataFolder() + "/");
}
