package me.baraban4ik.ecolobby.utils;

import com.google.common.collect.Lists;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Configurations {

    private final Map<String, FileConfiguration> configurations = new HashMap<>();
    private final Map<String, FileConfiguration> guis = new HashMap<>();

    private final List<String> names;
    private final JavaPlugin plugin;


    public static FileConfiguration config;
    public static FileConfiguration messages;

    public static FileConfiguration itemsConfig;
    public static FileConfiguration spawnConfig;
    public static FileConfiguration tablistConfig;
    public static FileConfiguration bossBarConfig;
    public static FileConfiguration scoreboardConfig;

    public Configurations(JavaPlugin plugin, String... names) {
        this.plugin = plugin;
        this.names = Lists.newArrayList(names);
        this.load();
    }

    private void load() {
        for (String name : names) {
            File config = new File(plugin.getDataFolder(), name +".yml");

            if (!config.exists()) {
                plugin.saveResource(name +".yml", false);
            }

            FileConfiguration configuration = YamlConfiguration.loadConfiguration(config);
            configurations.put(name, configuration);
        }
        this.loadConfigurations();
        this.loadGUIs();
    }

    private void loadGUIs() {
        if (config.getBoolean("gui_example")) {
            File gui_example = new File(plugin.getDataFolder(),  "guis/gui_example.yml");

            if (!gui_example.exists()) {
                plugin.saveResource("guis/gui_example.yml", false);
            }
        }

        File guisFolder = new File(plugin.getDataFolder(), "guis");

        if (!guisFolder.exists()) {
            guisFolder.mkdirs();
            return;
        }
        File[] files = guisFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().replace(".yml", ""); // Убираем расширение
                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                guis.put(fileName, configuration);
            }
        }
    }

    public List<String> getGUIs() {
        List<String> guiKeys = new ArrayList<>();
        for (Map.Entry<String, FileConfiguration> entry : guis.entrySet()) {
            guiKeys.add(entry.getKey());
        }
        return guiKeys;
    }


    private void loadLanguage() {
        messages = this.getConfig("language/messages");

        if (config.getString(Path.LANGUAGE.getPath()).equalsIgnoreCase("ru"))
            messages = this.getConfig("language/messages_ru");
    }

    private void loadConfigurations() {
        config = this.getConfig("config");
        spawnConfig = this.getConfig("spawn");
        itemsConfig = this.getConfig("items");
        tablistConfig = this.getConfig("tablist");
        bossBarConfig = this.getConfig("bossbar");
        scoreboardConfig = this.getConfig("scoreboard");

        this.loadLanguage();

        EcoLobby pl = EcoLobby.getInstance();

        if (!Objects.equals(config.get(Path.CONFIG_VERSION.getPath()), pl.getDescription().getVersion())) {

            File file = new File(pl.getDataFolder(), "config.yml");
            file.renameTo(new File(pl.getDataFolder(), "config.yml-old"));

            this.reload();
        }
    }


    public void reload() {
        configurations.clear();
        this.load();

        guis.clear();
        this.loadGUIs();
    }

    private FileConfiguration getConfig(String name) {
        return configurations.get(name);
    }
    public FileConfiguration getGui(String name) {
        return guis.get(name);
    }

    public void save(FileConfiguration fileConfiguration, String name)  {
        File file = new File(plugin.getDataFolder(), name + ".yml");

        try { fileConfiguration.save(file); }
        catch (IOException var3) { var3.printStackTrace(); }
    }
}
