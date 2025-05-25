package me.baraban4ik.ecolobby;

import me.baraban4ik.ecolobby.commands.lobby.LobbyCommand;
import me.baraban4ik.ecolobby.commands.spawn.SetSpawnCommand;
import me.baraban4ik.ecolobby.commands.spawn.SpawnCommand;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.gui.GUI;
import me.baraban4ik.ecolobby.gui.GUIData;
import me.baraban4ik.ecolobby.gui.GUIListener;
import me.baraban4ik.ecolobby.listeners.*;
import me.baraban4ik.ecolobby.managers.BossBarManager;
import me.baraban4ik.ecolobby.managers.ScoreBoardManager;
import me.baraban4ik.ecolobby.managers.TabManager;
import me.baraban4ik.ecolobby.tasks.ParticleFallTask;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Configurations;
import me.baraban4ik.ecolobby.utils.Update;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static me.baraban4ik.ecolobby.utils.Chat.*;
import static me.baraban4ik.ecolobby.utils.Configurations.*;

public class EcoLobby extends JavaPlugin {

    private static Configurations configurations;
    private static EcoLobby instance;

    public static boolean PLACEHOLDER_API = false;
    public static boolean NOTE_BLOCK_API = false;

    public static boolean UPDATE_AVAILABLE = false;
    public static String UPDATE_VERSION = "";

    private final TabManager tabManager = new TabManager();
    private final BossBarManager barManager = new BossBarManager();
    private final ScoreBoardManager scoreBoardManager = new ScoreBoardManager();


    @Override
    public void onLoad() {
        instance = this;
        configurations = new Configurations(this,
                "config",
                "language/messages",
                "language/messages_ru",
                "spawn",
                "tablist",
                "bossbar",
                "scoreboard",
                "items"
        );
    }

    @Override
    public void onEnable() {
        if (config.getBoolean(Path.CHECK_UPDATES.getPath())) this.checkUpdate();

        PLACEHOLDER_API = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        NOTE_BLOCK_API = getServer().getPluginManager().isPluginEnabled("NoteBlockAPI");

        this.registerCommands();
        this.registerListeners();

        new Metrics(this, 14978);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (tablistConfig.getBoolean(Path.TABLIST.getPath())) tabManager.sendTablist();

        if (config.getBoolean(Path.FALL_PARTICLES.getPath())) {
            Bukkit.getOnlinePlayers().forEach(ParticleFallTask::add);
            (new ParticleFallTask()).runTaskTimer(this, 0L, 1L);
        }
        updateWorld();
        Chat.sendMessage(MESSAGES.ENABLE_MESSAGE(), Bukkit.getConsoleSender());
    }

    @Override
    public void onDisable() {
        barManager.removeAllPlayers();
        scoreBoardManager.removeAllPlayers();
    }

    public static EcoLobby getInstance() {
        return instance;
    }
    public static Configurations getConfigurations() {
        return configurations;
    }

    private void registerCommands() {
        getServer().getPluginCommand("ecolobby").setExecutor(new LobbyCommand());
        getServer().getPluginCommand("setspawn").setExecutor(new SetSpawnCommand());
        getServer().getPluginCommand("spawn").setExecutor(new SpawnCommand());
    }
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new HiderListener(), this);
        getServer().getPluginManager().registerEvents(new ItemsListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListeners(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new PreJoinListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    private void updateWorld() {
        for (World world : Bukkit.getWorlds()) {
            ConfigurationSection rules = config.getConfigurationSection(Path.WORLD_RULES.getPath());

            if (rules == null) return;
            long time = config.getLong(Path.WORLD_TIME.getPath());

            for (String rule : rules.getKeys(false)) {
                GameRule<?> gameRule = GameRule.getByName(rule);

                if (gameRule != null) {
                    String value = rules.getString(rule);
                    Object parsedValue;

                    if (gameRule.getType() == Boolean.class) {
                        parsedValue = Boolean.parseBoolean(value);
                    }
                    else if (gameRule.getType() == Integer.class) {
                        parsedValue = Integer.parseInt(value);
                    }
                    else continue;

                    world.setGameRule((GameRule<Object>) gameRule, parsedValue);
                }
            }
            world.setTime(time);
        }
    }

    private void checkUpdate() {
        new Update().getVersion(version ->
        {
            if (!this.getDescription().getVersion().equals(version)) {
                UPDATE_AVAILABLE = true;
                UPDATE_VERSION = version;

                sendMessage(MESSAGES.NEW_VERSION(UPDATE_VERSION), Bukkit.getConsoleSender());
            }
        });
    }

    public void reload() {
        configurations.reload();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (tablistConfig.getBoolean(Path.TABLIST.getPath())) tabManager.update(player);
            if (bossBarConfig.getBoolean(Path.BOSSBAR.getPath())) barManager.update(player);
            if (scoreboardConfig.getBoolean(Path.SCOREBOARD.getPath())) scoreBoardManager.update(player);

            GUI gui = GUIData.getGUI(player.getOpenInventory().getTopInventory());
            if (gui != null) gui.update(player);

        }
        updateWorld();
    }


    public Float getServerVersion() {
        String version = Bukkit.getBukkitVersion().split("-")[0];
        String[] versionParts = version.split("\\.");

        return Float.parseFloat(versionParts[0] + "." + versionParts[1] + versionParts[2]);
    }
}
