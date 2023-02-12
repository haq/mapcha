package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.events.CaptchaFailureEvent;
import dev.affan.mapcha.events.CaptchaSuccessEvent;
import dev.affan.mapcha.player.CaptchaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    private final Mapcha mapcha;

    public ChatHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        // checking if the player is filling the captcha
        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        // captcha success
        if (event.getMessage().equals(player.getCaptcha())) {
            Bukkit.getScheduler().runTask(mapcha, () -> Bukkit.getPluginManager().callEvent(new CaptchaSuccessEvent(player)));
        } else {
            Bukkit.getScheduler().runTask(mapcha, () -> Bukkit.getPluginManager().callEvent(new CaptchaFailureEvent(player)));
        }

        event.setCancelled(true);
    }
}
