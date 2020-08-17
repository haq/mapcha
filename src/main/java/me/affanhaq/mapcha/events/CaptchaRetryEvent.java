package me.affanhaq.mapcha.events;

import me.affanhaq.mapcha.player.CaptchaPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CaptchaRetryEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CaptchaPlayer player;

    public CaptchaRetryEvent(CaptchaPlayer player) {
        this.player = player;
    }

    public CaptchaPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}
