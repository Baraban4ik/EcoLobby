package me.baraban4ik.ecolobby.enums.paths;

public enum TablistPath implements IPath{

    ENABLED("enabled"),
    REFRESH("refresh"),
    HEADER("header"),
    FOOTER("footer"),
    PREFIX("prefix"),
    SUFFIX("suffix");

    private final String path;

    TablistPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
