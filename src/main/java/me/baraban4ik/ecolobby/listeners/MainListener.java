package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.configurations.Configurations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

import static me.baraban4ik.ecolobby.utils.chatUtils.*;

public class MainListener implements Listener {
    private final EcoLobby plugin;
    private final Configurations config;

    public MainListener(Configurations configurations, EcoLobby plugin) {
        config = configurations;
        this.plugin = plugin;
    }

    // Hidestream:
    @EventHandler
    public void HidestreamJoin(PlayerJoinEvent e) {
        if (config.get("config.yml").getBoolean("Main.hidestream")) {
            e.setJoinMessage(null);
        }
    }

    @EventHandler
    public void HidestreamQuit(PlayerQuitEvent e) {
        if (config.get("config.yml").getBoolean("Main.hidestream")) {
            e.setQuitMessage(null);
        }
    }

    @EventHandler
    public void HidestreamDeath(PlayerDeathEvent e) {
        if (config.get("config.yml").getBoolean("Main.hidestream")) {
            e.setDeathMessage(null);
        }
    }

    @EventHandler
    public void HidestreamKick(PlayerKickEvent e) {
        if (config.get("config.yml").getBoolean("Main.hidestream")) {
            e.setLeaveMessage("");
        }
    }

    // Hide players:
    @EventHandler
    public void HidePlayers(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (config.get("config.yml").getBoolean("Main.hide-players")) {
            player.hidePlayer(plugin, player);
        } else {
            player.showPlayer(plugin, player);
        }
    }

    // Motd:
    @EventHandler
    public void Motd(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (config.get("config.yml").getBoolean("Main.motd-message")) {
            List<String> motd = getLang().getStringList("motd");
            if (motd.isEmpty()) {
                return;
            }
            motd.forEach((x) -> sendMessage(player, x));
        }
        if (config.get("config.yml").getBoolean("Main.title-motd.enabled")) {
            String text = config.get("config.yml").getString("Main.title-motd.title-text");
            String subText = config.get("config.yml").getString("Main.title-motd.subtitle-text");
            int duration = config.get("config.yml").getInt("Main.title-motd.duration");

            sendTitle(player, text, subText, duration);
        }
        if (config.get("config.yml").getBoolean("Main.action-bar-motd.enabled")) {
            sendActionBar(player, config.get("config.yml").getString("Main.action-bar-motd.text"));
        }
    }

}
