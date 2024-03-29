package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemHandler implements Listener {

    private final Mapcha mapcha;

    public DropItemHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(
                !Config.DROP_ITEMS && mapcha.getPlayerManager().getPlayer(event.getPlayer()) != null
        );
    }
}
