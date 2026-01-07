package me.baraban4ik.ecolobby.config.files.modules;

import lombok.Getter;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.PlayerPath;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
@Getter
public class PlayerConfig extends AbstractConfig {

    private GameMode gameMode;
    private double health;
    private int foodLevel;
    private int levelExp;

    private boolean disableChat;
    private boolean disableCommands;
    private boolean disableCommandsTabComplete;
    private List<String> disableCommandsAllowed;

    private boolean disableDamage;
    private boolean disableHunger;
    private boolean disableMovements;
    private boolean disableFly;

    private boolean blocksDisableBreak;
    private boolean blocksDisablePlace;
    private boolean blocksDisableInteract;

    private boolean blocksUseDenyMessage;
    private boolean blocksUseParticleEffect;
    private boolean blocksUseDenySound;
    private Sound blocksDenySound;

    private boolean itemsDisableMove;
    private boolean itemsDisableDrop;
    private boolean itemsDisablePickup;
    private boolean itemsUseDenySound;
    private Sound itemsDenySound;

    @Override
    protected void loadValues() {
        gameMode = getEnum(
                PlayerPath.GAMEMODE,
                GameMode.class, GameMode.ADVENTURE
        );
        health = getInt(PlayerPath.HEALTH);
        foodLevel = getInt(PlayerPath.FOOD_LEVEL);
        levelExp = getInt(PlayerPath.LEVEL_EXP);

        disableChat = getBoolean(PlayerPath.DISABLE_CHAT);
        disableCommands = getBoolean(PlayerPath.DISABLE_COMMANDS);
        disableCommandsTabComplete = getBoolean(PlayerPath.DISABLE_COMMANDS_TAB_COMPLETE);
        disableCommandsAllowed = getStringList(PlayerPath.DISABLE_COMMANDS_ALLOWED);

        disableDamage = getBoolean(PlayerPath.DISABLE_DAMAGE);
        disableHunger = getBoolean(PlayerPath.DISABLE_HUNGER);
        disableMovements = getBoolean(PlayerPath.DISABLE_MOVEMENTS);
        disableFly = getBoolean(PlayerPath.DISABLE_FLY);

        blocksDisableBreak = getBoolean(PlayerPath.BLOCKS_DISABLE_BREAK);
        blocksDisablePlace = getBoolean(PlayerPath.BLOCKS_DISABLE_PLACE);
        blocksDisableInteract = getBoolean(PlayerPath.BLOCKS_DISABLE_INTERACT);

        blocksUseDenyMessage = getBoolean(PlayerPath.BLOCKS_USE_DENY_MESSAGE);
        blocksUseParticleEffect = getBoolean(PlayerPath.BLOCKS_USE_PARTICLE_EFFECT);
        blocksUseDenySound = getBoolean(PlayerPath.BLOCKS_USE_DENY_SOUND);
        blocksDenySound = getEnum(
                PlayerPath.BLOCKS_DENY_SOUND,
                Sound.class, Sound.BLOCK_NOTE_BLOCK_PLING
        );

        itemsDisableMove = getBoolean(PlayerPath.ITEMS_DISABLE_MOVE);
        itemsDisableDrop = getBoolean(PlayerPath.ITEMS_DISABLE_DROP);
        itemsDisablePickup = getBoolean(PlayerPath.ITEMS_DISABLE_PICKUP);
        itemsUseDenySound = getBoolean(PlayerPath.ITEMS_USE_DENY_SOUND);
        itemsDenySound = getEnum(
                PlayerPath.ITEMS_DENY_SOUND,
                Sound.class, Sound.BLOCK_NOTE_BLOCK_PLING
        );
    }

    public List<PotionEffect> getEffects() {
        List<PotionEffect> effects = new ArrayList<>();

        for (String effect : getStringList(PlayerPath.EFFECTS)) {
            String[] parts = effect.split(":");
            if (parts.length < 1) continue;

            PotionEffectType type = PotionEffectType.getByName(parts[0].toUpperCase());
            if (type == null) continue;

            int level = 0;
            int time = 0;
            boolean particles = false;

            try {
                if (parts.length > 1) level = Integer.parseInt(parts[1]) - 1;
                if (parts.length > 2) time = Integer.parseInt(parts[2]) * 20;
                if (parts.length > 3) particles = Boolean.parseBoolean(parts[3]);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning("Invalid potion effect format: " + effect);
                continue;
            }

            int duration = (time == 0)
                    ? (EcoLobby.getInstance().getServerVersion() < 1.194 ? Integer.MAX_VALUE : -1)
                    : time;

            effects.add(new PotionEffect(type, duration, level, false, particles));
        }
        return effects;
    }

    @Override
    public String getPath() {
        return FilePath.PLAYER.get();
    }
}
