package dev.affan.mapcha.player;

import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.Config;
import dev.affan.mapcha.tasks.KickPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Random;

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
        Color background = Config.INVERT_COLOR ? Color.WHITE : Color.BLACK;
        Color foreground = Config.INVERT_COLOR ? Color.BLACK : Color.WHITE;

        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(background);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        String title = Config.TITLE;
        g.setColor(foreground);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(title, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(title, g).getWidth()) / 2), 30);

        g.setFont(new Font("Arial", Font.BOLD, 10));

        String sTries = "Tries Left: ";
        g.setColor(foreground);
        g.drawString(sTries, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2), 45);
        g.setColor((Config.TRIES - tries) == 1 ? Color.RED : Color.GREEN);
        g.drawString(String.valueOf((Config.TRIES - tries)), (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTries, g).getWidth() + 2), 45);

        String sTime = "Time Left: ";
        g.setColor(foreground);
        g.drawString(sTime, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2), 55);
        g.setColor((Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime) == 1000 ? Color.RED : Color.GREEN);
        g.drawString(new SimpleDateFormat("ss").format((Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime)) + " sec", (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTime, g).getWidth() + 2), 55);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(foreground);
        g.drawString(captcha, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(captcha, g).getWidth()) / 2), 105);

        Random random = new Random();
        if (Config.POINTS) {
            for (int i = 0; i < 100; i++) {
                int x = random.nextInt(image.getWidth());
                int y = random.nextInt(image.getHeight() / 2) + image.getHeight() / 2;
                g.drawOval(x, y, 1, 1);
            }
        }

        if (Config.LINES) {
            for (int i = 0; i < 10; i++) {
                int x1 = random.nextInt(image.getWidth());
                int y1 = random.nextInt(image.getHeight() / 2) + image.getHeight() / 2;
                int x2 = random.nextInt(image.getWidth());
                int y2 = random.nextInt(image.getHeight() / 2) + image.getHeight() / 2;
                g.drawLine(x1 % image.getWidth(), y1, x2, y2);
            }
        }

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
