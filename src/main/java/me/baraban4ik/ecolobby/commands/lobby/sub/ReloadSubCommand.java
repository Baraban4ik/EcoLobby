package me.baraban4ik.ecolobby.commands.lobby.sub;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.SubCommand;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadSubCommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "ecolobby.command.reload";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        EcoLobby.getInstance().reload();
        Chat.sendMessage(MESSAGES.PLUGIN_RELOADED(), sender);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
