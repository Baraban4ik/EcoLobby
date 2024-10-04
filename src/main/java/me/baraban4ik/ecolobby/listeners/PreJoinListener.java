package me.baraban4ik.ecolobby.listeners;


import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.config;
import static me.baraban4ik.ecolobby.EcoLobby.messages;

public class PreJoinListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        if (config.getBoolean(Path.WHITELIST.getPath())) {
            whitelist(event, event.getName());
        }
        else if (config.getBoolean(Path.BLACKLIST.getPath())) {
            blacklist(event, event.getName());
        }
    }

    private void whitelist(AsyncPlayerPreLoginEvent event, String playerName) {
        List<String> players = config.getStringList(Path.WHITELIST_PLAYERS.getPath());

        if (!players.contains(playerName)) {
            String kickPathMessage = messages.getString(Path.WHITELIST_KICK.getPath());
            String kickMessage = Format.format(kickPathMessage, Bukkit.getPlayer(playerName));

            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
            event.setKickMessage(kickMessage);
        }
    }

    private void blacklist(AsyncPlayerPreLoginEvent event, String playerName) {
        List<String> players = config.getStringList(Path.BLACKLIST_PLAYERS.getPath());

        if (players.contains(playerName)) {
            String kickPathMessage = messages.getString(Path.BLACKLIST_KICK.getPath());
            String kickMessage = Format.format(kickPathMessage, Bukkit.getPlayer(playerName));

            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(kickMessage);
        }
    }
}
