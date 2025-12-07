package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.message.MessageFormatter;
import org.bukkit.entity.Player;

public class TitleAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        String[] actionArgs = action.split(";");

        String title = MessageFormatter.format(player, actionArgs[0]);
        String sub = actionArgs.length >= 2 ? MessageFormatter.format(player, actionArgs[1]) : "";

        player.sendTitle(title, sub, 10, 20, 70);
    }
}