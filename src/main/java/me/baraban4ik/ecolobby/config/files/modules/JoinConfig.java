package me.baraban4ik.ecolobby.config.files.modules;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.JoinPath;

import java.util.List;
@Getter
public class JoinConfig extends AbstractConfig {

    private boolean teleportToSpawn;
    private List<String> actions;
    private boolean clearChat;
    private boolean musicEnabled;
    private List<String> musicTracks;
    private boolean musicRepeat;
    private boolean musicRandom;

    @Override
    protected void loadValues() {
        teleportToSpawn = getBoolean(JoinPath.TELEPORT_TO_SPAWN);
        actions = getStringList(JoinPath.ACTIONS);
        clearChat = getBoolean(JoinPath.CLEAR_CHAT);
        musicEnabled = getBoolean(JoinPath.MUSIC_ENABLED);
        musicTracks = getStringList(JoinPath.MUSIC_TRACKS);
        musicRepeat = getBoolean(JoinPath.MUSIC_REPEAT);
        musicRandom = getBoolean(JoinPath.MUSIC_RANDOM);
    }

    @Override
    public String getPath() {
        return FilePath.JOIN.get();
    }
}
