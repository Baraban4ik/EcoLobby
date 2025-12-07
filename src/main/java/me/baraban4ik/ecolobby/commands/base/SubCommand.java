package me.baraban4ik.ecolobby.commands.base;

import me.baraban4ik.ecolobby.enums.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    String getName();
    Permission getPermission();
    boolean execute(CommandSender sender, String[] args);

    List<String> tabComplete(CommandSender sender, String[] args);
}
