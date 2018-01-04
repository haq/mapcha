package com.envyclient.mapcha.api.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomPlayer {

    private Player player;
    private String text;
    private ItemStack[] contents;
    private ItemStack[] armour;

    private BufferedImage image;
    private int tries;

    public CustomPlayer(Player player, String text, ItemStack[] contents, ItemStack[] armour) {
        this.player = player;
        this.text = text;
        this.contents = contents;
        this.armour = armour;

        tries = 0;

        image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Century Gothic", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString(text, 10, 70);

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

    public BufferedImage getImage() {
        return image;
    }

}
