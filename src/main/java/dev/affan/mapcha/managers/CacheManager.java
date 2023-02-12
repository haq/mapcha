package dev.affan.mapcha.managers;

import dev.affan.mapcha.Config;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CacheManager {

    private final Set<UUID> cache = new HashSet<>();

    public void add(Player player) {
        if (!Config.USE_CACHE) {
            return;
        }
        cache.add(player.getUniqueId());
    }

    public boolean isCached(Player player) {
        return Config.USE_CACHE && cache.contains(player.getUniqueId());
    }
}
