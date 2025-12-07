package me.baraban4ik.ecolobby.enums.paths;

public enum PlayerPath implements IPath{

    GAMEMODE("statistics.gamemode"),
    HEALTH("statistics.health"),
    FOOD_LEVEL("statistics.food_level"),
    LEVEL_EXP("statistics.level_exp"),
    EFFECTS("statistics.effects"),

    DISABLE_CHAT("restrictions.disable_chat"),
    DISABLE_COMMANDS("restrictions.disable_commands.enabled"),
    DISABLE_COMMANDS_TAB_COMPLETE("restrictions.disable_commands.tab_complete"),
    DISABLE_COMMANDS_ALLOWED("restrictions.disable_commands.allowed"),

    DISABLE_DAMAGE("restrictions.disable_damage"),
    DISABLE_HUNGER("restrictions.disable_hunger"),
    DISABLE_MOVEMENTS("restrictions.disable_movements"),
    DISABLE_FLY("restrictions.disable_fly"),

    BLOCKS_DISABLE_BREAK("restrictions.blocks.disable_break"),
    BLOCKS_DISABLE_PLACE("restrictions.blocks.disable_place"),
    BLOCKS_DISABLE_INTERACT("restrictions.blocks.disable_interact"),

    BLOCKS_USE_DENY_MESSAGE("restrictions.blocks.use_deny_message"),
    BLOCKS_USE_PARTICLE_EFFECT("restrictions.blocks.use_particle_effect"),
    BLOCKS_USE_DENY_SOUND("restrictions.blocks.use_deny_sound.enabled"),
    BLOCKS_DENY_SOUND("restrictions.blocks.use_deny_sound.sound"),

    ITEMS_DISABLE_MOVE("restrictions.items.disable_move"),
    ITEMS_DISABLE_DROP("restrictions.items.disable_drop"),
    ITEMS_DISABLE_PICKUP("restrictions.items.disable_pickup"),

    ITEMS_USE_DENY_SOUND("restrictions.items.use_deny_sound.enabled"),
    ITEMS_DENY_SOUND("restrictions.items.use_deny_sound.sound");

    private final String path;

    PlayerPath(String path) {
        this.path = path;
    }
    @Override
    public String get() {
        return path;
    }
}

