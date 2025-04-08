package me.baraban4ik.ecolobby.commands.base;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface SubCommand {
    String getName();
    String getPermission();
    boolean execute(CommandSender sender, String[] args);

    List<String> argsComplete = new ArrayList<>();
    List<String> tabComplete(CommandSender sender, String[] args);
}
