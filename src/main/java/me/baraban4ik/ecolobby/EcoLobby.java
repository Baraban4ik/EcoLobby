package me.baraban4ik.ecolobby;

import me.baraban4ik.ecolobby.commands.LobbyCommand;
import me.baraban4ik.ecolobby.commands.SetSpawnCommand;
import me.baraban4ik.ecolobby.commands.SpawnCommand;
import me.baraban4ik.ecolobby.listeners.*;
import me.baraban4ik.ecolobby.managers.TabManager;
import me.baraban4ik.ecolobby.utils.Files;
import me.baraban4ik.ecolobby.utils.Update;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static me.baraban4ik.ecolobby.utils.Chat.sendMessage;

public final class EcoLobby extends JavaPlugin {

    Files files = new Files(this, "config.yml",
            "language/messages.yml",
            "language/messages_ru.yml",
            "spawn.yml",
            "items.yml"
    );

    public Files getFiles() {
        return files;
    }

    public static EcoLobby instance;

    public static FileConfiguration config;
    public static FileConfiguration messages;
    public static FileConfiguration items;
    public static FileConfiguration spawn;

    public static boolean placeholderAPI = false;
    public static boolean noteBlockAPI = false;
    public static boolean updateAvailable = false;
    public static boolean legacyItems = false;

    @Override
    public void onLoad() {
        instance = this;

        this.files.load();
        this.loadConfigurations();
    }

    TabManager tab = new TabManager();
    @Override
    public void onEnable() {

        placeholderAPI = this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        noteBlockAPI = this.getServer().getPluginManager().isPluginEnabled("NoteBlockAPI");

        this.registerCommands();
        this.registerListeners();

        if (config.getBoolean("check_updates")) this.checkUpdate();
        else updateAvailable = false;

        legacyItems = getServerVersion() < 1.14f;
        Metrics metrics = new Metrics(this, 14978);

        sendMessage(MESSAGES.ENABLE_MESSAGE, Bukkit.getConsoleSender());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (getServerVersion() >= 1.13) {
            tab.setHeader(config.getStringList("TabList.header"));
            tab.setFooter(config.getStringList("TabList.footer"));

            Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    tab.update(player);
                }
            }, 0L, config.getInt("TabList.refresh") * 20L);
        }
        updateWorld();
    }

    @Override
    public void onDisable() {
        this.files = null;
    }

    private void registerCommands() {
        this.getServer().getPluginCommand("ecolobby").setExecutor(new LobbyCommand());
        this.getServer().getPluginCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getServer().getPluginCommand("spawn").setExecutor(new SpawnCommand());
    }
    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new HiderListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemsListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new LeaveListeners(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new PreJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    private void updateWorld() {
        for (World world : Bukkit.getWorlds()) {
            world.setTime(config.getLong("World.time"));
            ConfigurationSection rules = config.getConfigurationSection("World.rules");

            for (String rule : rules.getKeys(false)) {
                world.setGameRuleValue(rule, rules.getString(rule));
            }
        }
    }

    private void checkUpdate() {
        new Update().getVersion(version ->
        {
            if (!this.getDescription().getVersion().equals(version)) {
				updateAvailable = true;
                sendMessage(MESSAGES.NEW_VERSION, Bukkit.getConsoleSender());
            }
        });
    }

    public Float getServerVersion() {
        String version = Bukkit.getVersion();
        String pattern = "[^0-9\\.\\:]";
        String versionMinecraft = version.replaceAll(pattern, "");

        return Float.parseFloat(versionMinecraft.substring(versionMinecraft.indexOf(":") + 1, versionMinecraft.lastIndexOf(".")));
    }

    public void reload() {
        this.files.reload();
        this.loadConfigurations();

        if (getServerVersion() >= 1.13) {
            tab.setHeader(config.getStringList("TabList.header"));
            tab.setFooter(config.getStringList("TabList.footer"));
        }
        updateWorld();
    }

    private void loadLanguage() {
        messages = this.files.get("language/messages.yml");

        if (config.getString("language").equalsIgnoreCase("ru"))
            messages = this.files.get("language/messages_ru.yml");
    }
    private void loadConfigurations() {
        config = this.files.get("config.yml");
        spawn = this.files.get("spawn.yml");
        items = this.files.get("items.yml");

        this.loadLanguage();

        if (!Objects.equals(config.get("config_version"), this.getDescription().getVersion())) {

            File file = new File(this.getDataFolder(), "config.yml");
            file.renameTo(new File(this.getDataFolder(), "config.yml-old"));

            this.files.reload();
        }
    }
}
