package me.baraban4ik.ecolobby.commands.base;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.enums.MessagesPath;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCommand extends BaseTabCompleter implements CommandExecutor {

    public boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission) {
        if (sender.hasPermission(permission)) return true;

        Chat.sendPathMessage(MessagesPath.NO_PERMISSION.getPath(), sender);
        return false;
    }
    public boolean isPlayer(@NotNull CommandSender sender) {
        if (sender instanceof Player) return true;

        Chat.sendMessage(MESSAGES.ONLY_PLAYER, sender);
        return false;
    }
    public abstract void execute(@NotNull CommandSender sender, @NotNull String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.execute(sender, args);
        return true;
    }
}
