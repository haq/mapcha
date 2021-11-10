package me.affanhaq.mapcha.handlers;

import me.affanhaq.mapcha.Mapcha;
import me.affanhaq.mapcha.events.CaptchaFailureEvent;
import me.affanhaq.mapcha.events.CaptchaSuccessEvent;
import me.affanhaq.mapcha.player.CaptchaPlayer;
import me.affanhaq.mapcha.tasks.SendPlayerToServerTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

import static me.affanhaq.mapcha.Config.*;

public class PlayerHandler implements Listener {

    private final Mapcha mapcha;

    public PlayerHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // checking if player has permission to bypass the captcha or player has already completed the captcha before
        // by default OPs have the '*' permission so this method will return true
        if (player.hasPermission(BYPASS_PERMISSION) || (USE_CACHE && mapcha.getCacheManager().isCached(player))) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    mapcha,
                    new SendPlayerToServerTask(mapcha, player),
                    SendPlayerToServerTask.delay()
            );
            return;
        }

        // creating a captcha player
        CaptchaPlayer captchaPlayer = new CaptchaPlayer(
                player,
                genCaptcha(),
                mapcha
        ).cleanPlayer();

        // getting the map itemstack depending ont he spigot version
        String version = Bukkit.getVersion();
        ItemStack itemStack;
        if (version.contains("1.13") ||
                version.contains("1.14") ||
                version.contains("1.15") ||
                version.contains("1.16") ||
                version.contains("1.17")) {
            itemStack = new ItemStack(Material.valueOf("LEGACY_EMPTY_MAP"));
        } else {
            itemStack = new ItemStack(Material.valueOf("EMPTY_MAP"));
        }

        // setting the item metadata
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Mapcha");
        itemMeta.setLore(Collections.singletonList("Open the map to see the captcha."));
        itemStack.setItemMeta(itemMeta);

        // giving the player the map and adding them to the captcha array
        captchaPlayer.getPlayer().getInventory().setItemInHand(itemStack);
        mapcha.getPlayerManager().add(captchaPlayer);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {

        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        // giving the player their items back
        player.rollbackInventory();

        // removing the player from the captcha list
        mapcha.getPlayerManager().remove(player);
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

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        event.setCancelled(
                mapcha.getPlayerManager().getPlayer(event.getPlayer()) != null && !validCommand(event.getMessage())
        );
    }

    /**
     * Checks if the message contains a command.
     *
     * @param message the message to check commands for
     * @return whether the message contains a command or not
     */
    private boolean validCommand(String message) {
        for (String command : ALLOWED_COMMANDS) {
            if (message.contains(command)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return a random string with len 4
     */
    private String genCaptcha() {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        }
        return random.toString();
    }

}