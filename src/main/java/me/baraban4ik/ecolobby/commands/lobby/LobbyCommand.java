package me.baraban4ik.ecolobby.commands.lobby;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.commands.lobby.sub.*;
import me.baraban4ik.ecolobby.commands.lobby.sub.GiveSubCommand;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.command.CommandSender;

public class LobbyCommand extends AbstractCommand {


    public LobbyCommand() {
        registerSubCommand(new ReloadSubCommand());
        registerSubCommand(new SetSpawnSubCommand());
        registerSubCommand(new SpawnSubCommand());
        registerSubCommand(new GiveSubCommand());
        registerSubCommand(new OpenSubCommand());
        registerSubCommand(new HelpSubCommand());
    }

    @Override
    protected void sendHelp(CommandSender sender) {
        if (hasPermission(sender, "ecolobby.command.help")) {
            Chat.sendMessage(MESSAGES.HELP_MESSAGE(), sender);
        }
    }
}
