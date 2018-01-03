package com.envyclient.mapcha.impl.files;

import com.envyclient.mapcha.api.file.CustomFile;
import com.envyclient.mapcha.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

        Util.TEXT = jsonObject.has("text") && jsonObject.get("text").getAsBoolean();
        Util.MATH = jsonObject.has("math") && jsonObject.get("math").getAsBoolean();

        fr.close();

    }

    @Override
    public void saveFile() throws IOException {
        FileWriter fw = new FileWriter(getFile());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", Util.TEXT);
        jsonObject.addProperty("math", Util.MATH);

        fw.write(getGson().toJson(jsonObject));
        fw.close();
    }
}
