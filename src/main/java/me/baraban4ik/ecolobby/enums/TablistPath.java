package me.baraban4ik.ecolobby.enums;

public enum TablistPath {
    ENABLED("enabled"),
    REFRESH("refresh"),
    HEADER("header"),
    FOOTER("footer"),

    PREFIX("tabPrefix"),
    SUFFIX("tabSuffix");

    private final String path;

    TablistPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
