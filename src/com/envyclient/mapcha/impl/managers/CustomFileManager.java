package com.envyclient.mapcha.impl.managers;

import com.envyclient.mapcha.api.file.CustomFile;
import com.envyclient.mapcha.api.manager.Manager;
import com.envyclient.mapcha.impl.files.SettingsFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomFileManager extends Manager<CustomFile> {

    private File directory;
    private Gson gson;

    public CustomFileManager(JavaPlugin javaPlugin) {
        directory = new File(javaPlugin.getDataFolder() + "/");
        gson = new GsonBuilder().setPrettyPrinting().create();
        makeDirectory();
        registerFiles();
    }


    private void makeDirectory() {
        if (!directory.exists())
            directory.mkdirs();
    }

    private void registerFiles() {
        getContents().add(new SettingsFile(gson, new File(directory, "settings.json")));
    }

    public boolean loadFiles() {
        for (CustomFile file : getContents()) {
            try {
                file.loadFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void saveFiles() {
        for (CustomFile file : getContents()) {
            try {
                file.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CustomFile getFile(Class<? extends CustomFile> clazz) {
        for (CustomFile file : getContents()) {
            if (file.getClass().equals(clazz))
                return file;
        }
        return null;
    }


}