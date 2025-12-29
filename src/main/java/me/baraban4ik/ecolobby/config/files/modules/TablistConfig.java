package me.baraban4ik.ecolobby.config.files.modules;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.TablistPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;

@Getter
public class TablistConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private FormattedMessage header;
    private FormattedMessage footer;
    private FormattedMessage prefix;
    private FormattedMessage suffix;

    @Override
    protected void loadValues() {
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