package me.baraban4ik.ecolobby.commands.lobby.sub;


import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.gui.GUI;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OpenSubCommand extends PlayerSubCommand {

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_OPEN;
    }
    @Override
    public boolean executePlayer(Player player, String[] args) {
        if (args.length == 1) {
            GUI GUI = new GUI(args[0]);

            if (!GUI.isLoaded) {
                PluginMessageSender.send(player, PluginMessage.GUI_NOT_FOUND);
                return true;
            }

            GUI.open(player);
            PluginMessageSender.send(player, PluginMessage.SUCCESSFULLY_OPEN_MENU);
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
                PluginMessageSender.send(sender, PluginMessage.PLAYER_NOT_FOUND);
                return true;
            }

            GUI GUI = new GUI(args[0]);
            GUI.open(target);

            PluginMessageSender.send(sender, PluginMessage.SUCCESSFULLY_OPEN_MENU);
            return true;
        }
        return super.execute(sender, args);
    }

    private void sendHelp(CommandSender sender) {
        PluginMessageSender.send(sender, PluginMessage.OPEN_USAGE);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> playersNames = Bukkit.getOnlinePlayers().stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toList());

        switch (args.length) {
            case 1: return EcoLobby.getConfigManager().getGUIsNames();
            case 2: return playersNames;
        }
        return Collections.emptyList();
    }
}