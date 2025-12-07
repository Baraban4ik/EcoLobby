package me.baraban4ik.ecolobby.actions;


import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                new FormattedMessage(action).send(player)
        );
    }
}