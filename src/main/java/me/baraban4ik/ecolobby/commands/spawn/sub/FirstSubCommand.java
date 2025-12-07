package me.baraban4ik.ecolobby.commands.spawn.sub;

import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.enums.types.SpawnType;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class FirstSubCommand extends PlayerSubCommand {
    @Override
    public String getName() {
        return "first";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_SETSPAWN;
    }


    @Override
    public boolean executePlayer(Player player, String[] args) {
        ConfigManager.getSpawnConfig().setSpawn(SpawnType.FIRST, player.getLocation());
        PluginMessageSender.send(player, PluginMessage.SUCCESSFULLY_SETSPAWN);

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
