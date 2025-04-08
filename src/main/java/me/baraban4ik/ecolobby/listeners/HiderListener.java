package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.baraban4ik.ecolobby.utils.Configurations.config;

public class HiderListener implements Listener {
    @EventHandler
    public void hideJoin(PlayerJoinEvent event) {
        if (config.getBoolean(Path.HIDE_STREAM.getPath()))
            event.setJoinMessage(null);

        boolean hidePlayers = config.getBoolean(Path.HIDE_PLAYERS.getPath());

        for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (hidePlayers) {
                otherPlayer.hidePlayer(EcoLobby.getInstance(), event.getPlayer());
                event.getPlayer().hidePlayer(EcoLobby.getInstance(), otherPlayer);
                continue;
            }
            otherPlayer.showPlayer(EcoLobby.getInstance(), event.getPlayer());
            event.getPlayer().showPlayer(EcoLobby.getInstance(), otherPlayer);
        }
    }
    @EventHandler
    public void hideLeave(PlayerQuitEvent event) {
        if (config.getBoolean(Path.HIDE_STREAM.getPath()))
            event.setQuitMessage(null);
    }
    @EventHandler
    public void hideDeath(PlayerDeathEvent event) {
        if (config.getBoolean(Path.HIDE_STREAM.getPath()))
            event.setDeathMessage(null);
    }
    @EventHandler
    public void hideKick(PlayerKickEvent event) {
        if (config.getBoolean(Path.HIDE_STREAM.getPath()))
            event.setLeaveMessage("");
    }
}
