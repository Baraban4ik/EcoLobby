package me.baraban4ik.ecolobby.enums.paths;

public enum WorldsPath implements IPath{

    DEFAULT_TIME("default.time"),
    DEFAULT_RULES("default.rules"),
    DEFAULT_VOID_ENABLED("default.void.enabled"),
    DEFAULT_VOID_HEIGHT("default.void.height"),
    DEFAULT_VOID_ACTIONS("default.void.actions"),
    DEFAULT_AMBIENT_ENABLED("default.ambient.enabled"),
    DEFAULT_AMBIENT_RADIUS("default.ambient.radius"),
    DEFAULT_AMBIENT_AMOUNT("default.ambient.amount"),
    DEFAULT_AMBIENT_PARTICLE("default.ambient.particle"),

    TIME(".time"),
    RULES(".rules"),
    VOID_ENABLED(".void.enabled"),
    VOID_HEIGHT(".void.height"),
    VOID_ACTIONS(".void.actions"),
    AMBIENT_ENABLED(".ambient.enabled"),
    AMBIENT_RADIUS(".ambient.radius"),
    AMBIENT_AMOUNT(".ambient.amount"),
    AMBIENT_PARTICLE(".ambient.particle");

    private final String path;

    WorldsPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}
