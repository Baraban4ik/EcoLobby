package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.WorldsConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class WorldsManager {

    public static void apply() {
        WorldsConfig worldsConfig = ConfigManager.getWorldsConfig();

        for (World world : Bukkit.getWorlds()) {
            ConfigurationSection rules = worldsConfig.getRules(world);

            for (String rule : rules.getKeys(false)) {
                GameRule<?> gameRule = GameRule.getByName(rule);
                if (gameRule == null) continue;

                String value = rules.getString(rule);
                Object parsedValue = parseValue(gameRule, value);

                if (parsedValue != null) {
                    world.setGameRule((GameRule<Object>) gameRule, parsedValue);
                }
            }
            world.setTime(worldsConfig.getTime(world));
        }
    }

    private static Object parseValue(GameRule<?> gameRule, String value) {
        try {
            if (gameRule.getType() == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (gameRule.getType() == Integer.class) {
                return Integer.parseInt(value);
            }
        } catch (Exception ignored) {}
        return null;
    }
}
