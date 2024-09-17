package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.entity.Player;

public class TitleAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        String[] actionArgs = action.split(";");
        player.sendTitle (
                Format.format(actionArgs[0], player),
                Format.format(actionArgs[1], player), 10, 20, 70);
    }
}