package com.envyclient.mapcha.api.file;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public abstract class CustomFile {

    private Gson gson;
    private File file;

    public CustomFile(Gson gson, File file) {
        this.gson = gson;
        this.file = file;
        makeDirectory();
    }

    private void makeDirectory() {
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
                createFirstFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void createFirstFile() throws IOException;

    public abstract void loadFile() throws IOException;

    public abstract void saveFile() throws IOException;

    protected File getFile() {
        return file;
    }

    protected Gson getGson() {
        return gson;
    }

}