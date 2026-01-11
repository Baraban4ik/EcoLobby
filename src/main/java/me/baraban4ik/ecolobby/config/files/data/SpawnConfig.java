package me.baraban4ik.ecolobby.config.files.data;

import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.types.SpawnType;

import org.bukkit.Location;

public class SpawnConfig extends AbstractConfig {

    private Location mainSpawn;
    private Location firstSpawn;

    private final ConfigManager configManager;

    public SpawnConfig(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    protected void loadValues() {
        mainSpawn = config.getLocation(SpawnType.MAIN.name());
        firstSpawn = config.getLocation(SpawnType.FIRST.name());
    }

    public Location getSpawn(SpawnType type) {
        switch (type) {
            case MAIN: return mainSpawn;
            case FIRST: return firstSpawn;
            default: return null;
        }
    }

    public void setSpawn(SpawnType type, Location location) {
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
