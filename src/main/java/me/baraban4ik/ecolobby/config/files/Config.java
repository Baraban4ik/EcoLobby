package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.Language;
import me.baraban4ik.ecolobby.enums.paths.ConfigPath;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.utils.EnumUtils;

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
    protected void loadValues() {
        checkUpdates = getBoolean(ConfigPath.CHECK_UPDATES, true);
        language = EnumUtils.parseEnum(
                getString(ConfigPath.LANGUAGE),
                Language.class, Language.EN
        );

        whitelistEnabled = getBoolean(ConfigPath.WHITELIST_ENABLED);
        whitelistPlayers = getStringList(ConfigPath.WHITELIST_PLAYERS);

        blacklistEnabled = getBoolean(ConfigPath.BLACKLIST_ENABLED);
        blacklistPlayers = getStringList(ConfigPath.BLACKLIST_PLAYERS);

        leaveClearItems = getBoolean(ConfigPath.LEAVE_CLEAR_ITEMS);

        hideStreamAll = getBoolean(ConfigPath.HIDE_STREAM_ALL, true);
        hideStreamJoin = getBoolean(ConfigPath.HIDE_STREAM_JOIN);
        hideStreamLeave = getBoolean(ConfigPath.HIDE_STREAM_LEAVE);
        hideStreamDeath = getBoolean(ConfigPath.HIDE_STREAM_DEATH);
        hideStreamKick = getBoolean(ConfigPath.HIDE_STREAM_KICK);

        hidePlayers = getBoolean(ConfigPath.HIDE_PLAYERS, true);
    }
    @Override
    public String getPath() {
        return FilePath.CONFIG.get();
    }
}
