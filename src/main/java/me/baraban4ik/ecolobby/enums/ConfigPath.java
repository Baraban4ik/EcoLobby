package me.baraban4ik.ecolobby.enums;

public enum ConfigPath {

    // Main:
    CONFIG_VERSION("config_version"),
    CHECK_UPDATES("check_updates"),
    LANGUAGE("language"),

    // Whitelist:
    WHITELIST("Whitelist.enabled"),
    WHITELIST_PLAYERS("Whitelist.players"),

    // Blacklist:
    BLACKLIST("Blacklist.enabled"),
    BLACKLIST_PLAYERS("Blacklist.players"),

    // Join:
    TELEPORT_TO_SPAWN("Join.teleport_to_spawn"),
    JOIN_ACTIONS("Join.actions"),

    MUSIC("Join.music.enabled"),
    MUSIC_TRACKS("Join.music.tracks"),
    MUSIC_REPEAT("Join.music.repeat"),
    CLEAR_CHAT("Join.clear_chat"),

    // Leave:
    CLEAR_ITEMS("Leave.clear_items"),

    // Player:
    PLAYER_GAMEMODE("Player.gamemode"),
    PLAYER_HEALTH("Player.health"),
    PLAYER_FOOD_LEVEL("Player.food_level"),
    PLAYER_LEVEL_EXP("Player.level_exp"),
    PLAYER_EFFECTS("Player.effects"),

    // Player abilities:
    ABILITIES_CHAT("Player.abilities.disable_chat"),

    ABILITIES_COMMANDS("Player.abilities.disable_commands.enabled"),
    ABILITIES_COMMANDS_TAB_COMPLETE("Player.abilities.disable_commands.tab_complete"),
    ABILITIES_COMMANDS_ALLOWED("Player.abilities.disable_commands.allowed"),

    ABILITIES_DAMAGE("Player.abilities.disable_damage"),
    ABILITIES_HUNGER("Player.abilities.disable_hunger"),

    ABILITIES_MOVEMENTS("Player.abilities.disable_movements"),
    ABILITIES_FLY("Player.abilities.enable_fly"),

    // Player blocks abilities:
    ABILITIES_BLOCKS_BREAK("Player.abilities.blocks.disable_break"),
    ABILITIES_BLOCKS_PLACE("Player.abilities.blocks.disable_place"),
    ABILITIES_BLOCKS_INTERACT("Player.abilities.blocks.disable_interact"),

    ABILITIES_BLOCKS_DENY_MESSAGE("Player.abilities.blocks.use_deny_message"),
    ABILITIES_BLOCKS_PARTICLE_EFFECT("Player.abilities.blocks.use_particle_effect"),

    ABILITIES_BLOCKS_DENY_SOUND("Player.abilities.blocks.use_deny_sound.enabled"),
    ABILITIES_BLOCKS_DENY_SOUND_SOUND("Player.abilities.blocks.use_deny_sound.sound"),

    // Player items abilities:
    ABILITIES_ITEMS_MOVE("Player.abilities.items.disable_move"),
    ABILITIES_ITEMS_DROP("Player.abilities.items.disable_drop"),
    ABILITIES_ITEMS_PICKUP("Player.abilities.items.disable_pickup"),

    ABILITIES_ITEMS_DENY_SOUND("Player.abilities.items.use_deny_sound.enabled"),
    ABILITIES_ITEMS_DENY_SOUND_SOUND("Player.abilities.items.use_deny_sound.sound"),

    // World:
    WORLD_TIME("World.time"),
    WORLD_RULES("World.rules"),
    VOID("World.jump_into_the_void.enabled"),
    VOID_HEIGHT("World.jump_into_the_void.height"),
    VOID_ACTIONS("World.jump_into_the_void.actions"),

    // Hiders:
    HIDE_STREAM("Hiders.hide_stream"),
    HIDE_PLAYERS("Hiders.hide_players");

    private final String path;

    ConfigPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
