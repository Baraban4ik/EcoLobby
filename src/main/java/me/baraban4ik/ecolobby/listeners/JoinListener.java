package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.JoinConfig;
import me.baraban4ik.ecolobby.config.files.modules.PlayerConfig;
import me.baraban4ik.ecolobby.config.files.data.SpawnConfig;
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

public class JoinListener implements Listener {

    private final EcoLobby plugin;

    public JoinListener(EcoLobby plugin) {
        this.plugin = plugin;
    }

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
}
