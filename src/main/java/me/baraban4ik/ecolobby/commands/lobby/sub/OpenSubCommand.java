package me.baraban4ik.ecolobby.commands.lobby.sub;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.gui.GUI;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.Collections;
import java.util.List;

public class OpenSubCommand extends PlayerSubCommand {


    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getPermission() {
        return "ecolobby.command.open";
    }
    @Override
    public boolean executePlayer(Player player, String[] args) {
        if (args.length == 1) {
            GUI GUI = new GUI(args[0]);
            if (!GUI.isActive) {
                Chat.sendMessage(MESSAGES.GUI_NOT_FOUND(), player);
                return true;
            }
            GUI.open(player);
            Chat.sendMessage(MESSAGES.SUCCESSFULLY_OPEN_MENU(), player);
            return true;
        }

        sendHelp(player);
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                Chat.sendMessage(MESSAGES.PLAYER_NOT_FOUND(), sender);
                return true;
            }
            GUI GUI = new GUI(args[0]);
            GUI.open(target);

            Chat.sendMessage(MESSAGES.SUCCESSFULLY_OPEN_MENU(), sender);
            return true;
        }
        return super.execute(sender, args);
    }

    private void sendHelp(CommandSender sender) {
        Chat.sendMessage(MESSAGES.OPEN_USAGE(), sender);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                return EcoLobby.getConfigurations().getGUIs();
            case 2:
                Bukkit.getOnlinePlayers().forEach(p -> argsComplete.add(p.getName()));
                return argsComplete;
        }
        return Collections.emptyList();
    }
}
