package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.data.SpawnConfig;
import me.baraban4ik.ecolobby.enums.types.SpawnType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportSpawnAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        SpawnConfig spawnConfig = ConfigManager.getSpawnConfig();

        Location spawn = spawnConfig.getSpawn(SpawnType.MAIN);
        if (spawn != null) player.teleport(spawn);
    }
}