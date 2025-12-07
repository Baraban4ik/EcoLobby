package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.TablistPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.configuration.ConfigurationSection;
@Getter
public class TablistConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private FormattedMessage header;
    private FormattedMessage footer;
    private FormattedMessage prefix;
    private FormattedMessage suffix;


    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

        enabled = getBoolean(TablistPath.ENABLED);
        refresh = getInt(TablistPath.REFRESH);

        header = getFormattedMessageList(TablistPath.HEADER);
        footer = getFormattedMessageList(TablistPath.FOOTER);
        prefix = getFormattedMessage(TablistPath.PREFIX);
        suffix = getFormattedMessage(TablistPath.SUFFIX);
    }

    @Override
    public String getPath() {
        return FilePath.TABLIST.get();
    }
}
