package com.envyclient.mapcha.impl.events;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.player.CustomPlayer;
import com.envyclient.mapcha.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JoinAndLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        CustomPlayer customPlayer = new CustomPlayer(event.getPlayer(), generateRandomString(Util.CAPTCHA_LENGTH), event.getPlayer().getInventory().getContents(), event.getPlayer().getInventory().getArmorContents()).cleanPlayer();

        customPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.EMPTY_MAP));
        Mapcha.INSTANCE.PLAYER_MANAGER.addPlayer(customPlayer);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        CustomPlayer customPlayer = Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(event.getPlayer());

        if (customPlayer == null)
            return;

        customPlayer.resetInventory();

        Mapcha.INSTANCE.PLAYER_MANAGER.removePlayer(customPlayer);

    }

    private String generateRandomString(int length) {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < length; i++) {
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        }
        return random.toString();
    }

}
