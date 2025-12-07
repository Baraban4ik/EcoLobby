package me.baraban4ik.ecolobby.message;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.Language;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class PluginMessageSender {

    private static final EcoLobby plugin = EcoLobby.getInstance();

    public static void send(CommandSender sender, PluginMessage msg) {
        Audience audience = plugin.getAudience(sender);

        if (msg.isList()) {
            msg.getList().forEach(audience::sendMessage);
        } else {
            audience.sendMessage(msg.get());
        }
    }

    public static void console(PluginMessage msg) {
        send(Bukkit.getConsoleSender(), msg);
    }

    public static void sendUpdateMessage(CommandSender sender) {
        String currentVersion = EcoLobby.getInstance().getDescription().getVersion();

        List<Component> message = Arrays.asList(
                MessageFactory.plain(" __       ", TextColor.color(0xAEE495)),
                Component.text()
                        .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(" |    ", TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(
                                getLanguage() == Language.RU ? "Доступно обновление плагина!" : "Plugin update available!",
                                TextColor.color(0xf2ede0)))
                        .build(),
                Component.text()
                        .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(" |__  ", TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(
                                getLanguage() == Language.RU ? "Текущая версия" : "Current version",
                                TextColor.color(0xf2ede0)))
                        .append(MessageFactory.plain(" — ", NamedTextColor.GRAY))
                        .append(MessageFactory.plain(currentVersion, TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(" (", NamedTextColor.GRAY))
                        .append(MessageFactory.plain(EcoLobby.UPDATE_VERSION, TextColor.color(0xAEE495)))
                        .append(MessageFactory.plain(")", NamedTextColor.GRAY))
                        .build(),
                MessageFactory.space()
        );

        Audience audience = plugin.getAudience(sender);
        message.forEach(audience::sendMessage);
    }

    private static Language getLanguage() {
        return ConfigManager.getConfig().getLanguage();
    }
}
