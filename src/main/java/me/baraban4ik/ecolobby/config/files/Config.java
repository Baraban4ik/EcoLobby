package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.Language;
import me.baraban4ik.ecolobby.enums.paths.ConfigPath;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@Getter
public class Config extends AbstractConfig {

    private boolean checkUpdates;
    private Language language;

    private boolean whitelistEnabled;
    private List<String> whitelistPlayers;

    private boolean blacklistEnabled;
    private List<String> blacklistPlayers;

    private boolean leaveClearItems;

    private boolean hideStreamAll;
    private boolean hideStreamJoin;
    private boolean hideStreamLeave;
    private boolean hideStreamDeath;
    private boolean hideStreamKick;

    private boolean hidePlayers;

    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

        checkUpdates = config.getBoolean(ConfigPath.CHECK_UPDATES.get(), true);
        language = Language.valueOf(config.getString(ConfigPath.LANGUAGE.get(), "EN").toUpperCase());

        whitelistEnabled = config.getBoolean(ConfigPath.WHITELIST_ENABLED.get());
        whitelistPlayers = config.getStringList(ConfigPath.WHITELIST_PLAYERS.get());

        blacklistEnabled = config.getBoolean(ConfigPath.BLACKLIST_ENABLED.get(), false);
        blacklistPlayers = config.getStringList(ConfigPath.BLACKLIST_PLAYERS.get());

        leaveClearItems = config.getBoolean(ConfigPath.LEAVE_CLEAR_ITEMS.get());

        hideStreamAll = config.getBoolean(ConfigPath.HIDE_STREAM_ALL.get(), true);
        hideStreamJoin = config.getBoolean(ConfigPath.HIDE_STREAM_JOIN.get());
        hideStreamLeave = config.getBoolean(ConfigPath.HIDE_STREAM_LEAVE.get());
        hideStreamDeath = config.getBoolean(ConfigPath.HIDE_STREAM_DEATH.get());
        hideStreamKick = config.getBoolean(ConfigPath.HIDE_STREAM_KICK.get());

        hidePlayers = config.getBoolean(ConfigPath.HIDE_PLAYERS.get(), true);
    }
    @Override
    public String getPath() {
        return FilePath.CONFIG.get();
    }
}
