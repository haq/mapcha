package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandHandler implements Listener {

    private final Mapcha mapcha;

    public CommandHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(mapcha.getPlayerManager().getPlayer(event.getPlayer()) != null && !validCommand(event.getMessage()));
    }

    /**
     * Checks if the message contains a command.
     *
     * @param message the message to check commands for
     * @return whether the message contains a command or not
     */
    private static boolean validCommand(String message) {
        for (String command : Config.ALLOWED_COMMANDS) {
            if (message.contains(command)) {
                return true;
            }
        }
        return false;
    }
}
