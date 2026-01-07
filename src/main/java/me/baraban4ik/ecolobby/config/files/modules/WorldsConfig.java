package me.baraban4ik.ecolobby.config.files.modules;

import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.WorldsPath;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class WorldsConfig extends AbstractConfig {

    private int defaultTime;
    private ConfigurationSection defaultRules;

    private boolean defaultVoidEnabled;
    private double defaultVoidHeight;
    private List<String> defaultVoidActions;

    private boolean defaultAmbientEnabled;
    private int defaultAmbientRadius;
    private int defaultAmbientAmount;
    private Particle defaultAmbientParticle;

    @Override
    protected void loadValues() {
        defaultTime = getInt(WorldsPath.DEFAULT_TIME, 6000);
        defaultRules = getConfigurationSection(WorldsPath.DEFAULT_RULES);

        defaultVoidEnabled = getBoolean(WorldsPath.DEFAULT_VOID_ENABLED, true);
        defaultVoidHeight = getInt(WorldsPath.DEFAULT_VOID_HEIGHT, 0);
        defaultVoidActions = getStringList(WorldsPath.DEFAULT_VOID_ACTIONS);

        defaultAmbientEnabled = getBoolean(WorldsPath.DEFAULT_AMBIENT_ENABLED, true);
        defaultAmbientRadius = getInt(WorldsPath.DEFAULT_AMBIENT_RADIUS, 20);
        defaultAmbientAmount = getInt(WorldsPath.DEFAULT_AMBIENT_AMOUNT, 6);
        defaultAmbientParticle = getEnum(
                WorldsPath.DEFAULT_AMBIENT_PARTICLE,
                Particle.class, Particle.FIREWORKS_SPARK
        );
    }

    public long getTime(World world) {
        String path = world.getName() + WorldsPath.TIME.get();
        return config.getLong(path, defaultTime);
    }

    public ConfigurationSection getRules(World world) {
        String path = world.getName() + WorldsPath.RULES.get();
        ConfigurationSection section = config.getConfigurationSection(path);

        return section != null ? section : defaultRules;
    }

    public boolean isVoidEnabled(World world) {
        String path = world.getName() + WorldsPath.VOID_ENABLED.get();
        return config.getBoolean(path, defaultVoidEnabled);
    }

    public double getVoidHeight(World world) {
        String path = world.getName() + WorldsPath.VOID_HEIGHT.get();
        return config.getDouble(path, defaultVoidHeight);
    }
    public List<String> getVoidActions(World world) {
        String path = world.getName() + WorldsPath.VOID_ACTIONS.get();
        return config.contains(path) ? config.getStringList(path) : defaultVoidActions;
    }

    public boolean isAmbientEnabled(World world) {
        String path = world.getName() + WorldsPath.AMBIENT_ENABLED.get();
        return config.getBoolean(path, defaultAmbientEnabled);
    }

    public int getAmbientRadius(World world) {
        String path = world.getName() + WorldsPath.AMBIENT_RADIUS.get();
        return config.getInt(path, defaultAmbientRadius);
    }

    public int getAmbientAmount(World world) {
        String path = world.getName() + WorldsPath.AMBIENT_AMOUNT.get();
        return config.getInt(path, defaultAmbientAmount);
    }

    public Particle getAmbientParticle(World world) {
        String path = world.getName() + WorldsPath.AMBIENT_PARTICLE.get();

        if (!config.contains(path)) {
            return defaultAmbientParticle;
        }

        return getEnum(
                config.getString(world.getName() + WorldsPath.AMBIENT_PARTICLE.get()),
                Particle.class, Particle.FIREWORKS_SPARK
        );
    }

    @Override
    public String getPath() {
        return FilePath.WOLRDS.get();
    }
}
