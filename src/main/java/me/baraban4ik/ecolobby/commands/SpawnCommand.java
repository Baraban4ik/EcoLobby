package me.baraban4ik.ecolobby.commands;

import me.baraban4ik.ecolobby.commands.base.BaseCommand;
import me.baraban4ik.ecolobby.enums.MessagesPath;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand extends BaseCommand {
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!isPlayer(sender)) return;
        Player player = (Player) sender;

        if (hasPermission(player, "ecolobby.command.spawn")) {
            Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);

            if (spawn == null) {
                Chat.sendPathMessage(MessagesPath.NULL_SPAWN.getPath(), player);
                return;
            }
            player.teleport(spawn);
            Chat.sendPathMessage(MessagesPath.TELEPORTED_SPAWN.getPath(), player);
        }
    }
    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
