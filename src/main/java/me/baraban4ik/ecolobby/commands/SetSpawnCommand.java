package me.baraban4ik.ecolobby.commands;

import com.google.common.collect.Lists;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.BaseCommand;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCommand extends BaseCommand {
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!isPlayer(sender)) return;

        Player player = (Player) sender;
        if (hasPermission(player, "ecolobby.command.setspawn")) {

            if (args.length == 1) {
                SpawnType type = SpawnType.valueOf(args[0]);

                if (type != SpawnType.MAIN && type != SpawnType.FIRST) {
                    Chat.sendMessage(MESSAGES.SETSPAWN_USAGE, player);
                    return;
                }
                SpawnManager.setSpawn(player, type);
                Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
                return;
            }
            SpawnManager.setSpawn(player, SpawnType.MAIN);
            Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
        }
    }
    @Override
    public List<String> complete(CommandSender sender, String[] args) {

        if (args.length == 1) {
            return Lists.newArrayList("main", "first");
        }

        return new ArrayList<>();
    }
}
