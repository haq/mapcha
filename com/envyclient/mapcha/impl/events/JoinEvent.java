package com.envyclient.mapcha.impl.events;

import com.envyclient.mapcha.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Util.MATH && Util.TEXT) {
            Player player = event.getPlayer();
            player.getInventory().setItem(36, new ItemStack(Material.MAP));
        }
    }

}
