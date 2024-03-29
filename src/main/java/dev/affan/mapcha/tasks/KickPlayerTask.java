package dev.affan.mapcha.tasks;

import dev.affan.mapcha.Config;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KickPlayerTask extends BukkitRunnable {

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
