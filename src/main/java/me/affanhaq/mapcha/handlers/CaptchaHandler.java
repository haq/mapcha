package me.affanhaq.mapcha.handlers;

import me.affanhaq.mapcha.Mapcha;
import me.affanhaq.mapcha.events.CaptchaFailureEvent;
import me.affanhaq.mapcha.events.CaptchaSuccessEvent;
import me.affanhaq.mapcha.player.CaptchaPlayer;
import me.affanhaq.mapcha.tasks.KickPlayerTask;
import me.affanhaq.mapcha.tasks.SendPlayerToServerTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.affanhaq.mapcha.Config.*;

public class CaptchaHandler implements Listener {

    private final Mapcha mapcha;

    public CaptchaHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onCaptchaSuccess(CaptchaSuccessEvent event) {
        CaptchaPlayer player = event.getPlayer();

        // send success message to player
        player.getPlayer().sendMessage(PREFIX + " " + MESSAGE_SUCCESS);

        // give the players items back
        player.rollbackInventory();

        // added the user to the completed cache
        if (USE_CACHE) {
            mapcha.getCacheManager().add(player.getPlayer());
        }

        // cancel kick task
        player.cancelKickTask();

        mapcha.getPlayerManager().remove(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                mapcha,
                new SendPlayerToServerTask(mapcha, player.getPlayer()),
                SendPlayerToServerTask.delay()
        );
    }

    @EventHandler
    public void onCaptchaFail(CaptchaFailureEvent event) {
        CaptchaPlayer player = event.getPlayer();

        // kick the player because he's out of tries
        if (player.getTries() >= (TRIES - 1)) {

            // cancel kick task
            player.cancelKickTask();

            // kick the player
            Bukkit.getScheduler().runTask(mapcha, new KickPlayerTask(player.getPlayer()));

        } else {

            // increment the players tries
            player.incrementTries();

            // notify the player of how many tries they have left
            player.getPlayer().sendMessage(
                    PREFIX + " " + MESSAGE_RETRY.replace("{CURRENT}", String.valueOf(player.getTries())).replace("{MAX}", String.valueOf(TRIES))
            );
        }
    }

}
