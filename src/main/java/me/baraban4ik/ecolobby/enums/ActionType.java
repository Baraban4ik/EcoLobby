package me.baraban4ik.ecolobby.enums;

public enum ActionType {
    PLAYER("[PLAYER]"),
    CONSOLE("[CONSOLE]"),
    CONNECT("[CONNECT]"),
    MSG("[MSG]"),
    MM("[MM]"),
    BROADCAST("[BROADCAST]"),
    TELEPORT_TO_SPAWN("[TELEPORT_TO_SPAWN]"),
    TITLE("[TITLE]"),
    ACTIONBAR("[ACTIONBAR]"),
    SOUND("[SOUND]"),
    HIDE_PLAYERS("[HIDE_PLAYERS]"),
    SHOW_PLAYERS("[SHOW_PLAYERS]");

    private final String tag;

    ActionType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static ActionType getActionType(String action) {
        for (ActionType type : values()) {
            if (action.startsWith(type.tag) || action.startsWith(type.tag + " ")) {
                return type;
            }
        }
        return null;
    }
}
