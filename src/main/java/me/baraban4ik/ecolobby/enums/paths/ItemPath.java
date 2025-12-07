package me.baraban4ik.ecolobby.enums.paths;

public enum ItemPath implements IPath {

    NAME("name"),
    LORE("lore"),
    MATERIAL("material"),
    SLOT("slot"),
    SLOTS("slots"),
    AMOUNT("amount"),
    MODEL_DATA("model_data"),
    ENCHANTMENTS("enchantments"),
    FLAGS("flags"),
    ACTIONS("actions"),
    JOIN_GIVE("join_give"),
    REFRESH("refresh");

    private final String path;

    ItemPath(String path) {
        this.path = path;
    }

    @Override
    public String get() {
        return path;
    }
}
