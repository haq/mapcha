package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.events.CaptchaFailureEvent;
import dev.affan.mapcha.events.CaptchaSuccessEvent;
import dev.affan.mapcha.player.CaptchaPlayer;
import dev.affan.mapcha.tasks.KickPlayerTask;
import dev.affan.mapcha.tasks.SendPlayerToServerTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CaptchaHandler implements Listener {

    private final Mapcha mapcha;

    public CaptchaHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onCaptchaSuccess(CaptchaSuccessEvent event) {
        CaptchaPlayer player = event.getPlayer();

        // send success message to player
        player.getPlayer().sendMessage(Config.PREFIX + " " + Config.MESSAGE_SUCCESS);

        // give the players items back
        player.rollbackInventory();

        // add the user to the completed cache
        mapcha.getCacheManager().add(player.getPlayer());

        // cancel kick task
        player.cancelKickTask();

        // remove the player from the captcha system
        mapcha.getPlayerManager().remove(player);

        // schedule task to send player to specified server
        new SendPlayerToServerTask(
                mapcha, player.getPlayer()
        ).runTaskLater(mapcha, SendPlayerToServerTask.delay());
    }

    @EventHandler
    public void onCaptchaFail(CaptchaFailureEvent event) {
        CaptchaPlayer player = event.getPlayer();

        // kick the player because he's out of tries
        if (player.getTries() >= (Config.TRIES - 1)) {

            // cancel kick task
            player.cancelKickTask();

            // kick the player
            new KickPlayerTask(player.getPlayer()).runTask(mapcha);

        } else {

            // increment the players tries
            player.incrementTries();

            // notify the player of how many tries they have left
            player.getPlayer().sendMessage(
                    Config.PREFIX + " " + Config.MESSAGE_RETRY.replace("{CURRENT}", String.valueOf(player.getTries())).replace("{MAX}", String.valueOf(Config.TRIES))
            );
        }
    }

}
