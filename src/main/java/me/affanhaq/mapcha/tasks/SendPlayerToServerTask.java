package me.affanhaq.mapcha.tasks;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.affanhaq.mapcha.Mapcha;
import org.bukkit.entity.Player;

import static me.affanhaq.mapcha.Config.SEND_TO_SERVER;
import static me.affanhaq.mapcha.Config.SUCCESS_SERVER;

public class SendPlayerToServerTask implements Runnable {

    private final Mapcha mapcha;
    private final Player player;

    public SendPlayerToServerTask(Mapcha mapcha, Player player) {
        this.mapcha = mapcha;
        this.player = player;
    }

    @Override
    public void run() {
        if (SEND_TO_SERVER && SUCCESS_SERVER != null && !SUCCESS_SERVER.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(SUCCESS_SERVER);
            player.sendPluginMessage(mapcha, "BungeeCord", out.toByteArray());
        }
    }

    public static long delay() {
        return 15;
    }
}
