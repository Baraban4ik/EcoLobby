package me.baraban4ik.ecolobby.utils;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configurations {

    private final Map<String, FileConfiguration> configurations = new HashMap<>();

    private final List<String> names;
    private final JavaPlugin plugin;


    public Configurations(JavaPlugin plugin, String... names) {
        this.plugin = plugin;
        this.names = Lists.newArrayList(names);
        this.load();
    }

    public void load() {
        for (String name : names) {
            File config = new File(plugin.getDataFolder(), name +".yml");

            if (!config.exists()) {
                plugin.saveResource(name +".yml", false);
            }

            FileConfiguration configuration = YamlConfiguration.loadConfiguration(config);
            configurations.put(name, configuration);
        }
    }

    public void reload() {
        configurations.clear();
        this.load();
    }

    public FileConfiguration getConfig(String name) {
        return configurations.get(name);
    }

    public void save(FileConfiguration fileConfiguration, String name)  {
        File file = new File(plugin.getDataFolder(), name + ".yml");

        try { fileConfiguration.save(file); }
        catch (IOException var3) { var3.printStackTrace(); }
    }
}
