package me.baraban4ik.ecolobby.configurations;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configurations
{
    private Map<String, Map.Entry<FileConfiguration, File>> configurations = new HashMap<> ();

    private List<String> configurationsNames;

    private final Plugin plugin;

    public Configurations(Plugin plugin, String... configurationsNames)
    {
        this.plugin = plugin;

        this.configurationsNames = Lists.newArrayList(configurationsNames);

        this.loadConfigurations();
    }


    private File generateDefaultFile(String name)
    {
        File file = new File(this.plugin.getDataFolder(), name);

        if (!file.exists())
        {
            this.plugin.saveResource(name, false);
        }

        return file;
    }

    public void loadConfigurations()
    {
        for (String configurationName : this.configurationsNames)
        {
            if (this.configurations.containsKey(configurationName))
            {
                continue;
            }

            File configurationFile = this.generateDefaultFile(configurationName);

            FileConfiguration configuration = YamlConfiguration.loadConfiguration(configurationFile);

            this.configurations.put(configurationName, new AbstractMap.SimpleEntry<> (configuration, configurationFile));
        }
    }

    public void reloadConfigurations()
    {
        this.configurations.clear();

        this.loadConfigurations();
    }

    private Optional<Map.Entry<FileConfiguration, File>> getEntry(String configurationName)
    {
        return Optional.ofNullable(this.configurations.get(configurationName));
    }

    public FileConfiguration get(String configurationName)
    {
        return this.getEntry(configurationName)
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private File getFile(String configurationName)
    {
        return this.getEntry(configurationName)
                .map(Map.Entry::getValue)
                .orElse(null);
    }


    public FileConfiguration save(FileConfiguration fileConfiguration, String s) {
        try {
            fileConfiguration.save(new File(plugin.getDataFolder(), s));
        } catch (IOException var3) {
            var3.printStackTrace();
        }
        return fileConfiguration;
    }
}
