package me.baraban4ik.ecolobby.config;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.files.*;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.config.files.WorldsConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigManager {

    private final Map<String, FileConfiguration> configurations = new HashMap<>();

    private final JavaPlugin plugin;
    private final File pluginFolder;

    private final String YML = ".yml";
    private final String GUIS_FOLDER = "guis/";
    private final String ITEMS_FOLDER = "items/";

    @Getter private static Config config;
    @Getter private static MessagesConfig messagesConfig;
    @Getter private static BossbarConfig bossbarConfig;
    @Getter private static TablistConfig tablistConfig;
    @Getter private static ScoreboardConfig scoreboardConfig;
    @Getter private static JoinConfig joinConfig;
    @Getter private static SpawnConfig spawnConfig;
    @Getter private static PlayerConfig playerConfig;
    @Getter private static WorldsConfig worldsConfig;

    private final List<AbstractConfig> iConfigList;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginFolder = plugin.getDataFolder();

        iConfigList = Arrays.asList(
                config = new Config(),
                messagesConfig = new MessagesConfig(),
                bossbarConfig = new BossbarConfig(),
                scoreboardConfig = new ScoreboardConfig(),
                tablistConfig = new TablistConfig(),
                joinConfig = new JoinConfig(),
                spawnConfig = new SpawnConfig(),
                playerConfig = new PlayerConfig(),
                worldsConfig = new WorldsConfig()
        );

        this.load();
    }

    private void load() {
        for (FilePath filePath : FilePath.values()) {
            String fullPath = filePath.get() + YML;
            File file = new File(pluginFolder, fullPath);

            if (!file.exists()) {
                plugin.saveResource(fullPath, false);
            }

            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configurations.put(filePath.get(), configuration);
        }
        File tracksFolder = new File(pluginFolder, "tracks");

        if (!tracksFolder.exists()) {
            tracksFolder.mkdirs();
        }

        loadGUIsConfigs();
        loadItemsConfigs();

        iConfigList.forEach(iConfig ->
                iConfig.initialize(getFileConfiguration(iConfig.getPath()))
        );
    }

    private void loadGUIsConfigs() {
        this.loadConfigDirectory(GUIS_FOLDER,"example_gui");
    }

    private void loadItemsConfigs() {
        this.loadConfigDirectory(ITEMS_FOLDER,"example_item");
    }

    private void loadConfigDirectory(String folderPath, String exampleFilePath) {
        File folder = new File(pluginFolder, folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
            plugin.saveResource(folderPath + exampleFilePath + YML, false);
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(YML));

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().replace(YML, "");
                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                configurations.put(folderPath + fileName, configuration);
            }
        }
    }

    public void reload() {
        configurations.clear();
        load();
    }

    private void save(FileConfiguration fileConfiguration, String name) {
        File file = new File(pluginFolder, name + YML);

        try {
            fileConfiguration.save(file);
            reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(AbstractConfig configFile, String path, Object value) {
        FileConfiguration yaml = getFileConfiguration(configFile.getPath());
        yaml.set(path, value);

        this.save(yaml, configFile.getPath());
    }

    public List<String> getGUIsNames() {
        return configurations.keySet().stream()
                .filter(key -> key.startsWith(GUIS_FOLDER))
                .map(key -> key.split(GUIS_FOLDER)[1])
                .collect(Collectors.toList());
    }
    public List<String> getItemsNames() {
        return configurations.keySet().stream()
                .filter(key -> key.startsWith(ITEMS_FOLDER))
                .map(key -> key.split(ITEMS_FOLDER)[1])
                .collect(Collectors.toList());
    }

    private FileConfiguration getFileConfiguration(String path) {
        return configurations.get(path);
    }

    public ItemConfig getItemConfig(String itemID) {
        FileConfiguration yaml = getFileConfiguration(ITEMS_FOLDER + itemID);

        if (yaml == null) return null;

        ItemConfig itemConfig = new ItemConfig();
        itemConfig.initialize(yaml);

        return itemConfig;
    }

    public ItemConfig getItemConfig(ConfigurationSection section) {
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.initialize(section);

        return itemConfig;
    }

    public GUIConfig getGUIConfig(String guiID) {
        FileConfiguration yaml = getFileConfiguration(GUIS_FOLDER + guiID);

        GUIConfig guiConfig = new GUIConfig();
        guiConfig.initialize(yaml);

        return guiConfig;
    }

}
