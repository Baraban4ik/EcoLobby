package me.baraban4ik.ecolobby.actions;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConsoleCommandAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action);
    }
}
