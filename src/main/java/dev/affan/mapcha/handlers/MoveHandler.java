package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveHandler implements Listener {

    private final Mapcha mapcha;

    public MoveHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!Config.FREEZE_PLAYER || mapcha.getPlayerManager().getPlayer(event.getPlayer()) == null) {
            return;
        }
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
            event.setTo(from);
        }
    }
}
