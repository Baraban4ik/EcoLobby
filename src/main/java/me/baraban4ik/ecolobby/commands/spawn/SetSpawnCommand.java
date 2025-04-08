package me.baraban4ik.ecolobby.commands.spawn;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.AbstractCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.FirstSubCommand;
import me.baraban4ik.ecolobby.commands.spawn.sub.MainSubCommand;
import me.baraban4ik.ecolobby.utils.Chat;
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
        Chat.sendMessage(MESSAGES.SETSPAWN_USAGE(), sender);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length > 0) {
            super.execute(sender, args);
            return;
        }
        if (!isPlayer(sender)) return;

        Player player = (Player) sender;
        new MainSubCommand().executePlayer(player, args);
    }

}
