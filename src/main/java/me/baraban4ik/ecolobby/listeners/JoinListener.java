package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.ConfigPath;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.managers.SpawnManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.config;
import static me.baraban4ik.ecolobby.EcoLobby.getInstance;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean(ConfigPath.TELEPORT_TO_SPAWN.getPath())) {
            teleportToSpawn(player);
        }
        if (config.getBoolean(ConfigPath.MUSIC.getPath()) && EcoLobby.NOTE_BLOCK_API) {
            playMusic(player);
        }

        if (config.getBoolean(ConfigPath.CLEAR_CHAT.getPath())) {
            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }
        }

        ActionManager.execute(player, config.getStringList(ConfigPath.JOIN_ACTIONS.getPath()));
        ItemManager.setItems(player);

        if (EcoLobby.UPDATE_AVAILABLE) {
            sendUpdateAvailable(player);
        }
    }


    private void sendUpdateAvailable(Player player) {
        if (player.hasPermission("ecolobby.notify") || player.isOp()) {

            List<Component> NEW_VERSION = Arrays.asList(
                    Component.text(" __       ", TextColor.color(0xAEE495)),
                    Component.text("|__", TextColor.color(0xAEE495))
                            .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                            .append(Component.text("Plugin update available!", TextColor.color(0xf2ede0))),
                    Component.text("|__", TextColor.color(0xAEE495))
                            .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                            .append(Component.text("Current version", TextColor.color(0xf2ede0)))
                            .append(Component.text(" — ", NamedTextColor.GRAY))
                            .append(Component.text(getInstance().getDescription().getVersion(), TextColor.color(0xAEE495)))
                            .append(Component.text(" (", NamedTextColor.GRAY))
                            .append(Component.text(EcoLobby.UPDATE_VERSION, TextColor.color(0xAEE495)))
                            .append( Component.text(")", NamedTextColor.GRAY)),
                    Component.space()
            );

            Chat.sendMessage(NEW_VERSION, player);
        }
    }

    private void teleportToSpawn(Player player) {
        Location spawn;

        if (player.hasPlayedBefore()) spawn = SpawnManager.getSpawn(SpawnType.MAIN);
        else spawn = SpawnManager.getSpawn(SpawnType.FIRST);

        if (spawn != null) player.teleport(spawn);
    }

    private void playMusic(Player player) {
        List<String> tracks = config.getStringList(ConfigPath.MUSIC_TRACKS.getPath());
        String repeatMode = config.getString(ConfigPath.MUSIC_REPEAT.getPath(), "NO");

        List<Song> songList = new ArrayList<>();

        for (String track : tracks) {
            Song song = NBSDecoder.parse(new File(getInstance().getDataFolder(), track));
            if (song != null) songList.add(song);
        }
        if (songList.isEmpty()) return;

        Playlist playlist = new Playlist(songList.toArray(new Song[0]));
        RadioSongPlayer rsp = new RadioSongPlayer(playlist);

        if (repeatMode.equalsIgnoreCase("YES"))
            rsp.setRepeatMode(RepeatMode.ALL);
        else if (repeatMode.equalsIgnoreCase("NO"))
            rsp.setRepeatMode(RepeatMode.NO);
        else
            rsp.setRepeatMode(RepeatMode.NO);
		
        rsp.addPlayer(player);
        rsp.setPlaying(true);
    }

}
