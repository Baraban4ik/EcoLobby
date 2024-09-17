package me.baraban4ik.ecolobby.models;

import org.bukkit.entity.Player;

public interface PlayerAction {
    void execute(Player player, String action);
}
