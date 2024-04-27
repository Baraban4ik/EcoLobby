package me.baraban4ik.ecolobby.commands;

import com.google.common.collect.Lists;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.BaseCommand;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.items;

public class LobbyCommand extends BaseCommand {
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "reload":
                        if (!hasPermission(sender, "ecolobby.reload")) return;

                        Chat.sendMessage(MESSAGES.PLUGIN_RELOADED, sender);
                        EcoLobby.instance.reload();
                        return;
                    case "setspawn": {
                        if (!isPlayer(sender)) return;

                        Player player = (Player) sender;
                        if (!hasPermission(player, "ecolobby.setspawn")) return;

                        Spawn.setSpawn(player, "main");
                        Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
                        return;
                    }
                    case "spawn": {
                        if (!isPlayer(sender)) return;

                        Player player = (Player) sender;
                        if (!hasPermission(player, "ecolobby.spawn")) return;

                        teleportSpawn(player);
                        return;
                    }
                    case "give":
                        if (!isPlayer(sender)) return;

                        Player player = (Player) sender;
                        if (!hasPermission(player, "ecolobby.give")) return;

                        Chat.sendMessage(MESSAGES.GIVE_USAGE, player);
                        return;

                }
                break;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "setspawn": {
                        if (!isPlayer(sender)) return;

                        Player player = (Player) sender;
                        if (!hasPermission(player, "ecolobby.setspawn")) return;

                        String spawnType = args[1];

                        if (!spawnType.equalsIgnoreCase("main") && !spawnType.equalsIgnoreCase("first")) {
                            Chat.sendMessage(MESSAGES.SETSPAWN_USAGE, player);
                            return;
                        }

                        Spawn.setSpawn(player, spawnType);
                        Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN, player);
                        return;
                    }
                    case "give": {
                        if (!isPlayer(sender)) return;

                        Player player = (Player) sender;
                        if (!hasPermission(player, "ecolobby.give")) return;

                        String itemName = args[1];
                        giveItem(player, itemName);
                        return;
                    }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("give")) {
                    if (!isPlayer(sender)) return;

                    Player player = (Player) sender;
                    if (!hasPermission(player, "ecolobby.give")) return;

                    Player fromPlayer = Bukkit.getPlayer(args[1]);
                    if (fromPlayer == null) {
                        Chat.sendMessage(MESSAGES.PLAYER_NOT_FOUND, player);
                        return;
                    }
                    String itemName = args[2];
                    giveItem(fromPlayer, itemName);
                    return;
                }
        }
        if (!hasPermission(sender, "ecolobby.help")) return;
        Chat.sendMessage(MESSAGES.HELP_MESSAGE, sender);
    }

    private void teleportSpawn(Player player) {
        Location spawn = Spawn.getSpawn("main");

        if (spawn == null) {
            Chat.sendPathMessage("null_spawn", player);
            return;
        }
        player.teleport(spawn);
        Chat.sendPathMessage("teleported_spawn", player);
    }

    private void giveItem(Player player, String itemName) {
        Chat.sendMessage(MESSAGES.SUCCESSFULLY_GIVE_ITEM, player);
        ItemManager.giveItem(player, itemName);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        ConfigurationSection itemsSection = items.getConfigurationSection("Items");
        List<String> argsComplete = new ArrayList<>();

        switch (args.length) {
            case 1:
                String[] permissions = {"ecolobby.reload", "ecolobby.setspawn", "ecolobby.spawn", "ecolobby.help", "ecolobby.give"};

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
