package me.ihaq.mapcha.events;

import me.ihaq.mapcha.Mapcha;
import me.ihaq.mapcha.player.CaptchaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

import static me.ihaq.mapcha.Mapcha.Config.*;

public class PlayerEvent implements Listener {

    private Mapcha mapcha;

    public PlayerEvent(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (event.getPlayer().hasPermission(permission)) {
            return;
        }

        CaptchaPlayer captchaPlayer = new CaptchaPlayer(event.getPlayer(), genCaptcha(), mapcha)
                .cleanPlayer();

        ItemStack itemStack = new ItemStack(Material.EMPTY_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Mapcha");
        itemMeta.setLore(Collections.singletonList("Open the map to see the captcha."));
        itemStack.setItemMeta(itemMeta);

        captchaPlayer.getPlayer().getInventory().setItemInHand(itemStack);
        mapcha.getPlayerManager().addPlayer(captchaPlayer);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        CaptchaPlayer captchaPlayer = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (captchaPlayer == null) {
            return;
        }

        captchaPlayer.resetInventory();
        mapcha.getPlayerManager().removePlayer(captchaPlayer);
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(event.getPlayer());

        if (player != null) {

            if (!event.getMessage().equals(player.getCaptcha())) {
                if (player.getTries() >= (captchaTries - 1)) {
                    Bukkit.getScheduler().runTask(mapcha, () -> player.getPlayer().kickPlayer(prefix + " " + captchaFailMessage));
                } else {
                    player.setTries(player.getTries() + 1);
                    player.getPlayer().sendMessage(prefix + " " + captchaRetryMessage.replace("{CURRENT}", String.valueOf(player.getTries())).replace("{MAX}", String.valueOf(captchaTries)));
                }
            } else {
                player.getPlayer().sendMessage(prefix + " " + captchaSuccessMessage);
                player.resetInventory();
                mapcha.getPlayerManager().removePlayer(player);
            }

            event.setCancelled(true);
        }
    }

    private String genCaptcha() {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        }
        return random.toString();
    }

}
