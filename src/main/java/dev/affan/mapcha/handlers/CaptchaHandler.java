package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.events.CaptchaFailureEvent;
import dev.affan.mapcha.events.CaptchaSuccessEvent;
import dev.affan.mapcha.player.CaptchaPlayer;
import dev.affan.mapcha.tasks.KickPlayerTask;
import dev.affan.mapcha.tasks.SendPlayerToServerTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static dev.affan.mapcha.Config.*;

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
