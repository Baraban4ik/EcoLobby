package me.baraban4ik.ecolobby;

import lombok.Getter;
import me.baraban4ik.ecolobby.commands.lobby.LobbyCommand;
import me.baraban4ik.ecolobby.commands.spawn.SetSpawnCommand;
import me.baraban4ik.ecolobby.commands.spawn.SpawnCommand;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.gui.GUI;
import me.baraban4ik.ecolobby.gui.GUIData;
import me.baraban4ik.ecolobby.gui.GUIListener;
import me.baraban4ik.ecolobby.listeners.*;
import me.baraban4ik.ecolobby.managers.AmbientManager;
import me.baraban4ik.ecolobby.managers.WorldsManager;
import me.baraban4ik.ecolobby.managers.display.AbstractDisplayManager;
import me.baraban4ik.ecolobby.managers.display.BossbarManager;
import me.baraban4ik.ecolobby.managers.display.ScoreboardManager;
import me.baraban4ik.ecolobby.managers.display.TabManager;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import me.baraban4ik.ecolobby.utils.UpdateChecker;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class EcoLobby extends JavaPlugin {

    public static boolean PLACEHOLDER_API = false;
    public static boolean NOTE_BLOCK_API = false;

    public static boolean UPDATE_AVAILABLE = false;
    public static String UPDATE_VERSION = "";

    @Getter private static EcoLobby instance;
    @Getter private static ConfigManager configManager;

    @Getter private Set<AbstractDisplayManager<?>> displayManagers;
    @Getter private AmbientManager ambientManager;

    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        instance = this;
        audiences = BukkitAudiences.create(this);

        configManager = new ConfigManager(this);
        displayManagers = new HashSet<>(Arrays.asList(
                new TabManager(),
                new BossbarManager(),
                new ScoreboardManager()
        ));
        ambientManager = new AmbientManager();
        ambientManager.start();

        WorldsManager.apply();
        refreshDisplays();

        if (ConfigManager.getConfig().isCheckUpdates()) this.checkUpdate();

        PLACEHOLDER_API = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        NOTE_BLOCK_API = getServer().getPluginManager().isPluginEnabled("NoteBlockAPI");

        new Metrics(this, 14978);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        registerCommands();
        registerListeners();

        PluginMessageSender.console(PluginMessage.ENABLE_MESSAGE);
    }

    @Override
    public void onDisable() {
        if (audiences != null) audiences.close();

        displayManagers.forEach(AbstractDisplayManager::stop);
        ambientManager.stop();
    }

    public void reload() {
        configManager.reload();

        WorldsManager.apply();
        refreshDisplays();

        for (Player player : Bukkit.getOnlinePlayers()) {
            GUI gui = GUIData.getGUI(player.getOpenInventory().getTopInventory());
            if (gui != null) gui.update(player);
        }
    }

    private void refreshDisplays() {
        for (AbstractDisplayManager<?> displayManager : displayManagers) {
            if (displayManager.isEnabled()) {
                displayManager.start();
                Bukkit.getOnlinePlayers().forEach(displayManager::send);
            }
            else displayManager.stop();
        }
    }

    private void registerCommands() {
        getServer().getPluginCommand("ecolobby").setExecutor(new LobbyCommand());
        getServer().getPluginCommand("spawn").setExecutor(new SpawnCommand());
        getServer().getPluginCommand("setspawn").setExecutor(new SetSpawnCommand());
    }

    private void registerListeners() {
        List<Listener> listeners = Arrays.asList(
                new BlockListener(),
                new ChatListener(),
                new CommandListener(),
                new DamageListener(),
                new HiderListener(),
                new ItemListener(),
                new JoinListener(),
                new LeaveListener(),
                new MovementListener(),
                new PreJoinListener(),
                new WorldListener(),
                new GUIListener()

        );

        listeners.forEach(listener ->
            getServer().getPluginManager().registerEvents(listener, this)
        );
    }

    private void checkUpdate() {
        new UpdateChecker().getVersion(version ->
        {
            if (!this.getDescription().getVersion().equals(version)) {
                UPDATE_AVAILABLE = true;
                UPDATE_VERSION = version;

                PluginMessageSender.sendUpdateMessage(Bukkit.getConsoleSender());
            }
        });

    }

    public Float getServerVersion() {
        String version = Bukkit.getBukkitVersion().split("-")[0];
        String[] versionParts = version.split("\\.");

        return Float.parseFloat(versionParts[0] + "." + versionParts[1] + versionParts[2]);
    }

    public Audience getAudience(CommandSender sender) {
        return audiences.sender(sender);
    }
}
