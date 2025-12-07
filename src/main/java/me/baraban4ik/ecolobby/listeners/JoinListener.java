package me.baraban4ik.ecolobby.listeners;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.JoinConfig;
import me.baraban4ik.ecolobby.config.files.PlayerConfig;
import me.baraban4ik.ecolobby.config.files.SpawnConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.enums.types.SpawnType;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class JoinListener implements Listener {

    private final EcoLobby plugin = EcoLobby.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        JoinConfig joinConfig = ConfigManager.getJoinConfig();
        Player player = event.getPlayer();

        if (joinConfig.isClearChat()) clearChat(player);

        if ((ConfigManager.getConfig().isCheckUpdates() && EcoLobby.UPDATE_AVAILABLE) &&
                (Permission.UPDATE_NOTIFY.has(player) || player.isOp())) {
            PluginMessageSender.sendUpdateMessage(player);
        }

        ActionManager.execute(player, joinConfig.getActions());
        ItemManager.giveJoinItems(player);
        setPlayersStatistics();

        plugin.getDisplayManagers().forEach(d -> d.send(player));
        plugin.getAmbientManager().addPlayer(player);

        if (joinConfig.isTeleportToSpawn()) teleportToSpawn(player);
        if (joinConfig.isMusicEnabled()) playMusic(joinConfig, player);
    }

    public static void setPlayersStatistics() {
        PlayerConfig playerConfig = ConfigManager.getPlayerConfig();
        double playerHealth = playerConfig.getHealth();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setGameMode(playerConfig.getGameMode());

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(playerHealth);
            player.setHealth(playerHealth);

            player.setFoodLevel(playerConfig.getFoodLevel());
            player.setLevel(playerConfig.getLevelExp());

            player.getActivePotionEffects().forEach(
                    activeEffect -> player.removePotionEffect(activeEffect.getType())
            );
            player.addPotionEffects(playerConfig.getEffects());
            player.setAllowFlight(!playerConfig.isDisableFly());
        });
    }


    private void teleportToSpawn(Player player) {
        SpawnConfig spawnConfig = ConfigManager.getSpawnConfig();
        Location spawn = spawnConfig.getSpawn(SpawnType.MAIN);

        if (!player.hasPlayedBefore()) {
            spawn = spawnConfig.getSpawn(SpawnType.FIRST);

            if (spawn == null)
                spawn = spawnConfig.getSpawn(SpawnType.MAIN);
        }

        if (spawn != null) player.teleport(spawn);
    }

    private void clearChat(Player player) {
        for (int i = 0; i < 100; i++)
            player.sendMessage("");
    }


    private void playMusic(JoinConfig joinConfig, Player player) {
        List<String> tracks = joinConfig.getMusicTracks();

        boolean repeatMode = joinConfig.isMusicRepeat();
        boolean random = joinConfig.isMusicRandom();

        Song[] songs = tracks.stream()
                .map(track -> NBSDecoder.parse(new File(plugin.getDataFolder() + "/tracks/", track)))
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
