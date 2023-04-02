package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.configurations.Configurations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.baraban4ik.ecolobby.utils.chatUtils.getLang;

public class JoinListener implements Listener {
    private final Configurations config;

    public JoinListener(Configurations configurations) {
        config = configurations;
    }

    // Whitelist and Blacklist
    @EventHandler
    public void Whitelist(AsyncPlayerPreLoginEvent e) {
        if (config.get("config.yml").getBoolean("Join.whitelist.enabled")) {
            if (!config.get("config.yml").getStringList("settings.join.whitelist.players").contains(e.getName())) {
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
                e.setKickMessage(getLang().getString("whitelist-kick"));
            }
        }
    }

    @EventHandler
    public void Blacklist(AsyncPlayerPreLoginEvent e) {
        if (config.get("config.yml").getBoolean("Join.blacklist.enabled")) {
            if (config.get("config.yml").getStringList("settings.join.blacklist.players").contains(e.getName())) {
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                e.setKickMessage(getLang().getString("blacklist-kick"));
            }
        }
    }

    // Glow:
    @EventHandler
    public void Glow(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (config.get("config.yml").getBoolean("Join.glow")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, true, false));
        } else {
            player.removePotionEffect(PotionEffectType.GLOWING);
        }
    }

    // Clear:
    @EventHandler
    public void ClearChat(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (config.get("config.yml").getBoolean("Join.clear.chat")) {
            for (int i = 0; i < 120; ++i) {
                player.sendMessage("");
            }
        }
    }

    @EventHandler
    public void ClearInventory(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (config.get("config.yml").getBoolean("Join.clear.inventory")) {
            player.getInventory().clear();
        }
    }
}
