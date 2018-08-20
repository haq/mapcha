package me.ihaq.mapcha.player;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CaptchaPlayerManager {

    private List<CaptchaPlayer> playerList = new ArrayList<>();

    public void addPlayer(CaptchaPlayer player) {
        playerList.add(player);
    }

    public void removePlayer(CaptchaPlayer player) {
        playerList.remove(player);
    }

    @Nullable
    public CaptchaPlayer getPlayer(Player player) {
        return playerList.stream()
                .filter(p -> p.getPlayer().getUniqueId() == player.getPlayer().getUniqueId())
                .findFirst().orElse(null);
    }
}