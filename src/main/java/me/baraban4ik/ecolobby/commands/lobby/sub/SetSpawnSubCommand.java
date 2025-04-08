package me.baraban4ik.ecolobby.commands.lobby.sub;

import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.FirstSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.MainSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetSpawnSubCommand extends PlayerSubCommand {

    private final MainSubCommand mainCommand = new MainSubCommand();
    private final FirstSubCommand firstCommand = new FirstSubCommand();


    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getPermission() {
        return "ecolobby.command.setspawn";
    }
    @Override
    public boolean executePlayer(Player player, String[] args) {

        if (args.length == 0) {
            mainCommand.executePlayer(player, args);
            return true;
        }
        String type = args[0].toLowerCase();

        switch (type) {
            case "main":
                mainCommand.executePlayer(player, args);
                break;
            case "first":
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
