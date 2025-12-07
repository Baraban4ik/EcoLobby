package me.baraban4ik.ecolobby.commands.spawn;

import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.config.ConfigManager;

import me.baraban4ik.ecolobby.config.files.MessagesConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.types.SpawnType;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SpawnCommand extends AbstractCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (isNotPlayer(sender)) return;
        Player player = (Player) sender;

        if (Permission.COMMAND_SPAWN.has(player, true)) {
            MessagesConfig messages = ConfigManager.getMessagesConfig();

            Location spawn = ConfigManager.getSpawnConfig().getSpawn(SpawnType.MAIN);

            if (spawn == null) {
                messages.getNullSpawn().send(player);
                return;
            }
            player.teleport(spawn);
            messages.getTeleportedSpawn().send(player);
        }
    }
}
