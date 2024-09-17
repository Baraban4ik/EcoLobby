package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                onlinePlayer.sendMessage(Format.format(action, player))
        );
    }
}