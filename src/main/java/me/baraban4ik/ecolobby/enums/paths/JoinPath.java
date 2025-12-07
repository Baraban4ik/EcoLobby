package me.baraban4ik.ecolobby.enums.paths;

public enum JoinPath implements IPath {

    TELEPORT_TO_SPAWN("teleport_to_spawn"),
    ACTIONS("actions"),
    CLEAR_CHAT("clear_chat"),
    MUSIC_ENABLED("music.enabled"),
    MUSIC_TRACKS("music.tracks"),
    MUSIC_REPEAT("music.repeat"),
    MUSIC_RANDOM("music.random");

    private final String path;

    JoinPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
