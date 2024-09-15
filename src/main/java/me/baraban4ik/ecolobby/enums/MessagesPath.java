package me.baraban4ik.ecolobby.enums;

public enum MessagesPath {

    PREFIX("prefix"),
    NO_PERMISSION("no_permission"),

    WHITELIST_KICK("whitelist_kick"),
    BLACKLIST_KICK("blacklist_kick"),

    DENY_CHAT("deny_chat"),
    DENY_COMMANDS("deny_commands"),
    DENY_BREAK_BLOCKS("deny_break_blocks"),
    DENY_PLACE_BLOCKS("deny_place_blocks"),
    DENY_INTERACT_BLOCKS("deny_interact_blocks"),

    TELEPORTED_SPAWN("teleported_spawn"),
    NULL_SPAWN("null_spawn");

    private final String path;

    MessagesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
