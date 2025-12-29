package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;

import me.baraban4ik.ecolobby.config.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HiderListener implements Listener {

    private final Config config;

    public HiderListener() {
        config = ConfigManager.getConfig();
    }

    @EventHandler
    public void hideJoin(PlayerJoinEvent event) {
        if (config.isHideStreamAll() || config.isHideStreamJoin())
            event.setJoinMessage(null);

        boolean hidePlayers = config.isHidePlayers();

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
        if (config.isHideStreamAll() || config.isHideStreamLeave())
            event.setQuitMessage(null);
    }
    @EventHandler
    public void hideDeath(PlayerDeathEvent event) {
        if (config.isHideStreamAll() || config.isHideStreamDeath())
            event.setDeathMessage(null);
    }
    @EventHandler
    public void hideKick(PlayerKickEvent event) {
        if (config.isHideStreamAll() || config.isHideStreamKick())
            event.setLeaveMessage("");
    }
}
