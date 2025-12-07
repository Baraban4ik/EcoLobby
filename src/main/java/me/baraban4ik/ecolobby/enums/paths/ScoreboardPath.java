package me.baraban4ik.ecolobby.enums.paths;

public enum ScoreboardPath implements IPath{

    ENABLED("enabled"),
    REFRESH("refresh"),
    TITLE("title"),
    LINES("lines");

    private final String path;

    ScoreboardPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
