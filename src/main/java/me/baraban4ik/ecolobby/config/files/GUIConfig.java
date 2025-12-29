package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.GUIPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class GUIConfig extends AbstractConfig {

    private FormattedMessage title;
    private int size;
    private ConfigurationSection items;

    public GUIConfig(ConfigurationSection yaml) {
        initialize(yaml);
    }

    @Override
    protected void loadValues() {
        title = getFormattedMessage(GUIPath.TITLE);
        size = getInt(GUIPath.SIZE);
        items = getConfigurationSection(GUIPath.ITEMS);
    }
}