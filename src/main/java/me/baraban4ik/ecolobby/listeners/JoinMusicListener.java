package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.JoinConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class JoinMusicListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        JoinConfig joinConfig = ConfigManager.getJoinConfig();
        Player player = event.getPlayer();

        if (joinConfig.isMusicEnabled()) return;

        List<String> tracks = joinConfig.getMusicTracks();

        boolean repeatMode = joinConfig.isMusicRepeat();
        boolean random = joinConfig.isMusicRandom();

        Song[] songs = tracks.stream()
                .map(track -> NBSDecoder.parse(new File(EcoLobby.getInstance().getDataFolder() + "/tracks/", track)))
                .filter(Objects::nonNull)
                .toArray(Song[]::new);

        Playlist playlist = new Playlist(songs);
        RadioSongPlayer rsp = new RadioSongPlayer(playlist);

        if (repeatMode) rsp.setRepeatMode(RepeatMode.ALL);
        else rsp.setRepeatMode(RepeatMode.NO);

        rsp.setRandom(random);

        rsp.addPlayer(player);
        rsp.setPlaying(true);
    }
}
