package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.Config;
import me.baraban4ik.ecolobby.config.files.MessagesConfig;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;


public class PreJoinListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Config config = ConfigManager.getConfig();
        MessagesConfig messages = ConfigManager.getMessagesConfig();

        if (config.isWhitelistEnabled()) {
            kickPlayer(event, config.getWhitelistPlayers(),
                    messages.getWhitelistKick(),
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    false);
        }
        else if (config.isBlacklistEnabled()) {
            kickPlayer(event, config.getBlacklistPlayers(),
                    messages.getBlacklistKick(),
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    true);
        }

    }

    private void kickPlayer(AsyncPlayerPreLoginEvent event, List<String> players, FormattedMessage message, AsyncPlayerPreLoginEvent.Result result, boolean shouldContain) {
        boolean contains = players.contains(event.getName());

        if (contains == shouldContain) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(event.getUniqueId());

            event.setLoginResult(result);
            event.setKickMessage(message.toStringOffline(player));
        }
    }

}
