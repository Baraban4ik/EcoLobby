package me.baraban4ik.ecolobby.actions;

import org.bukkit.entity.Player;

public class CloseGUIAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        player.getOpenInventory().close();
    }
}
