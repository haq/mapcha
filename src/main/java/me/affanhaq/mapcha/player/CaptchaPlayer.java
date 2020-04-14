package me.affanhaq.mapcha.player;

import me.affanhaq.mapcha.Mapcha;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

import static me.affanhaq.mapcha.Mapcha.Config.*;

public class CaptchaPlayer {

    private final Player player;
    private final String captcha;

    private final ItemStack[] contents;
    private final ItemStack[] armour;
    private int tries;
    private final long lastTime;

    /**
     * @param player  the player
     * @param captcha the captcha string for the player
     * @param mapcha  JavaPlugin
     */
    public CaptchaPlayer(Player player, String captcha, Mapcha mapcha) {
        this.player = player;
        this.captcha = captcha;

        // getting the players
        contents = player.getInventory().getContents();
        armour = player.getInventory().getArmorContents();

        lastTime = System.currentTimeMillis();
        tries = 0;

        // starting a timer to kick the player if the captcha has not been finished
        player.getServer().getScheduler().scheduleSyncDelayedTask(mapcha, () -> {
            if (mapcha.getPlayerManager().getPlayer(player) != null) {
                player.getPlayer().kickPlayer(prefix + " " + captchaFailMessage);
            }
        }, captchaTimeLimit * 20);
    }

    /**
     * Renders the captcha.
     *
     * @return the rendered captcha
     */
    public BufferedImage render() {

        BufferedImage image = new BufferedImage(130, 130, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Mapcha", (int) ((image.getWidth() - g.getFontMetrics().getStringBounds("Mapcha", g).getWidth()) / 2), 30);

        g.setFont(new Font("Arial", Font.BOLD, 10));

        String sTries = "Tries Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTries, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2), 45);
        g.setColor((captchaTries - tries) == 1 ? Color.RED : Color.GREEN);
        g.drawString(String.valueOf((captchaTries - tries)), (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTries, g).getWidth() + 2), 45);

        String sTime = "Time Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTime, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2), 55);
        g.setColor((captchaTimeLimit * 1000) - (System.currentTimeMillis() - lastTime) == 1000 ? Color.RED : Color.GREEN);
        g.drawString(new SimpleDateFormat("ss").format((captchaTimeLimit * 1000) - (System.currentTimeMillis() - lastTime)) + " sec", (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTime, g).getWidth() + 2), 55);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString(captcha, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(captcha, g).getWidth()) / 2), 105);

        return image;
    }

    /**
     * Gives the players items back.
     */
    public void resetInventory() {
        getPlayer().getInventory().setContents(contents);
        getPlayer().getInventory().setArmorContents(armour);
        getPlayer().updateInventory();
    }

    public CaptchaPlayer cleanPlayer() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        getPlayer().updateInventory();
        tries = 0;
        return this;
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

    public void setTries(int tries) {
        this.tries = tries;
    }

}