package me.baraban4ik.ecolobby.actions;


import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.entity.Player;

public class MessageAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        new FormattedMessage(action).send(player);
    }
}