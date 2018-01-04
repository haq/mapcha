package com.envyclient.mapcha.api.player;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

public class CustomPlayer {

    private Player player;
    private String text;
    private ItemStack[] contents;
    private ItemStack[] armour;
    private int tries;
    private long lastTime;

    public CustomPlayer(Player player, String text, ItemStack[] contents, ItemStack[] armour) {
        this.player = player;
        this.text = text;
        this.contents = contents;
        this.armour = armour;

        lastTime = System.currentTimeMillis();

        tries = 0;

        player.getServer().getScheduler().scheduleSyncDelayedTask(Mapcha.INSTANCE, () -> {
            if (Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(player) != null)
                player.getPlayer().kickPlayer(Util.PREFIX + " " + Util.CAPTCHA_FAIL);
        }, Util.CAPTCHA_TIME_LIMIT * 20);

    }

    public BufferedImage render() {


        BufferedImage image;

        Graphics g = (image = new BufferedImage(130, 130, BufferedImage.TYPE_INT_RGB)).getGraphics();

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(Util.NAME, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(Util.NAME, g).getWidth()) / 2), 30);

        g.setFont(new Font("Arial", Font.BOLD, 10));

        String sTries = "Tries Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTries, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2), 45);
        g.setColor((Util.CAPTCHA_TRIES - tries) == 1 ? Color.RED : Color.GREEN);
        g.drawString(String.valueOf((Util.CAPTCHA_TRIES - tries)), (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTries, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTries, g).getWidth() + 2), 45);

        String sTime = "Time Left: ";
        g.setColor(Color.WHITE);
        g.drawString(sTime, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2), 55);
        g.setColor((Util.CAPTCHA_TIME_LIMIT * 1000) - (System.currentTimeMillis() - lastTime) == 1000 ? Color.RED : Color.GREEN);
        g.drawString(new SimpleDateFormat("ss").format((Util.CAPTCHA_TIME_LIMIT * 1000) - (System.currentTimeMillis() - lastTime)) + " sec", (int) (((image.getWidth() - g.getFontMetrics().getStringBounds(sTime, g).getWidth()) / 2) + g.getFontMetrics().getStringBounds(sTime, g).getWidth() + 2), 55);


        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString(text, (int) ((image.getWidth() - g.getFontMetrics().getStringBounds(text, g).getWidth()) / 2), 105);

        return image;

    }


    public void resetInventory() {
        getPlayer().getInventory().setContents(contents);
        getPlayer().getInventory().setArmorContents(armour);
        getPlayer().updateInventory();
    }

    public CustomPlayer cleanPlayer() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        getPlayer().updateInventory();
        tries = 0;
        return this;
    }

    public String getText() {
        return text;
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
