package com.envyclient.mapcha.impl.events;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.player.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.List;

public class MapEvent implements Listener {

    @EventHandler
    public void onMapInitilize(MapInitializeEvent event) {

        MapView map = event.getMap();

        List<MapRenderer> old = map.getRenderers();

        map.setScale(MapView.Scale.NORMAL);
        map.getRenderers().forEach(map::removeRenderer);
        
        map.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                CustomPlayer p = Mapcha.INSTANCE.PLAYER_MANAGER.getPlayer(player);
                if (p == null) {
                    old.forEach(map::addRenderer);
                } else {
                    mapCanvas.drawImage(0, 0, p.render());
                }
            }

        });

    }
}
