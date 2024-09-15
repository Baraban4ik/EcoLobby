package me.baraban4ik.ecolobby.enums;

public enum ItemsPath {

    ITEMS("Items"),
    NAME("name"),
    LORE("lore"),
    MATERIAL("material"),
    AMOUNT("amount");

    private final String path;

    ItemsPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
