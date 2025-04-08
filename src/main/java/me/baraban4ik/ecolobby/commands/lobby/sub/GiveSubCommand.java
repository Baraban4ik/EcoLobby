package me.baraban4ik.ecolobby.commands.lobby.sub;

import com.google.common.collect.Lists;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.commands.base.PlayerSubCommand;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static me.baraban4ik.ecolobby.utils.Configurations.itemsConfig;

public class GiveSubCommand extends PlayerSubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getPermission() {
        return "ecolobby.command.give";
    }

    @Override
    public boolean executePlayer(Player player, String[] args) {
        if (args.length == 1) {
            ItemManager.giveItem(player, args[0]);
            Chat.sendMessage(MESSAGES.SUCCESSFULLY_GIVE_ITEM(), player);
            return true;
        }

        sendHelp(player);
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Chat.sendMessage(MESSAGES.PLAYER_NOT_FOUND(), sender);
                return true;
            }

            ItemManager.giveItem(target, args[1]);
            Chat.sendMessage(MESSAGES.SUCCESSFULLY_GIVE_ITEM(), sender);
            return true;
        }
        return super.execute(sender, args);
    }

    private void sendHelp(CommandSender sender) {
        Chat.sendMessage(MESSAGES.GIVE_USAGE(), sender);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        ConfigurationSection itemsSection = itemsConfig.getConfigurationSection("Items");

        switch (args.length) {
            case 1:
                if (itemsSection != null) {
                    argsComplete.addAll(itemsSection.getKeys(false));
                    Bukkit.getOnlinePlayers().forEach(p -> argsComplete.add(p.getName()));

                    return argsComplete;

                }
                break;
            case 2: return Lists.newArrayList(itemsSection.getKeys(false));
        }

        return Collections.emptyList();
    }
}

