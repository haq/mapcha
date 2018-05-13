package me.ihaq.mapcha.events;

import me.ihaq.mapcha.Mapcha;
import me.ihaq.mapcha.player.CaptchaPlayer;
import me.ihaq.mapcha.util.Util;
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

public class PlayerEvent implements Listener {

    private Mapcha mapcha;

    public PlayerEvent(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (event.getPlayer().hasPermission("mapcha.bypass"))
            return;

        CaptchaPlayer captchaPlayer = new CaptchaPlayer(event.getPlayer(), genCaptcha()).cleanPlayer();

        ItemStack itemStack = new ItemStack(Material.EMPTY_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Mapcha");
        itemMeta.setLore(Collections.singletonList("Open the map to see the captcha."));
        itemStack.setItemMeta(itemMeta);

        captchaPlayer.getPlayer().getInventory().setItemInHand(itemStack);
        Mapcha.INSTANCE.PLAYER_MANAGER.addPlayer(captchaPlayer);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        CaptchaPlayer captchaPlayer = Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(event.getPlayer());

        if (captchaPlayer == null)
            return;

        captchaPlayer.resetInventory();

        Mapcha.INSTANCE.PLAYER_MANAGER.removePlayer(captchaPlayer);

    }

    private String genCaptcha() {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 4; i++)
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        return random.toString();
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        CaptchaPlayer player = Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(event.getPlayer());

        if (player != null) {

            if (!event.getMessage().equals(player.getCaptcha())) {
                if (player.getTries() >= (Util.CAPTCHA_TRIES - 1)) {
                    Bukkit.getScheduler().runTask(Mapcha.INSTANCE, () -> player.getPlayer().kickPlayer(Util.PREFIX + " " + Util.CAPTCHA_FAIL));
                } else {
                    player.setTries(player.getTries() + 1);
                    player.getPlayer().sendMessage(Util.PREFIX + " " + Util.CAPTCHA_RETRY.replace("{TRIES}", String.valueOf(player.getTries())).replace("{MAX_TRIES}", String.valueOf(Util.CAPTCHA_TRIES)));
                }
            } else {
                player.getPlayer().sendMessage(Util.PREFIX + " " + Util.CAPTCHA_SUCCESS);
                player.resetInventory();
                Mapcha.INSTANCE.PLAYER_MANAGER.removePlayer(player);
            }
            event.setCancelled(true);

        }

    }

}
