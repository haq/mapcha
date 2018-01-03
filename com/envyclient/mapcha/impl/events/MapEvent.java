package com.envyclient.mapcha.impl.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class MapEvent implements Listener {

    @EventHandler
    public void onMapInitilize(MapInitializeEvent event) {

        MapView map = event.getMap();

        map.getRenderers().forEach(map::removeRenderer);



        map.addRenderer(new MapRenderer() {

            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                mapCanvas.drawText(5, 5, MinecraftFont.Font, "This is a test.");
            }

        });


    }
}
