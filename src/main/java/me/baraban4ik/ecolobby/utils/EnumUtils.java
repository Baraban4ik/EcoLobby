package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.EcoLobby;
import org.bukkit.Sound;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class EnumUtils {

    private static final Set<String> loggedErrors = new HashSet<>();

    @SuppressWarnings("unchecked")
    public static <E> E parseEnum(String value, Class<E> enumClass, E defaultValue) {
        if (enumClass == Sound.class) {
            return (E) parseSound(value, (Sound) defaultValue);
        }

        if (Enum.class.isAssignableFrom(enumClass)) {
            try {
                return (E) Enum.valueOf((Class<Enum>) enumClass, value.toUpperCase());
            } catch (Exception e) {
                logError(enumClass, value, defaultValue);
                return defaultValue;
            }
        }

        logError(enumClass, value, defaultValue);
        return defaultValue;
    }

    private static Sound parseSound(String value, Sound defaultValue) {
        String name = value.toUpperCase();

        try {
            return Sound.valueOf(name);
        } catch (Throwable ignored) {}

        try {
            Method fromName = Sound.class.getDeclaredMethod("fromName", String.class);
            Object result = fromName.invoke(null, name);
            if (result != null) {
                return (Sound) result;
            }
        } catch (Throwable ignored) {}

        logError(Sound.class, value, defaultValue);
        return defaultValue;
    }

    private static void logError(Class<?> enumClass, String value, Object defaultValue) {
        String key = enumClass.getSimpleName() + ":" + value.toUpperCase();

        if (!loggedErrors.contains(key)) {
            loggedErrors.add(key);
            Logger logger  = EcoLobby.getInstance().getLogger();

            if (defaultValue == null) {
                logger.warning("Incorrect value '" + value + "' for " + enumClass.getSimpleName());
            }
            else
            {
                EcoLobby.getInstance().getLogger().warning(
                        "Incorrect value '" + value + "' for " + enumClass.getSimpleName() +
                                ", default value will be used: " + defaultValue
                );
            }
        }
    }
}
