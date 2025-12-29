package me.baraban4ik.ecolobby.config.files.modules;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.ScoreboardPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;

@Getter
public class ScoreboardConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private FormattedMessage title;
    private FormattedMessage lines;

    @Override
    protected void loadValues() {
        enabled = getBoolean(ScoreboardPath.ENABLED);
        refresh = getInt(ScoreboardPath.REFRESH);

        title = getFormattedMessage(ScoreboardPath.TITLE);
        lines = getFormattedMessageList(ScoreboardPath.LINES);
    }

    @Override
    public String getPath() {
        return FilePath.SCOREBOARD.get();
    }
}
