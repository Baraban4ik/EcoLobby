package me.baraban4ik.ecolobby.config.files;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.types.SpawnType;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class SpawnConfig extends AbstractConfig {

    private Location mainSpawn;
    private Location firstSpawn;

    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

        mainSpawn = config.getLocation(SpawnType.MAIN.name());
        firstSpawn = config.getLocation(SpawnType.FIRST.name());
    }

    public Location getSpawn(SpawnType type) {
        switch (type) {
            case MAIN: return mainSpawn;
            case FIRST: return firstSpawn;
        }
        return null;
    }

    public void setSpawn(SpawnType type, Location location) {
        ConfigManager configManager = EcoLobby.getConfigManager();

        switch (type) {
            case MAIN:
                configManager.setValue(this, SpawnType.MAIN.name(), location);
                break;
            case FIRST:
                configManager.setValue(this, SpawnType.FIRST.name(), location);
                break;
        }
    }

    @Override
    public String getPath() {
        return FilePath.SPAWN.get();
    }
}
