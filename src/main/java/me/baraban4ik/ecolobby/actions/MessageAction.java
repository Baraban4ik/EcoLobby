package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.entity.Player;

public class MessageAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        player.sendMessage(Format.format(action, player));
    }
}