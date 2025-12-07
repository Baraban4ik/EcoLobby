package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.PlayerConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.List;

public class CommandListener implements Listener {

    private final PlayerConfig playerConfig = ConfigManager.getPlayerConfig();

    @EventHandler()
    public void onCommands(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (Permission.BYPASS_COMMANDS.has(player)) return;

        if (playerConfig.isDisableCommands()) {
            String[] command = event.getMessage().replace("/", "").split(" ");
            List<String> allowedCommands = playerConfig.getDisableCommandsAllowed();

            if (!allowedCommands.contains(command[0])) {
                ConfigManager.getMessagesConfig().getDenyCommands().send(player);
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onCommandTab(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();

        if (Permission.BYPASS_COMMANDS.has(player)) return;

        if (playerConfig.isDisableCommands()) {
            List<String> allowedCommands = playerConfig.getDisableCommandsAllowed();

            if (!playerConfig.isDisableCommandsTabComplete()) {
                event.getCommands().removeIf(command -> !allowedCommands.contains(command));
            }
        }
    }
}
