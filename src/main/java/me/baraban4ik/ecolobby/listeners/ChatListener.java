package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (Permission.BYPASS_CHAT.has(player)) return;

        if (ConfigManager.getPlayerConfig().isDisableChat()) {
            ConfigManager.getMessagesConfig().getDenyChat().send(player);
            event.setCancelled(true);
        }
    }

}
