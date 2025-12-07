package me.baraban4ik.ecolobby.commands.lobby.sub.spawn;

import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.SpawnCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SpawnSubCommand extends PlayerSubCommand {

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_SPAWN;
    }

    @Override
    public boolean executePlayer(Player player, String[] args) {
        new SpawnCommand().execute(player, args);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
