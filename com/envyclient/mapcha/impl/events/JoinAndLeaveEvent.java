package com.envyclient.mapcha.impl.events;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.player.CustomPlayer;
import com.envyclient.mapcha.util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class JoinAndLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (event.getPlayer().hasPermission("mapcha.bypass"))
            return;

        CustomPlayer customPlayer = new CustomPlayer(event.getPlayer(), generateRandomString(Util.CAPTCHA_LENGTH)).cleanPlayer();

        ItemStack itemStack = new ItemStack(Material.EMPTY_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Mapcha");
        itemMeta.setLore(Collections.singletonList("Open the map to see the captcha."));
        itemStack.setItemMeta(itemMeta);

        customPlayer.getPlayer().getInventory().setItemInHand(itemStack);
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
        for (int i = 0; i < length; i++)
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        return random.toString();
    }

}
