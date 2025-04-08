package me.baraban4ik.ecolobby.commands.spawn.sub;

import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import me.baraban4ik.ecolobby.utils.Chat;
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
    public String getPermission() {
        return "ecolobby.command.setspawn";
    }


    @Override
    public boolean executePlayer(Player player, String[] args) {
        SpawnManager.setSpawn(player, SpawnType.FIRST);
        Chat.sendMessage(MESSAGES.SUCCESSFULLY_SETSPAWN(), player);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
