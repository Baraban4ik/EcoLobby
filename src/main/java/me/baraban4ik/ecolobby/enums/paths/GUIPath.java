package me.baraban4ik.ecolobby.enums.paths;

public enum GUIPath implements IPath {
    TITLE("title"),
    SIZE("size"),
    ITEMS("Items");

    private final String path;

    GUIPath(String path) {
        this.path = path;
    }

    @Override
    public String get() {
        return path;
    }
}
