package me.affanhaq.mapcha.player;

import me.affanhaq.mapcha.Config;
import me.affanhaq.mapcha.Mapcha;
import me.affanhaq.mapcha.tasks.KickPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

public class CaptchaPlayer {

    private final Player player;
    private final String captcha;

    private final ItemStack[] contents;
    private final ItemStack[] armour;
    private int tries;
    private final long lastTime;

    private final int kickPlayerTask;

    public CaptchaPlayer(Player player, String captcha, Mapcha mapcha) {
        this.player = player;
        this.captcha = captcha;

        // storing the players inventory & armor
        contents = player.getInventory().getContents();
        armour = player.getInventory().getArmorContents();

        tries = 0;
        lastTime = System.currentTimeMillis();

        // starting a timer to kick the player if the captcha has not been finished
        kickPlayerTask = Bukkit.getScheduler().scheduleSyncDelayedTask(
                mapcha,
                new KickPlayerTask(player),
                KickPlayerTask.delay()
        );
    }

    /**
     * Renders the captcha.
     *
     * @return the rendered captcha
     */
    public BufferedImage render() {
        String title = "Captcha";

        BufferedImage image = new BufferedImage(130, 130, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(title, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(title, g).getWidth()) / 2), 30);

        g.setFont(new Font("Arial", Font.BOLD, 10));

        String sTries = "Tries Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTries, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2), 45);
        g.setColor((Config.TRIES - tries) == 1 ? Color.RED : Color.GREEN);
        g.drawString(String.valueOf((Config.TRIES - tries)), (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTries, g).getWidth() + 2), 45);

        String sTime = "Time Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTime, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2), 55);
        g.setColor((Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime) == 1000 ? Color.RED : Color.GREEN);
        g.drawString(new SimpleDateFormat("ss").format((Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime)) + " sec", (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTime, g).getWidth() + 2), 55);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString(captcha, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(captcha, g).getWidth()) / 2), 105);

        return image;
    }

    /**
     * Gives the players items back.
     */
    public void rollbackInventory() {
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armour);
        player.updateInventory();
    }

    /**
     * Clean the players inventory.
     */
    public CaptchaPlayer cleanPlayer() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
        return this;
    }

    public void cancelKickTask() {
        Bukkit.getScheduler().cancelTask(kickPlayerTask);
    }

    public String getCaptcha() {
        return captcha;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTries() {
        return tries;
    }

    public void incrementTries() {
        this.tries++;
    }
}