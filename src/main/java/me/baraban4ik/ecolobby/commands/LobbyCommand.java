package me.baraban4ik.ecolobby.commands;

import com.google.common.collect.Lists;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.BaseCommand;
import me.baraban4ik.ecolobby.enums.MessagesPath;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.itemsConfig;

public class LobbyCommand extends BaseCommand {
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "reload":
                        if (hasPermission(sender, "ecolobby.command.reload")) {

                            Chat.sendMessage(MESSAGES.PLUGIN_RELOADED, sender);

                            EcoLobby.getInstance().reload();
                        }
                        return;
                    case "setspawn": {
                        if (!isPlayer(sender)) return;
                        Player player = (Player) sender;

                        if (hasPermission(player, "ecolobby.command.setspawn")) {
                            SpawnManager.setSpawn(player, SpawnType.MAIN);
                            Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
                        }
                        return;
                    }
                    case "spawn": {
                        if (!isPlayer(sender)) return;
                        Player player = (Player) sender;

                        if (hasPermission(player, "ecolobby.command.spawn")) {
                            teleportSpawn(player);
                        }
                        return;
                    }
                    case "give":
                        if (!isPlayer(sender)) return;
                        Player player = (Player) sender;

                        if (hasPermission(player, "ecolobby.command.give")) {
                            Chat.sendMessage(MESSAGES.GIVE_USAGE, player);
                        }
                        return;

                }
                break;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "setspawn": {
                        if (!isPlayer(sender)) return;
                        Player player = (Player) sender;

                        if (hasPermission(player, "ecolobby.command.setspawn")) {
                            SpawnType type = SpawnType.valueOf(args[1]);

                            if (type != SpawnType.MAIN && type != SpawnType.FIRST) {
                                Chat.sendMessage(MESSAGES.SETSPAWN_USAGE, player);
                                return;
                            }
                            SpawnManager.setSpawn(player, type);
                            Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
                        }
                        return;
                    }
                    case "give": {
                        if (!isPlayer(sender)) return;
                        Player player = (Player) sender;

                        if (hasPermission(player, "ecolobby.command.give")) {
                            giveItem(player,  player, args[1]);
                        }
                        return;
                    }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("give")) {
                    if (hasPermission(sender, "ecolobby.command.give")) {
                        Player player = Bukkit.getPlayer(args[1]);

                        if (player == null) {
                            Chat.sendMessage(MESSAGES.PLAYER_NOT_FOUND, sender);
                            return;
                        }
                        giveItem(sender, player, args[2]);
                    }
                    return;
                }
        }
        if (hasPermission(sender, "ecolobby.command.help")) {
            Chat.sendMessage(MESSAGES.HELP_MESSAGE, sender);
        }
    }

    private void teleportSpawn(Player player) {
        Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);

        if (spawn == null) {
            Chat.sendPathMessage(MessagesPath.NULL_SPAWN.getPath(), player);
            return;
        }
        player.teleport(spawn);
        Chat.sendPathMessage(MessagesPath.TELEPORTED_SPAWN.getPath(), player);
    }

    private void giveItem(CommandSender sender, Player player, String itemName) {
        Chat.sendMessage(MESSAGES.SUCCESSFULLY_GIVE_ITEM, sender);
        ItemManager.giveItem(player, itemName);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        ConfigurationSection itemsSection = itemsConfig.getConfigurationSection("Items");
        List<String> argsComplete = new ArrayList<>();

        switch (args.length) {
            case 1:
                String[] permissions = {"ecolobby.command.reload", "ecolobby.command.setspawn", "ecolobby.command.spawn", "ecolobby.command.help", "ecolobby.command.give"};

                for (String permission : permissions) {
                    if (sender.hasPermission(permission)) {
                        argsComplete.add(permission.substring(permission.lastIndexOf('.') + 1));
                    }
                }
                return argsComplete;
            case 2:
                if (args[0].equalsIgnoreCase("give") && itemsSection != null) {
                    argsComplete.addAll(itemsSection.getKeys(false));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        argsComplete.add(player.getName());
                    }
                    return argsComplete;
                }

                if (args[0].equalsIgnoreCase("setspawn"))
                    return Lists.newArrayList("first", "main");
            case 3:
                if (args[0].equalsIgnoreCase("give") && itemsSection != null)
                    return Lists.newArrayList(itemsSection.getKeys(false));
        }

        return new ArrayList<>();
    }
}
