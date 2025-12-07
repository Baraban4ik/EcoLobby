package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.ScoreboardPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.configuration.ConfigurationSection;
@Getter
public class ScoreboardConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private FormattedMessage title;
    private FormattedMessage lines;

    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

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
