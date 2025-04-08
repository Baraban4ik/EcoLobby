package me.baraban4ik.ecolobby.commands.spawn;

import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SpawnCommand extends AbstractCommand {

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!isPlayer(sender)) return;
        Player player = (Player) sender;

        if (hasPermission(player, "ecolobby.command.spawn")) {
            Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);

            if (spawn == null) {
                Chat.sendPathMessage(Path.NULL_SPAWN, player);
                return;
            }
            player.teleport(spawn);
            Chat.sendPathMessage(Path.TELEPORTED_SPAWN, player);
        }
    }
}
