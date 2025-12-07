package me.baraban4ik.ecolobby.commands.lobby.sub;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GiveSubCommand extends PlayerSubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public Permission getPermission() {
        return Permission.COMMAND_GIVE;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);

            if (ItemManager.giveItem(sender, target, args[1])) {
                PluginMessageSender.send(sender, PluginMessage.SUCCESSFULLY_GIVE_ITEM);
            }
            return true;
        }
        return super.execute(sender, args);
    }

    @Override
    public boolean executePlayer(Player player, String[] args) {
        if (args.length == 1) {
            if (ItemManager.giveItem(player, player, args[0])) {
                PluginMessageSender.send(player, PluginMessage.SUCCESSFULLY_GIVE_ITEM);
            }
            return true;
        }
        sendHelp(player);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        PluginMessageSender.send(sender, PluginMessage.GIVE_USAGE);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = Stream.concat(
                EcoLobby.getConfigManager().getItemsNames().stream(),
                Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName)
        ).collect(Collectors.toList());

        return (args.length == 1 || args.length == 2) ? suggestions : Collections.emptyList();
    }


}