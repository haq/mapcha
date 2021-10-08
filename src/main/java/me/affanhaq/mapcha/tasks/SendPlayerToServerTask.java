package me.affanhaq.mapcha.tasks;

import me.affanhaq.mapcha.Mapcha;
import org.bukkit.entity.Player;

public class SendPlayerToServerTask implements Runnable {

    private final Mapcha mapcha;
    private final Player player;

    public SendPlayerToServerTask(Mapcha mapcha, Player player) {
        this.mapcha = mapcha;
        this.player = player;
    }

    @Override
    public void run() {
        mapcha.sendPlayerToServer(player);
    }

    public static long delay() {
        return 15;
    }
}
