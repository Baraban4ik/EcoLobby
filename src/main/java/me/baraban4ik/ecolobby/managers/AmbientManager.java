package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.tasks.ParticleFallTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class AmbientManager implements PlayerAware {
    private final Map<World, ParticleFallTask> tasks = new HashMap<>();

    @Override
    public void start() {
        Bukkit.getWorlds().forEach(world -> {
            if (ConfigManager.getWorldsConfig().isAmbientEnabled(world)) {
                ParticleFallTask task = new ParticleFallTask(world);
                task.start();
                tasks.put(world, task);
            }
        });
    }

    @Override
    public void stop() {
        tasks.values().forEach(BukkitRunnable::cancel);
        tasks.clear();
    }

    public void addPlayer(Player player) {
        ParticleFallTask task = tasks.get(player.getWorld());
        if (task != null) {
            task.add(player);
        }
    }
    @Override
    public void removePlayer(Player player) {
        ParticleFallTask task = tasks.get(player.getWorld());
        if (task != null) {
            task.remove(player);
        }
    }
    @Override
    public void update(Player player) {
        removePlayer(player);
        addPlayer(player);
    }

}
