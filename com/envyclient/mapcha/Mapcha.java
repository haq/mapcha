package com.envyclient.mapcha;

import com.envyclient.mapcha.impl.commands.MapchaCommand;
import com.envyclient.mapcha.impl.events.JoinEvent;
import com.envyclient.mapcha.impl.events.MapEvent;
import com.envyclient.mapcha.impl.managers.CustomFileManager;
import com.envyclient.mapcha.impl.managers.SubCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Mapcha extends JavaPlugin {

    public static Mapcha INSTANCE;

    public String NAME = "NCPAction";
    public String PREFIX = "" + ChatColor.WHITE + "[" + ChatColor.GREEN + NAME + ChatColor.WHITE + "]";

    public CustomFileManager FILE_MANAGER;
    public SubCommandManager SUB_COMMAND_MANAGER;

    @Override
    public void onEnable() {
        INSTANCE = this;

        FILE_MANAGER = new CustomFileManager(this);
        SUB_COMMAND_MANAGER = new SubCommandManager();

        loadFiles();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        saveFiles();
    }

    private void loadFiles() {
        FILE_MANAGER.loadFiles();
    }

    private void saveFiles() {
        FILE_MANAGER.saveFiles();
    }

    private void registerCommands() {
        getCommand("mapcha").setExecutor(new MapchaCommand());
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new MapEvent(), this);
    }
}
