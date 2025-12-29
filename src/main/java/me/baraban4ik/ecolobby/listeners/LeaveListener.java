package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private final EcoLobby plugin;

    public LeaveListener(EcoLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (ConfigManager.getConfig().isLeaveClearItems())
            player.getInventory().clear();


        if (EcoLobby.NOTE_BLOCK_API) {
            NoteBlockAPI.stopPlaying(event.getPlayer());
        }
        plugin.getDisplayManagers().forEach(d -> d.removePlayer(player));
        plugin.getAmbientManager().removePlayer(player);
    }
}
