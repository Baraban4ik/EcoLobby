package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.*;
import me.baraban4ik.ecolobby.tasks.ParticleFallTask;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.*;
import static me.baraban4ik.ecolobby.utils.Configurations.*;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean(Path.TELEPORT_TO_SPAWN.getPath())) teleportToSpawn(player);
        if (config.getBoolean(Path.MUSIC.getPath()) && EcoLobby.NOTE_BLOCK_API) playMusic(player);

        if (bossBarConfig.getBoolean(Path.BOSSBAR.getPath())) {
            BossBarManager barManager = new BossBarManager();
            barManager.sendBossBar(player);
        }
        if (scoreboardConfig.getBoolean(Path.SCOREBOARD.getPath())) {
            ScoreBoardManager scoreBoardManager = new ScoreBoardManager();
            scoreBoardManager.send(player);
        }

        if (config.getBoolean(Path.CLEAR_CHAT.getPath())) {
            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }
        }

        ActionManager.execute(player, config.getStringList(Path.JOIN_ACTIONS.getPath()));
        ItemManager.setItems(player);

        if (config.getBoolean(Path.CHECK_UPDATES.getPath()) && EcoLobby.UPDATE_AVAILABLE)
            sendUpdateAvailable(player);

        ParticleFallTask.add(player);
    }


    private void sendUpdateAvailable(Player player) {
        if (player.hasPermission("ecolobby.notify") || player.isOp()) {
            Chat.sendMessage(MESSAGES.NEW_VERSION(UPDATE_VERSION), player);
        }
    }

    private void teleportToSpawn(Player player) {
        Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);

        if (!player.hasPlayedBefore()) {
            spawn = SpawnManager.getSpawn(SpawnType.FIRST);

            if (spawn == null)
                spawn = SpawnManager.getSpawn(SpawnType.MAIN);
        }

        if (spawn != null) player.teleport(spawn);
    }

    private void playMusic(Player player) {
        List<String> tracks = config.getStringList(Path.MUSIC_TRACKS.getPath());

        boolean repeatMode = config.getBoolean(Path.MUSIC_REPEAT.getPath(), false);
        boolean random = config.getBoolean(Path.MUSIC_RANDOM.getPath(), false);

        List<Song> songList = new ArrayList<>();

        for (String track : tracks) {
            Song song = NBSDecoder.parse(new File(getInstance().getDataFolder(), track));
            if (song != null) songList.add(song);
        }
        if (songList.isEmpty()) return;

        Playlist playlist = new Playlist(songList.toArray(new Song[0]));
        RadioSongPlayer rsp = new RadioSongPlayer(playlist);

        if (repeatMode) rsp.setRepeatMode(RepeatMode.ALL);
        else rsp.setRepeatMode(RepeatMode.NO);

        rsp.setRandom(random);

        rsp.addPlayer(player);
        rsp.setPlaying(true);
    }

}
