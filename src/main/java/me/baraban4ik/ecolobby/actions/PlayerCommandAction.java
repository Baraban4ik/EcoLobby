package me.baraban4ik.ecolobby.actions;


import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.PlayerConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;


public class PlayerCommandAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        PlayerConfig playerConfig = ConfigManager.getPlayerConfig();

        if (playerConfig.isDisableCommands()) {
            List<String> allowedCommands = playerConfig.getDisableCommandsAllowed();

            if (allowedCommands.contains(action) || Permission.BYPASS_COMMANDS.has(player)) {
                Bukkit.dispatchCommand(player, action);
            }
            return;
        }
        Bukkit.dispatchCommand(player, action);
    }
}