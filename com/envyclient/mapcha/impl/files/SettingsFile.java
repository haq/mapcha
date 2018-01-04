package com.envyclient.mapcha.impl.files;

import com.envyclient.mapcha.api.file.CustomFile;
import com.envyclient.mapcha.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsFile extends CustomFile {

    public SettingsFile(Gson gson, File file) {
        super(gson, file);
    }

    @Override
    public void createFirstFile() throws IOException {
        saveFile();
    }

    @Override
    public void loadFile() throws IOException {

        FileReader fr = new FileReader(getFile());
        JsonObject jsonObject = getGson().fromJson(fr, JsonObject.class);

        if (jsonObject.has("main_color"))
            Util.MAIN_COLOR = jsonObject.get("main_color").getAsString().replace("&", "" + ChatColor.COLOR_CHAR);

        if (jsonObject.has("secondary_color"))
            Util.SECONDARY_COLOR = jsonObject.get("secondary_color").getAsString().replace("&", "" + ChatColor.COLOR_CHAR);

        Util.PREFIX = Util.SECONDARY_COLOR + "[" + Util.MAIN_COLOR + Util.NAME + Util.SECONDARY_COLOR + "]";


        if (jsonObject.has("captcha_success_message"))
            Util.CAPTCHA_SUCCESS = jsonObject.get("captcha_success_message").getAsString().replace("&", "" + ChatColor.COLOR_CHAR);


        if (jsonObject.has("captcha_retry_message"))
            Util.CAPTCHA_RETRY = jsonObject.get("captcha_retry_message").getAsString().replace("&", "" + ChatColor.COLOR_CHAR);


        if (jsonObject.has("captcha_retry_fail"))
            Util.CAPTCHA_FAIL = jsonObject.get("captcha_retry_fail").getAsString().replace("&", "" + ChatColor.COLOR_CHAR);


        if (jsonObject.has("captcha_tries"))
            Util.CAPTCHA_TRIES = jsonObject.get("captcha_tries").getAsInt();

        if (jsonObject.has("captcha_time_limit"))
            Util.CAPTCHA_TIME_LIMIT = jsonObject.get("captcha_time_limit").getAsInt();


        fr.close();
    }

    @Override
    public void saveFile() throws IOException {
        FileWriter fw = new FileWriter(getFile());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("main_color", Util.MAIN_COLOR.replaceAll("" + ChatColor.COLOR_CHAR, "&"));
        jsonObject.addProperty("secondary_color", Util.SECONDARY_COLOR.replaceAll("" + ChatColor.COLOR_CHAR, "&"));
        jsonObject.addProperty("captcha_tries", Util.CAPTCHA_TRIES);
        jsonObject.addProperty("captcha_time_limit", Util.CAPTCHA_TIME_LIMIT);
        jsonObject.addProperty("captcha_success_message", Util.CAPTCHA_SUCCESS.replaceAll("" + ChatColor.COLOR_CHAR, "&"));
        jsonObject.addProperty("captcha_retry_message", Util.CAPTCHA_RETRY.replaceAll("" + ChatColor.COLOR_CHAR, "&"));
        jsonObject.addProperty("captcha_retry_fail", Util.CAPTCHA_FAIL.replaceAll("" + ChatColor.COLOR_CHAR, "&"));

        fw.write(getGson().toJson(jsonObject));
        fw.close();
    }
}
