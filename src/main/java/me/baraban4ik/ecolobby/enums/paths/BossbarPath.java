package me.baraban4ik.ecolobby.enums.paths;

public enum BossbarPath implements IPath {

    ENABLED("enabled"),
    REFRESH("refresh"),
    PROGRESS("progress"),
    TITLE("title"),
    COLOR("color"),
    STYLE("style");

    private final String path;

    BossbarPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
