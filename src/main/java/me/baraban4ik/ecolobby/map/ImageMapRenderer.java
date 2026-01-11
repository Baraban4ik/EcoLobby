package me.baraban4ik.ecolobby.map;

import me.baraban4ik.ecolobby.EcoLobby;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMapRenderer extends MapRenderer {

    private final BufferedImage image;
    private boolean rendered = false;

    public ImageMapRenderer(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(EcoLobby.getInstance().getDataFolder(), path));
        } catch (IOException e) {
            EcoLobby.getInstance().getLogger().warning(
                    "File not found: " + path);
        }
        this.image = img;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        if (rendered || image == null) return;

        canvas.drawImage(0, 0, image);
        rendered = true;
    }
}

