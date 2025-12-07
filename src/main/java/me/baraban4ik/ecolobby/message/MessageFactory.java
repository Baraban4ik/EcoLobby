package me.baraban4ik.ecolobby.message;

import me.baraban4ik.ecolobby.enums.PluginMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import static net.kyori.adventure.text.Component.text;

public class MessageFactory {

    public static Component usage(String usageText) {
        return text().append(PluginMessage.USAGE.get()).append(text(usageText, TextColor.color(0xf2ede0))).build();
    }

    public static Component plain(String message, TextColor color) {
        return text(message, color);
    }

    public static Component prefixed(String message) {
        return text().append(PluginMessage.PREFIX.get()).append(text(message, TextColor.color(0xf2ede0))).build();
    }

    public static Component helpLine(String command, String description) {
        return Component.text()
                .append(plain(command, TextColor.color(0xf2ede0)))
                .append(plain(" " + description, NamedTextColor.GRAY))
                .build();
    }

    public static Component space() {
        return Component.empty();
    }
}
