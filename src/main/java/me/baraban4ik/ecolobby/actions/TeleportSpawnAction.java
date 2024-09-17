package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportSpawnAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);
        if (spawn != null) player.teleport(spawn);
    }
}