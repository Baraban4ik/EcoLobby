package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.enums.SpawnType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.baraban4ik.ecolobby.EcoLobby.*;
import static me.baraban4ik.ecolobby.utils.Configurations.spawnConfig;

public class SpawnManager {
    public static void setSpawn(@NotNull Player player, SpawnType type) {
        String typeName = type.name().toUpperCase();
        Location location = player.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        float pitch = location.getPitch();
        float yaw = location.getYaw();

        String worldName = player.getWorld().getName();

        spawnConfig.set(typeName + ".x", x);
        spawnConfig.set(typeName + ".y", y);
        spawnConfig.set(typeName + ".z", z);

        spawnConfig.set(typeName + ".pitch", pitch);
        spawnConfig.set(typeName + ".yaw", yaw);
        spawnConfig.set(typeName + ".world", worldName);

        getConfigurations().save(spawnConfig, "spawn");
    }

    public static Location getSpawn(SpawnType type) {
        String typeName = type.name().toUpperCase();

        if (spawnConfig.get(typeName + ".x") == null && spawnConfig.get(typeName + ".y") == null) return null;

        double x = spawnConfig.getDouble(typeName + ".x");
        double y = spawnConfig.getDouble(typeName + ".y");
        double z = spawnConfig.getDouble(typeName + ".z");

        double pitch = spawnConfig.getDouble(typeName + ".pitch");
        double yaw = spawnConfig.getDouble(typeName + ".yaw");

        World world = Bukkit.getWorld(spawnConfig.getString(typeName + ".world", "world"));

        return new Location(world, x, y, z, (float)yaw, (float)pitch);
    }
}
