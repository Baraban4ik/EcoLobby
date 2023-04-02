package me.baraban4ik.ecolobby.command;

import me.baraban4ik.ecolobby.configurations.Configurations;
import me.baraban4ik.ecolobby.utils.spawnUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.baraban4ik.ecolobby.utils.chatUtils.getLang;
import static me.baraban4ik.ecolobby.utils.chatUtils.sendMessage;

public class SpawnCommand implements CommandExecutor {

    private final Configurations config;

    public SpawnCommand(Configurations configurations) {
        config = configurations;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, getLang().getString("no-player", "This command is only available to players!"));
            return true;
        }
        if (!sender.hasPermission("ecolobby.spawn")) {
            sendMessage(sender, getLang().getString("no-permission"));
            return true;
        }
        if (config.get("spawn.yml").getString("spawn.x") == null && config.get("spawn.yml").getString("spawn.y") == null) {
            sendMessage(sender, getLang().getString("spawn-null"));
            return true;
        }

        Player player = (Player) sender;
        spawnUtils.tpSpawn(player);

        sendMessage(sender, getLang().getString("successfully-spawn"));
        return true;
    }
}
