package me.affanhaq.mapcha.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.affanhaq.mapcha.Mapcha;
import me.affanhaq.mapcha.events.CaptchaFailedEvent;
import me.affanhaq.mapcha.events.CaptchaSuccessEvent;
import me.affanhaq.mapcha.player.CaptchaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.affanhaq.mapcha.Mapcha.Config.*;

public class CaptchaHandler implements Listener {

    private final Mapcha mapcha;

    public CaptchaHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onCaptchaSuccess(CaptchaSuccessEvent event) {
        CaptchaPlayer player = event.getPlayer();

        player.getPlayer().sendMessage(prefix + " " + captchaSuccessMessage);
        player.resetInventory();
        mapcha.getPlayerManager().removePlayer(player);
        sendPlayerToServer(player.getPlayer());
    }

    @EventHandler
    public void onCaptchaFail(CaptchaFailedEvent event) {
        CaptchaPlayer player = event.getPlayer();

        // kicking the player because he's out of tries
        if (player.getTries() >= (captchaTries - 1)) {
            Bukkit.getScheduler().runTask(mapcha, () -> player.getPlayer().kickPlayer(prefix + " " + captchaFailMessage));
        } else { // telling the player to try again
            player.setTries(player.getTries() + 1);
            player.getPlayer().sendMessage(
                    prefix + " " + captchaRetryMessage.replace("{CURRENT}", String.valueOf(player.getTries())).replace("{MAX}", String.valueOf(captchaTries))
            );
        }
    }

    /**
     * Sends a player to a connected server after the captcha is completed.
     *
     * @param player the player to send
     */
    private void sendPlayerToServer(Player player) {
        if (successServer != null && !successServer.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(successServer);
            player.sendPluginMessage(mapcha, "BungeeCord", out.toByteArray());
        }
    }
}
