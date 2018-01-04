package com.envyclient.mapcha.impl.managers;

import com.envyclient.mapcha.api.manager.Manager;
import com.envyclient.mapcha.api.player.CustomPlayer;
import org.bukkit.entity.Player;

public class CustomPlayerManager extends Manager<CustomPlayer> {


    public void addPlayer(CustomPlayer player) {
        getContents().add(player);
    }

    public void removePlayer(CustomPlayer player) {
        getContents().remove(player);
    }

    public CustomPlayer getPlayer(Player player) {
        for (CustomPlayer customPlayer : getContents()) {
            if (customPlayer.getPlayer().getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString()))
                return customPlayer;
        }
        return null;
    }
}