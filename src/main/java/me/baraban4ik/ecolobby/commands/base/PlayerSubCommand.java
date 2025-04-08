package me.baraban4ik.ecolobby.commands.base;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerSubCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Chat.sendMessage(MESSAGES.ONLY_PLAYER(), sender);
            return true;
        }
        return executePlayer((Player) sender, args);
    }

    public abstract boolean executePlayer(Player player, String[] args);
}

