package me.baraban4ik.ecolobby.commands.lobby.sub;


import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.commands.base.SubCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_RELOAD;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        EcoLobby.getInstance().reload();
        PluginMessageSender.send(sender, PluginMessage.PLUGIN_RELOADED);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
