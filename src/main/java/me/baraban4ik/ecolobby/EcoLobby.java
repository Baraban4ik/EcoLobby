package me.baraban4ik.ecolobby;

import me.baraban4ik.ecolobby.commands.LobbyCommand;
import me.baraban4ik.ecolobby.commands.SetSpawnCommand;
import me.baraban4ik.ecolobby.commands.SpawnCommand;
import me.baraban4ik.ecolobby.enums.ConfigPath;
import me.baraban4ik.ecolobby.enums.TablistPath;
import me.baraban4ik.ecolobby.listeners.*;
import me.baraban4ik.ecolobby.managers.TabManager;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Configurations;
import me.baraban4ik.ecolobby.utils.Update;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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

    public static boolean PLACEHOLDER_API = false;
    public static boolean NOTE_BLOCK_API = false;

    public static boolean UPDATE_AVAILABLE = false;
    public static String UPDATE_VERSION = "";

    TabManager tab = new TabManager();

    @Override
    public void onLoad() {
        instance = this;
        configurations = new Configurations(this,
                "config",
                "language/messages",
                "language/messages_ru",
                "spawn",
                "tablist",
                "items"
        );
        configurations.load();
        this.loadConfigurations();
    }

    @Override
    public void onEnable() {
        if (config.getBoolean(ConfigPath.CHECK_UPDATES.getPath())) this.checkUpdate();

        PLACEHOLDER_API = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        NOTE_BLOCK_API = getServer().getPluginManager().isPluginEnabled("NoteBlockAPI");

        this.registerCommands();
        this.registerListeners();

        new Metrics(this, 14978);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (tablistConfig.getBoolean(TablistPath.ENABLED.getPath()))
            this.sendTablist();
        updateWorld();

        Chat.sendMessage(MESSAGES.ENABLE_MESSAGE, Bukkit.getConsoleSender());
    }

    private void sendTablist() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                tab.update(player);
            }
        }, 0L, tablistConfig.getInt(TablistPath.REFRESH.getPath()) * 20L);
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
    }

    private void updateWorld() {
        for (World world : Bukkit.getWorlds()) {
            ConfigurationSection rules = config.getConfigurationSection(ConfigPath.WORLD_RULES.getPath());

            if (rules == null) return;
            long time = config.getLong(ConfigPath.WORLD_TIME.getPath());

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

                List<Component> NEW_VERSION = Arrays.asList(
                        Component.text(" __       ", TextColor.color(0xAEE495)),
                        Component.text("|__", TextColor.color(0xAEE495))
                                .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                                .append(Component.text("Plugin update available!", TextColor.color(0xf2ede0))),
                        Component.text("|__", TextColor.color(0xAEE495))
                                .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                                .append(Component.text("Current version", TextColor.color(0xf2ede0)))
                                .append(Component.text(" — ", NamedTextColor.GRAY))
                                .append(Component.text(instance.getDescription().getVersion(), TextColor.color(0xAEE495)))
                                .append(Component.text(" (", NamedTextColor.GRAY))
                                .append(Component.text(EcoLobby.UPDATE_VERSION, TextColor.color(0xAEE495)))
                                .append( Component.text(")", NamedTextColor.GRAY)),
                        Component.space()
                );
                sendMessage(NEW_VERSION, Bukkit.getConsoleSender());
            }
        });
    }

    public void reload() {
        configurations.reload();
        this.loadConfigurations();

        if (tablistConfig.getBoolean(TablistPath.ENABLED.getPath())) {
            Bukkit.getOnlinePlayers().forEach(player -> tab.update(player));
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

        if (config.getString(ConfigPath.LANGUAGE.getPath()).equalsIgnoreCase("ru"))
            messages = configurations.getConfig("language/messages_ru");
    }

    private void loadConfigurations() {
        config = configurations.getConfig("config");
        spawnConfig = configurations.getConfig("spawn");
        itemsConfig = configurations.getConfig("items");
        tablistConfig = configurations.getConfig("tablist");

        this.loadLanguage();

        if (!Objects.equals(config.get(ConfigPath.CONFIG_VERSION.getPath()), this.getDescription().getVersion())) {

            File file = new File(this.getDataFolder(), "config.yml");
            file.renameTo(new File(this.getDataFolder(), "config.yml-old"));

            configurations.reload();
        }
    }
}
