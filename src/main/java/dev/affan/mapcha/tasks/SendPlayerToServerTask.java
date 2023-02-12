package dev.affan.mapcha.tasks;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SendPlayerToServerTask extends BukkitRunnable {

    private final Mapcha mapcha;
    private final Player player;

    public SendPlayerToServerTask(Mapcha mapcha, Player player) {
        this.mapcha = mapcha;
        this.player = player;
    }

    @Override
    public void run() {
        if (Config.SERVER_SUCCESS == null || Config.SERVER_SUCCESS.isEmpty()) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(Config.SERVER_SUCCESS);

        player.sendPluginMessage(mapcha, "BungeeCord", out.toByteArray());
    }

    public static long delay() {
        return 15;
    }
}
