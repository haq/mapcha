package me.affanhaq.mapcha.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.affanhaq.mapcha.Mapcha;
import me.affanhaq.mapcha.player.CaptchaPlayer;
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

import static me.affanhaq.mapcha.Mapcha.Config.*;

public class PlayerHandler implements Listener {

    private final Mapcha mapcha;

    public PlayerHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // player has permission to bypass the captcha
        // by default OPs have the '*' permission so this method will return true
        if (player.hasPermission(permission)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(mapcha, () -> sendPlayerToServer(player), 15);
            return;
        }

        // creating a captcha player
        CaptchaPlayer captchaPlayer = new CaptchaPlayer(player, genCaptcha(), mapcha)
                .cleanPlayer();

        // making a map for the player
        String version = Bukkit.getVersion();
        ItemStack itemStack;
        if (version.contains("1.13") || version.contains("1.14") || version.contains("1.15") || version.contains("1.16")) {
            itemStack = new ItemStack(Material.valueOf("LEGACY_EMPTY_MAP"));
        } else {
            itemStack = new ItemStack(Material.valueOf("EMPTY_MAP"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Mapcha");
        itemMeta.setLore(Collections.singletonList("Open the map to see the captcha."));
        itemStack.setItemMeta(itemMeta);

        // giving the player the map and adding them to the captcha array
        captchaPlayer.getPlayer().getInventory().setItemInHand(itemStack);
        mapcha.getPlayerManager().addPlayer(captchaPlayer);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {

        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        // giving the player their items back
        player.resetInventory();
        mapcha.getPlayerManager().removePlayer(player);
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        // checking the the player is filling the captcha
        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        // TODO: use custom events
        //ExampleEvent exampleEvent = new ExampleEvent("Msrules123"); // Initialize your Event
        //Bukkit.getPluginManager().callEvent(exampleEvent);

        // captcha success
        if (event.getMessage().equals(player.getCaptcha())) {
            player.getPlayer().sendMessage(prefix + " " + captchaSuccessMessage);
            player.resetInventory();
            mapcha.getPlayerManager().removePlayer(player);
            sendPlayerToServer(player.getPlayer());
        } else {
            if (player.getTries() >= (captchaTries - 1)) { // kicking the player because he's out of tries
                Bukkit.getScheduler().runTask(mapcha, () -> player.getPlayer().kickPlayer(prefix + " " + captchaFailMessage));
            } else { // telling the player to try again
                player.setTries(player.getTries() + 1);
                player.getPlayer().sendMessage(prefix + " " + captchaRetryMessage.replace("{CURRENT}", String.valueOf(player.getTries())).replace("{MAX}", String.valueOf(captchaTries)));
            }
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
        for (String command : commands) {
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

    /**
     * Sends a player to a connected server after the captcha is completed.
     *
     * @param player the player to send
     */
    private void sendPlayerToServer(Player player) {
        if (successServer != null && !successServer.isEmpty()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(successServer);
            player.sendPluginMessage(mapcha, "BungeeCord", out.toByteArray());
        }
    }

}