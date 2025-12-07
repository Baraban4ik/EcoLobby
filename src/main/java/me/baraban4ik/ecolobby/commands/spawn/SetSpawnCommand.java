package me.baraban4ik.ecolobby.commands.spawn;

import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.FirstSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.MainSubCommand;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand extends AbstractCommand {

    public SetSpawnCommand() {
        registerSubCommand(new MainSubCommand());
        registerSubCommand(new FirstSubCommand());
    }

    @Override
    protected void sendHelp(CommandSender sender) {
        PluginMessageSender.send(sender, PluginMessage.SETSPAWN_USAGE);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length > 0) {
            super.execute(sender, args);
            return;
        }
        if (isNotPlayer(sender)) return;

        Player player = (Player) sender;
        new MainSubCommand().executePlayer(player, args);
    }

}
