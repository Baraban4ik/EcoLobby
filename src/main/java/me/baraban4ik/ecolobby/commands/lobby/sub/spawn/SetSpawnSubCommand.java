package me.baraban4ik.ecolobby.commands.lobby.sub.spawn;

import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.FirstSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.MainSubCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.types.SpawnType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetSpawnSubCommand extends PlayerSubCommand {

    private final MainSubCommand mainCommand;
    private final FirstSubCommand firstCommand;

    public SetSpawnSubCommand() {
        mainCommand = new MainSubCommand();
        firstCommand = new FirstSubCommand();
    }

    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_SETSPAWN;
    }
    @Override
    public boolean executePlayer(Player player, String[] args) {
        if (args.length == 0) {
            mainCommand.executePlayer(player, args);
            return true;
        }
        SpawnType type = SpawnType.valueOf(args[0].toUpperCase());

        switch (type) {
            case MAIN:
                mainCommand.executePlayer(player, args);
                break;
            case FIRST:
                firstCommand.executePlayer(player, args);
                break;
            default: return false;
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("main", "first");
        }

        return Collections.emptyList();
    }
}
