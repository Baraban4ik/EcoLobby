package me.baraban4ik.ecolobby.enums.paths;

public enum FilePath implements IPath{
    CONFIG("config"),
    MESSAGES("language/messages"),
    MESSAGES_RU("language/messages_ru"),
    SPAWN("data/spawn"),
    TABLIST("modules/tablist"),
    BOSSBAR("modules/bossbar"),
    SCOREBOARD("modules/scoreboard"),
    JOIN("modules/join"),
    PLAYER("modules/player"),
    WOLRDS("modules/worlds");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
