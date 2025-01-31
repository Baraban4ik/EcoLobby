package me.baraban4ik.ecolobby;

import me.baraban4ik.ecolobby.commands.LobbyCommand;
import me.baraban4ik.ecolobby.commands.SetSpawnCommand;
import me.baraban4ik.ecolobby.commands.SpawnCommand;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static me.baraban4ik.ecolobby.utils.Chat.*;

public class EcoLobby extends JavaPlugin {

    private static Configurations configurations;
    private static EcoLobby instance;

    public static FileConfiguration config;
    public static FileConfiguration messages;

    public static FileConfiguration itemsConfig;
    public static FileConfiguration spawnConfig;
    public static FileConfiguration tablistConfig;
    public static FileConfiguration bossBarConfig;
    public static FileConfiguration scoreboardConfig;

    public static boolean PLACEHOLDER_API = false;
    public static boolean NOTE_BLOCK_API = false;

    public static boolean UPDATE_AVAILABLE = false;
    public static String UPDATE_VERSION = "";

    TabManager tabManager = new TabManager();
    BossBarManager barManager = new BossBarManager();
    ScoreBoardManager scoreBoardManager = new ScoreBoardManager();


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
        configurations.load();
        this.loadConfigurations();
        configurations.loadGUIs();
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

        System.out.println(configurations.getGUIs());
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
        this.loadConfigurations();
        configurations.reloadGUIs();

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
        String version = Bukkit.getVersion();
        String pattern = "[^0-9\\.\\:]";
        String versionMinecraft = version.replaceAll(pattern, "");

        return Float.parseFloat(versionMinecraft.substring(versionMinecraft.indexOf(":") + 1, versionMinecraft.lastIndexOf(".")));
    }


    private void loadLanguage() {
        messages = configurations.getConfig("language/messages");

        if (config.getString(Path.LANGUAGE.getPath()).equalsIgnoreCase("ru"))
            messages = configurations.getConfig("language/messages_ru");
    }

    private void loadConfigurations() {
        config = configurations.getConfig("config");
        spawnConfig = configurations.getConfig("spawn");
        itemsConfig = configurations.getConfig("items");
        tablistConfig = configurations.getConfig("tablist");
        bossBarConfig = configurations.getConfig("bossbar");
        scoreboardConfig = configurations.getConfig("scoreboard");

        this.loadLanguage();

        if (!Objects.equals(config.get(Path.CONFIG_VERSION.getPath()), this.getDescription().getVersion())) {

            File file = new File(this.getDataFolder(), "config.yml");
            file.renameTo(new File(this.getDataFolder(), "config.yml-old"));

            configurations.reload();
        }
    }
}
