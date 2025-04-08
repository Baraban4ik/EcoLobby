package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static me.baraban4ik.ecolobby.utils.Configurations.config;

public class PlayerCommandAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        if (config.getBoolean(Path.ABILITIES_COMMANDS.getPath())) {
            List<String> allowedCommands = config.getStringList(Path.ABILITIES_COMMANDS_ALLOWED.getPath());

            if (allowedCommands.contains(action) && !(player.hasPermission("ecolobby.bypass.commands"))) {
                Bukkit.dispatchCommand(player, action);
            }
            return;
        }
        Bukkit.dispatchCommand(player, action);
    }
}