package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.ConfigPath;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.baraban4ik.ecolobby.EcoLobby.config;

public class LeaveListeners implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean(ConfigPath.CLEAR_ITEMS.getPath()))
            player.getInventory().clear();

        if (EcoLobby.NOTE_BLOCK_API)
            NoteBlockAPI.stopPlaying(event.getPlayer());
    }
}
