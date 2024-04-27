package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Spawn;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

import static me.baraban4ik.ecolobby.EcoLobby.config;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("Join_and_Leave.teleport_to_spawn")) {
            Location spawn;

            if (player.hasPlayedBefore()) spawn = Spawn.getSpawn("main");
            else spawn = Spawn.getSpawn("first");

            if (spawn != null) player.teleport(spawn);
        }
        if (config.getBoolean("Join_and_Leave.clear_chat")) {
            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }
        }
        if (config.getBoolean("Join_and_Leave.music.enabled") && EcoLobby.noteBlockAPI) {
            String track = config.getString("Join_and_Leave.music.track");
            Song song = NBSDecoder.parse(new File(EcoLobby.instance.getDataFolder(), track));

            RadioSongPlayer rsp = new RadioSongPlayer(song);
            rsp.addPlayer(player);
            rsp.setPlaying(true);

            String repeatMode = config.getString("Join.music.repeat", "NO");
            if (repeatMode.equalsIgnoreCase("YES")) {
                rsp.setRepeatMode(RepeatMode.ALL);
            }
            rsp.setRepeatMode(RepeatMode.valueOf(repeatMode));
        }
        ItemManager.setItems(player);
        ActionManager.execute(player, config.getStringList("Join_and_Leave.actions"));

        if (EcoLobby.updateAvailable) {
            if (player.hasPermission("ecolobby.notify") || player.isOp()) {
                Chat.sendMessage(MESSAGES.NEW_VERSION, player);
            }
        }
    }
}
