package com.envyclient.mapcha.api.player;

import org.bukkit.entity.Player;

public class CustomPlayer {

    public Player player;
    public CustomPlayerMode mode;

    public CustomPlayer(Player player, CustomPlayerMode mode) {
        this.player = player;
        this.mode = mode;
    }
}
