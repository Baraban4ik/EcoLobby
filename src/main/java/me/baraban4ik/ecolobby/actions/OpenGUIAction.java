package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.gui.GUI;
import org.bukkit.entity.Player;

public class OpenGUIAction implements PlayerAction{
    @Override
    public void execute(Player player, String action) {
        new GUI(action).open(player);
    }
}
