package dev.affan.mapcha.handlers;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.player.CaptchaPlayer;
import dev.affan.mapcha.tasks.SendPlayerToServerTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class JoinAndQuitHandler implements Listener {

    private final Mapcha mapcha;

    public JoinAndQuitHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // checking if player has permission to bypass the captcha or player has already completed the captcha before
        // by default OPs have the '*' permission so this method will return true
        if (player.hasPermission(Config.BYPASS_PERMISSION) || mapcha.getCacheManager().isCached(player)) {
            new SendPlayerToServerTask(mapcha, player).runTaskLater(mapcha, SendPlayerToServerTask.delay());
            return;
        }

        // creating a captcha player
        CaptchaPlayer captchaPlayer = new CaptchaPlayer(
                mapcha, player, genCaptcha()
        );

        // getting the map itemstack depending on the spigot version
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("LEGACY_EMPTY_MAP"));
        } catch (IllegalArgumentException e) {
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

        CaptchaPlayer player = mapcha.getPlayerManager().getPlayer(
                event.getPlayer()
        );

        if (player == null) {
            return;
        }

        // giving the player their items back
        player.rollbackInventory();

        // show hidden players
        player.showPlayers();

        // removing the player from the captcha list
        mapcha.getPlayerManager().remove(player);
    }

    /**
     * @return a random string with len 4
     */
    private static String genCaptcha() {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            random.append(charset.charAt(new Random().nextInt(charset.length() - 1)));
        }
        return random.toString();
    }
}
