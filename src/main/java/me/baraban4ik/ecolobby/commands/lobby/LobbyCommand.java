package me.baraban4ik.ecolobby.commands.lobby;

import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.commands.lobby.sub.*;
import me.baraban4ik.ecolobby.commands.lobby.sub.spawn.SetSpawnSubCommand;
import me.baraban4ik.ecolobby.commands.lobby.sub.spawn.SpawnSubCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.command.CommandSender;

public class LobbyCommand extends AbstractCommand {

    public LobbyCommand() {
        registerSubCommand(new ReloadSubCommand());
        registerSubCommand(new SetSpawnSubCommand());
        registerSubCommand(new SpawnSubCommand());
        registerSubCommand(new GiveSubCommand());
        registerSubCommand(new OpenSubCommand());
    }

    @Override
    protected void sendHelp(CommandSender sender) {
        if (Permission.COMMAND.has(sender, true)) {
            PluginMessageSender.send(sender, PluginMessage.HELP_MESSAGE);
        }
    }
}
