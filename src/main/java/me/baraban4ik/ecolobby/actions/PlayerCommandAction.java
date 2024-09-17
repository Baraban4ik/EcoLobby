package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.entity.Player;

public class PlayerCommandAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        player.performCommand(action);
    }
}