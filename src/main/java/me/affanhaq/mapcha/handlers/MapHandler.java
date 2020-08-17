package me.affanhaq.mapcha.handlers;

import me.affanhaq.mapcha.player.CaptchaPlayer;
import me.affanhaq.mapcha.Mapcha;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MapHandler implements Listener {

    private final Mapcha mapcha;

    public MapHandler(Mapcha mapcha) {
        this.mapcha = mapcha;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMapInitilize(MapInitializeEvent event) {

        MapView map = event.getMap();
        List<MapRenderer> old = map.getRenderers();

        map.setScale(MapView.Scale.NORMAL);
        map.getRenderers().forEach(map::removeRenderer);

        map.addRenderer(new MapRenderer() {
            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                CaptchaPlayer p = mapcha.getPlayerManager().getPlayer(player);
                if (p == null) {
                    old.forEach(map::addRenderer);
                } else {
                    mapCanvas.drawImage(0, 0, p.render());
                }
            }
        });
    }
}
