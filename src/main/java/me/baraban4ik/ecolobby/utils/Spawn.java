package me.baraban4ik.ecolobby.utils;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.baraban4ik.ecolobby.EcoLobby.instance;
import static me.baraban4ik.ecolobby.EcoLobby.spawn;

public class Spawn {
    public static void setSpawn(@NotNull Player player, String type) {
        Location location = player.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        float pitch = location.getPitch();
        float yaw = location.getYaw();

        String worldName = player.getWorld().getName();

        spawn.set(type + ".x", x);
        spawn.set(type + ".y", y);
        spawn.set(type + ".z", z);

        spawn.set(type + ".pitch", pitch);
        spawn.set(type + ".yaw", yaw);
        spawn.set(type + ".world", worldName);

        instance.getFiles().save(spawn, "spawn.yml");
    }

    public static Location getSpawn(String type) {
        if (spawn.get(type + ".x") == null && spawn.get(type + ".y") == null) return null;

        double x = spawn.getDouble(type + ".x");
        double y = spawn.getDouble(type + ".y");
        double z = spawn.getDouble(type + ".z");

        double pitch = spawn.getDouble(type + ".pitch");
        double yaw = spawn.getDouble(type + ".yaw");

        World world = Bukkit.getWorld(spawn.getString(type + ".world", "world"));

        return new Location(world, x, y, z, (float)yaw, (float)pitch);
    }
}
