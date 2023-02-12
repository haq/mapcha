package dev.affan.mapcha.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CacheManager {

    private final Set<UUID> cache = new HashSet<>();

    public void add(Player player) {
        cache.add(player.getUniqueId());
    }

    public boolean isCached(Player player) {
        return cache.contains(player.getUniqueId());
    }

}
