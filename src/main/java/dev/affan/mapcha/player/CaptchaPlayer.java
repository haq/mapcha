package dev.affan.mapcha.player;

import dev.affan.mapcha.Config;
import dev.affan.mapcha.Mapcha;
import dev.affan.mapcha.tasks.KickPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CaptchaPlayer {

    private final Mapcha mapcha;
    private final Player player;
    private final String captcha;

    private final ItemStack[] contents;
    private final ItemStack[] armour;
    private int tries;
    private final long lastTime;

    private final BukkitTask kickPlayerTask;

    private final BufferedImage image;
    private final Graphics graphics;

    public CaptchaPlayer(Mapcha mapcha, Player player, String captcha) {
        this.mapcha = mapcha;
        this.player = player;
        this.captcha = captcha;

        // storing the players inventory & armor
        contents = player.getInventory().getContents();
        armour = player.getInventory().getArmorContents();

        tries = 0;
        lastTime = System.currentTimeMillis();

        // starting a timer to kick the player if the captcha has not been finished
        kickPlayerTask = new KickPlayerTask(player).runTaskLater(mapcha, KickPlayerTask.delay());

        // create image to be drawn
        image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();

        // clean the player
        cleanPlayer();

        // hide other players
        hidePlayers();
    }

    /**
     * Renders the captcha.
     *
     * @return the rendered captcha
     */
    public BufferedImage render() {
        Color background = Config.INVERT_COLOR ? Color.WHITE : Color.BLACK;
        Color foreground = Config.INVERT_COLOR ? Color.BLACK : Color.WHITE;

        // set background color
        graphics.setColor(background);

        // draw the background
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        //
        drawTitle(foreground);

        //
        drawTries(foreground);
        drawTime(foreground);

        //
        drawCaptcha(foreground);

        //
        drawPoints(foreground, new Random());
        drawLines(foreground, new Random());

        return image;
    }

    private void drawTitle(Color color) {
        String sTitle = Config.TITLE;

        //
        graphics.setFont(
                new Font(Config.FONT, Font.BOLD, 30)
        );

        //
        graphics.setColor(color);

        //
        graphics.drawString(
                sTitle,
                (int) ((image.getWidth() - graphics.getFontMetrics().getStringBounds(sTitle, graphics).getWidth()) / 2),
                30
        );
    }

    private void drawTries(Color color) {
        String sTries = "Tries Left: ";

        graphics.setFont(
                new Font(Config.FONT, Font.BOLD, 10)
        );

        //
        graphics.setColor(color);

        //
        graphics.drawString(
                sTries,
                (int) ((image.getWidth() - graphics.getFontMetrics().getStringBounds(sTries, graphics).getWidth()) / 2),
                45
        );

        // set the color depending on how many tries are left
        graphics.setColor(
                (Config.TRIES - tries) == 1 ? Color.RED : Color.GREEN
        );

        // draw the amount of tries left
        graphics.drawString(
                String.valueOf((Config.TRIES - tries)),
                (int) (((image.getWidth() - graphics.getFontMetrics().getStringBounds(sTries, graphics).getWidth()) / 2) + graphics.getFontMetrics().getStringBounds(sTries, graphics).getWidth() + 2),
                45
        );
    }

    private void drawTime(Color color) {
        String sTime = "Time Left: ";

        graphics.setFont(
                new Font(Config.FONT, Font.BOLD, 10)
        );

        //
        graphics.setColor(color);

        //
        graphics.drawString(
                sTime,
                (int) ((image.getWidth() - graphics.getFontMetrics().getStringBounds(sTime, graphics).getWidth()) / 2),
                55
        );

        //
        graphics.setColor(
                (Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime) == 1000 ? Color.RED : Color.GREEN
        );

        //
        graphics.drawString(
                new SimpleDateFormat("ss").format((Config.TIME_LIMIT * 1000L) - (System.currentTimeMillis() - lastTime)) + " sec",
                (int) (((image.getWidth() - graphics.getFontMetrics().getStringBounds(sTime, graphics).getWidth()) / 2) + graphics.getFontMetrics().getStringBounds(sTime, graphics).getWidth() + 2),
                55
        );
    }

    private void drawCaptcha(Color color) {
        // set captcha font
        graphics.setFont(
                new Font(Config.FONT, Font.BOLD, 40)
        );

        // set captcha font color
        graphics.setColor(color);

        // draw captcha
        graphics.drawString(
                captcha,
                (int) ((image.getWidth() - graphics.getFontMetrics().getStringBounds(captcha, graphics).getWidth()) / 2),
                105
        );
    }

    private void drawPoints(Color color, Random random) {
        if (!Config.POINTS) {
            return;
        }

        // set the color for the points
        graphics.setColor(color);

        for (int i = 0; i < 100; i++) {
            graphics.drawOval(
                    random.nextInt(image.getWidth()),
                    random.nextInt(image.getHeight() / 2) + image.getHeight() / 2,
                    1,
                    1
            );
        }
    }

    private void drawLines(Color color, Random random) {
        if (!Config.LINES) {
            return;
        }

        // set the color for the lines
        graphics.setColor(color);

        for (int i = 0; i < 10; i++) {
            graphics.drawLine(
                    random.nextInt(image.getWidth()) % image.getWidth(),
                    random.nextInt(image.getHeight() / 2) + image.getHeight() / 2,
                    random.nextInt(image.getWidth()),
                    random.nextInt(image.getHeight() / 2) + image.getHeight() / 2
            );
        }
    }

    /**
     * Clean the players inventory.
     */
    public void cleanPlayer() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
    }

    /**
     * Gives the players items back.
     */
    public void rollbackInventory() {
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armour);
        player.updateInventory();
    }

    public void hidePlayers() {
        if (!Config.HIDE_PLAYERS) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> this.player.hidePlayer(mapcha, player));
        Bukkit.getOnlinePlayers().forEach(player -> player.hidePlayer(mapcha, this.player));
    }

    public void showPlayers() {
        if (!Config.HIDE_PLAYERS) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> this.player.showPlayer(mapcha, player));
        Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(mapcha, this.player));
    }

    public void cancelKickTask() {
        kickPlayerTask.cancel();
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
        tries++;
    }
}
