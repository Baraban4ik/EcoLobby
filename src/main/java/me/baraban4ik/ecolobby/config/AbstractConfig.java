package me.baraban4ik.ecolobby.config;

import me.baraban4ik.ecolobby.enums.paths.IPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public abstract class AbstractConfig {

    protected ConfigurationSection config;


    public void initialize(ConfigurationSection config) {
        this.config = config;
    }

    public String getPath() {
        return "";
    }

    protected String getString(IPath path) {
        return config.getString(path.get());
    }

    protected String getString(IPath path, String def) {
        return config.getString(path.get(), def);
    }

    protected List<String> getStringList(IPath path) {
        return config.getStringList(path.get());
    }

    protected FormattedMessage getFormattedMessage(IPath path) {
        return new FormattedMessage(getString(path));
    }

    protected FormattedMessage getFormattedMessageList(IPath path) {
        return new FormattedMessage(getStringList(path));
    }

    protected boolean getBoolean(IPath path) {
        return config.getBoolean(path.get());
    }

    protected boolean getBoolean(IPath path, boolean def) {
        return config.getBoolean(path.get(), def);
    }

    protected int getInt(IPath path) {
        return config.getInt(path.get());
    }
    protected int getInt(IPath path, int def) {
        return config.getInt(path.get(), def);
    }

    protected List<Integer> getIntList(IPath path) {
        return config.getIntegerList(path.get());
    }

    protected ConfigurationSection getConfigurationSection(IPath path) {
        return config.getConfigurationSection(path.get());
    }
}
