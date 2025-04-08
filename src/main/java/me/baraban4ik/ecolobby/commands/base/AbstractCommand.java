package me.baraban4ik.ecolobby.commands.base;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    private final List<SubCommand> subCommands = new ArrayList<>();

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    public boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission) {
        if (sender.hasPermission(permission)) return true;
        Chat.sendPathMessage(Path.NO_PERMISSION, sender);
        return false;
    }

    public boolean isPlayer(@NotNull CommandSender sender) {
        if (sender instanceof Player) return true;
        Chat.sendMessage(MESSAGES.ONLY_PLAYER(), sender);
        return false;
    }

    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return;
        }

        String subName = args[0];
        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(subName)) {
                if (!hasPermission(sender, subCommand.getPermission())) return;
                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                if (!subCommand.execute(sender, subArgs)) {
                    sendHelp(sender);
                }
                return;
            }
        }
        sendHelp(sender);
    }

    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (SubCommand cmd : subCommands) {
                if (sender.hasPermission(cmd.getPermission())) {
                    suggestions.add(cmd.getName());
                }
            }
            return suggestions;
        }

        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(args[0])) {
                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                return subCommand.tabComplete(sender, subArgs);
            }
        }

        return new ArrayList<>();
    }

    protected void sendHelp(CommandSender sender) {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        List<String> result = new ArrayList<>();
        if (list == null || args.length == 0) return result;

        String last = args[args.length - 1].toLowerCase();
        for (String s : list) {
            if (s.toLowerCase().startsWith(last)) {
                result.add(s);
            }
        }
        return result;
    }
}
