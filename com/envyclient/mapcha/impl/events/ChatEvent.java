package com.envyclient.mapcha.impl.events;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.player.CustomPlayer;
import com.envyclient.mapcha.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        CustomPlayer player = Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(event.getPlayer());

        if (player != null) {

            if (!event.getMessage().equals(player.getCaptcha())) {
                if (player.getTries() >= (Util.CAPTCHA_TRIES - 1)) {
                    Bukkit.getScheduler().runTask(Mapcha.INSTANCE, () -> player.getPlayer().kickPlayer(Util.PREFIX + " " + Util.CAPTCHA_FAIL));
                } else {
                    player.setTries(player.getTries() + 1);
                    player.getPlayer().sendMessage(Util.PREFIX + " " + Util.CAPTCHA_RETRY.replace("{TRIES}", String.valueOf(player.getTries())).replace("{MAX_TRIES}", String.valueOf(Util.CAPTCHA_TRIES)));
                }
            } else {
                player.getPlayer().sendMessage(Util.PREFIX + " " + Util.CAPTCHA_SUCCESS);
                player.resetInventory();
                Mapcha.INSTANCE.PLAYER_MANAGER.removePlayer(player);
            }
            event.setCancelled(true);

        }

    }

}
