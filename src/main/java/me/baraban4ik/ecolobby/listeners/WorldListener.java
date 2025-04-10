package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.managers.ActionManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

import static me.baraban4ik.ecolobby.utils.Configurations.config;

public class WorldListener implements Listener {
    @EventHandler (priority = EventPriority.LOWEST)
    public void onJumpVoid(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        double height = config.getDouble(Path.VOID_HEIGHT.getPath());
        List<String> actions = config.getStringList(Path.VOID_ACTIONS.getPath());

        if (config.getBoolean(Path.VOID.getPath())) {
            if (player.getLocation().getY() <= height) {
                player.getPassengers().stream().filter(entity -> entity.getType() == EntityType.AREA_EFFECT_CLOUD
                        || entity.getType() == EntityType.PLAYER).forEach(player::removePassenger);

                ActionManager.execute(player, actions);
            }
        }
    }
}

