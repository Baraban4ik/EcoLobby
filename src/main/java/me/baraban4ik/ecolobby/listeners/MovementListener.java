package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.WorldsConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.managers.ActionManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class MovementListener implements Listener {

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Permission.BYPASS_MOVEMENTS.has(player)) return;

        if (ConfigManager.getPlayerConfig().isDisableMovements()) {
            Location from = event.getFrom().clone();
            Location to = event.getTo();

            from.setYaw(to.getYaw());
            from.setPitch(to.getPitch());

            if (!from.equals(to)) event.setCancelled(true);
        }
    }


    @EventHandler (priority = EventPriority.LOWEST)
    public void onJumpVoid(PlayerMoveEvent event) {
        WorldsConfig worldsConfig = ConfigManager.getWorldsConfig();

        Player player = event.getPlayer();
        World world = player.getWorld();

        if (worldsConfig.isVoidEnabled(world)) {
            double height = worldsConfig.getVoidHeight(world);
            List<String> actions = worldsConfig.getVoidActions(world);

            if (player.getLocation().getY() <= height) {
                player.getPassengers().stream().filter(entity -> entity.getType() == EntityType.AREA_EFFECT_CLOUD
                        || entity.getType() == EntityType.PLAYER).forEach(player::removePassenger);

                ActionManager.execute(player, actions);
            }
        }
    }

}
