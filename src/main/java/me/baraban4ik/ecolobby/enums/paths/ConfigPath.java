package me.baraban4ik.ecolobby.enums.paths;

public enum ConfigPath implements IPath{

    CHECK_UPDATES("check_updates"),
    LANGUAGE("language"),

    WHITELIST_ENABLED("Whitelist.enabled"),
    WHITELIST_PLAYERS("Whitelist.players"),

    BLACKLIST_ENABLED("Blacklist.enabled"),
    BLACKLIST_PLAYERS("Blacklist.players"),

    LEAVE_CLEAR_ITEMS("Leave.clear_items"),
    HIDE_STREAM_ALL("Hiders.hide_stream.all"),
    HIDE_STREAM_JOIN("Hiders.hide_stream.join"),
    HIDE_STREAM_LEAVE("Hiders.hide_stream.leave"),
    HIDE_STREAM_DEATH("Hiders.hide_stream.death"),
    HIDE_STREAM_KICK("Hiders.hide_stream.kick"),
    HIDE_PLAYERS("Hiders.players");

    private final String path;

    ConfigPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
