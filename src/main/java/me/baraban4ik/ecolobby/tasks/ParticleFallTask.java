package me.baraban4ik.ecolobby.tasks;

import me.baraban4ik.ecolobby.enums.Path;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static me.baraban4ik.ecolobby.EcoLobby.config;

public class ParticleFallTask extends BukkitRunnable {
    private static final List<Player> playerList = new ArrayList<>();

    public static void add(Player player) {
        playerList.add(player);
    }

    public static void remove(Player player) {
        playerList.remove(player);
    }


    @Override
    public void run() {
        if (config.getBoolean(Path.FALL_PARTICLES.getPath())) {
            int amount = config.getInt(Path.FALL_PARTICLES_AMOUNT.getPath());
            playerList.forEach(player -> IntStream.range(0, amount).forEach(i -> spawnFallParticles(player)));
        }
    }

    private static void spawnFallParticles(Player player) {
        double radius = config.getDouble(Path.FALL_PARTICLES_RADIUS.getPath());
        Location location = player.getLocation();

        double min = -radius;

        double x = Math.random() * (radius - min) + min;
        double y = Math.random() * (radius - min) + min;
        double z = Math.random() * (radius - min) + min;

        Location newLocation = new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z);

        Particle particle = Particle.valueOf(config.getString(Path.FALL_PARTICLES_PARTICLE.getPath()).toUpperCase());
        player.spawnParticle(particle, newLocation, 0, 25.0F, 13.0F, 8.0F, 0.0F);
    }
}
