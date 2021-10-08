package me.affanhaq.mapcha.tasks;

import me.affanhaq.mapcha.Config;
import org.bukkit.entity.Player;

public class KickPlayerTask implements Runnable {

    private final Player player;

    public KickPlayerTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.kickPlayer(Config.PREFIX + " " + Config.MESSAGE_FAIL);
    }

    public static long delay() {
        return Config.TIME_LIMIT * 20L;
    }
}
