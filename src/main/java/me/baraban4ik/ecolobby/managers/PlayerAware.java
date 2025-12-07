package me.baraban4ik.ecolobby.managers;

import org.bukkit.entity.Player;

public interface PlayerAware {
    void update(Player player);
    void removePlayer(Player player);
    default void start() {}
    default void stop() {}
}


