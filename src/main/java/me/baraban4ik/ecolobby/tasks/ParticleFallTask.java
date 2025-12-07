package me.baraban4ik.ecolobby.tasks;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.WorldsConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class ParticleFallTask extends BukkitRunnable {

    private final World world;
    private final Set<Player> players = new HashSet<>();

    public ParticleFallTask(World world) {
        this.world = world;
    }

    public void add(Player player) {
        if (!players.contains(player) && player.getWorld().equals(world)) {
            players.add(player);
        }
    }

    public void remove(Player player) {
        players.remove(player);
    }

    @Override
    public void run() {
        WorldsConfig worldsConfig = ConfigManager.getWorldsConfig();
        if (!worldsConfig.isAmbientEnabled(world)) return;

        int amount = worldsConfig.getAmbientAmount(world);
        double radius = worldsConfig.getAmbientRadius(world);
        Particle particle = worldsConfig.getAmbientParticle(world);

        for (Player player : players) {
            if (!player.isOnline() || !player.getWorld().equals(world)) continue;

            for (int i = 0; i < amount; i++) {
                spawnFallParticles(player, radius, particle);
            }
        }
    }

    private void spawnFallParticles(Player player, double radius, Particle particle) {
        Location location = player.getLocation();
        double min = -radius;

        double x = Math.random() * (radius - min) + min;
        double y = Math.random() * (radius - min) + min;
        double z = Math.random() * (radius - min) + min;

        Location newLocation = location.clone().add(x, y, z);
        player.spawnParticle(particle, newLocation, 0, 25.0F, 13.0F, 8.0F, 0.0F);
    }

    public void start() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().equals(world))
                .forEach(this::add);

        runTaskTimer(EcoLobby.getInstance(), 0L, 1L);
    }
}
