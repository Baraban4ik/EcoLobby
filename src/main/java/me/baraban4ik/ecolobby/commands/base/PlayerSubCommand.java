package me.baraban4ik.ecolobby.commands.base;

import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerSubCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            PluginMessageSender.send(sender, PluginMessage.ONLY_PLAYER);
            return true;
        }
        return executePlayer((Player) sender, args);
    }

    public abstract boolean executePlayer(Player player, String[] args);
}

