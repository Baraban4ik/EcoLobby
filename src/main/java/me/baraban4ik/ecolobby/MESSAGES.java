package me.baraban4ik.ecolobby;

import me.baraban4ik.ecolobby.enums.Path;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.config;

public class MESSAGES {

    private static String getLanguage() {
        return config.getString(Path.LANGUAGE.getPath(), "en").toLowerCase();
    }

    private static List<Component> getMessage(List<Component> enMessage, List<Component> ruMessage) {
        return getLanguage().equals("ru") ? ruMessage : enMessage;
    }

    private static Component commandMessage(String command, String description) {
        return Component.text(command, TextColor.color(0xf2ede0))
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text(description, NamedTextColor.GRAY));
    }

    private static final Component PREFIX = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .build();

    private static final Component USAGE = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" usage: ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .build();


    private static final List<Component> NEW_VERSION = Arrays.asList(
            Component.text(" __       ", TextColor.color(0xAEE495)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                    .append(Component.text("Plugin update available!", TextColor.color(0xf2ede0))),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                    .append(Component.text("Current version", TextColor.color(0xf2ede0)))
                    .append(Component.text(" — ", NamedTextColor.GRAY))
                    .append(Component.text(EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xAEE495)))
                    .append(Component.text(" (", NamedTextColor.GRAY))
                    .append(Component.text("%new_version%", TextColor.color(0xAEE495)))
                    .append( Component.text(")", NamedTextColor.GRAY)),
            Component.space()
    );
    private static final List<Component> NEW_VERSION_RU = Arrays.asList(
            Component.text(" __       ", TextColor.color(0xAEE495)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                    .append(Component.text("Доступно обновление плагина!", TextColor.color(0xf2ede0))),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                    .append(Component.text("Текущая версия", TextColor.color(0xf2ede0)))
                    .append(Component.text(" — ", NamedTextColor.GRAY))
                    .append(Component.text(EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xAEE495)))
                    .append(Component.text(" (", NamedTextColor.GRAY))
                    .append(Component.text("%new_version%", TextColor.color(0xAEE495)))
                    .append( Component.text(")", NamedTextColor.GRAY)),
            Component.space()
    );


    private static final List<Component> HELP_MESSAGE = Arrays.asList(
            Component.space(),
            Component.text(" EcoLobby", TextColor.color(0xAEE495))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("help page", TextColor.color(0xf2ede0))),
            Component.space(),
            commandMessage("/el help", "open this page"),
            commandMessage("/el reload", "reload plugin configuration"),
            commandMessage("/el setspawn", "(main/first) - sets the main or first spawn"),
            commandMessage("/el spawn", "teleport to the spawn"),
            commandMessage("/el give", "(player) <item> - give out a custom item"),
            Component.space()
    );

    private static final List<Component> HELP_MESSAGE_RU = Arrays.asList(
            Component.space(),
            Component.text(" EcoLobby", TextColor.color(0xAEE495))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("страница помощи", TextColor.color(0xf2ede0))),
            Component.space(),
            commandMessage("/el help", "открыть эту страницу"),
            commandMessage("/el reload", "перезагрузить конфигурацию плагина"),
            commandMessage("/el setspawn", "(main/first) - установить основной или первый спавн"),
            commandMessage("/el spawn", "телепорт на спавн"),
            commandMessage("/el give", "(игрок) <предмет> - выдать кастомный предмет"),
            Component.space()
    );

    private static final List<Component> ENABLE_MESSAGE = Arrays.asList(
            Component.text(" __       ", TextColor.color(0xAEE495)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                    .append(Component.text("EcoLobby v" + EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xf2ede0)))
                    .append(Component.space())
                    .append(Component.text("(release)", NamedTextColor.GRAY)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                    .append(Component.text("Developer", TextColor.color(0xf2ede0)))
                    .append(Component.text(" — ", NamedTextColor.GRAY))
                    .append(Component.text("Baraban4ik", TextColor.color(0xAEE495))),
            Component.space()
    );

    private static final List<Component> ENABLE_MESSAGE_RU = Arrays.asList(
            Component.text(" __       ", TextColor.color(0xAEE495)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |    ", TextColor.color(0xAEE495)))
                    .append(Component.text("EcoLobby v" + EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xf2ede0)))
                    .append(Component.space())
                    .append(Component.text("(релиз)", NamedTextColor.GRAY)),
            Component.text("|__", TextColor.color(0xAEE495))
                    .append(Component.text(" |__  ", TextColor.color(0xAEE495)))
                    .append(Component.text("Разработчик", TextColor.color(0xf2ede0)))
                    .append(Component.text(" — ", NamedTextColor.GRAY))
                    .append(Component.text("Baraban4ik", TextColor.color(0xAEE495))),
            Component.space()
    );


    private static final Component ONLY_PLAYER = Component.text()
            .append(PREFIX)
            .append(Component.text("This command is only available to players!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component ONLY_PLAYER_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Эта команда доступна только игрокам!", TextColor.color(0xf2ede0)))
            .build();


    private static final Component GIVE_USAGE = Component.text()
            .append(USAGE)
            .append(Component.space())
            .append(Component.text("/el give", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("(player) <item>", TextColor.color(0xf2ede0)))
            .build();

    private static final Component GIVE_USAGE_RU = Component.text()
            .append(USAGE)
            .append(Component.space())
            .append(Component.text("/el give", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("(игрок) <предмет>", TextColor.color(0xf2ede0)))
            .build();

    private static final Component OPEN_USAGE = Component.text()
            .append(USAGE)
            .append(Component.space())
            .append(Component.text("/el open", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("<menu> (player)", TextColor.color(0xf2ede0)))
            .build();
    private static final Component OPEN_USAGE_RU = Component.text()
            .append(USAGE)
            .append(Component.space())
            .append(Component.text("/el open", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("<меню> (игрок)", TextColor.color(0xf2ede0)))
            .build();

    private static final Component SETSPAWN_USAGE = Component.text()
            .append(USAGE)
            .append(Component.space())
            .append(Component.text("/el setspawn", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("(main/first)", TextColor.color(0xf2ede0)))
            .build();

    private static final Component PLUGIN_RELOADED = Component.text()
            .append(PREFIX)
            .append(Component.text("Plugin successfully reloaded!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component PLUGIN_RELOADED_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Плагин успешно перезагружен!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component ITEM_NOT_FOUND = Component.text()
            .append(PREFIX)
            .append(Component.text("Item with this name not found!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component ITEM_NOT_FOUND_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Предмет с таким названием не найден!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component SUCCESSFULLY_GIVE_ITEM = Component.text()
            .append(PREFIX)
            .append(Component.text("You have successfully given an item!", TextColor.color(0xf2ede0)))
            .build();
    private static final Component SUCCESSFULLY_GIVE_ITEM_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Вы успешно получили предмет!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component SUCCESSFULLY_OPEN_MENU = Component.text()
            .append(PREFIX)
            .append(Component.text("You have successfully opened the menu!", TextColor.color(0xf2ede0)))
            .build();
    private static final Component SUCCESSFULLY_OPEN_MENU_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Вы успешно открыли меню!", TextColor.color(0xf2ede0)))
            .build();

    private static final Component PLAYER_NOT_FOUND = Component.text()
            .append(PREFIX)
            .append(Component.text("Player not found!", TextColor.color(0xf2ede0)))
            .build();
    private static final Component PLAYER_NOT_FOUND_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Игрок не найден!", TextColor.color(0xf2ede0)))
            .build();


    private static final Component SUCCESSFULLY_SETSPAWN = Component.text()
            .append(PREFIX)
            .append(Component.text("You have successfully set the spawn!", TextColor.color(0xf2ede0)))
            .build();
    private static final Component SUCCESSFULLY_SETSPAWN_RU = Component.text()
            .append(PREFIX)
            .append(Component.text("Вы успешно установили спавн!", TextColor.color(0xf2ede0)))
            .build();

    public static List<Component> NEW_VERSION(String version) {
        List<Component> newVersionMessage = getMessage(NEW_VERSION, NEW_VERSION_RU);
        List<Component> replacedNewVersionMessage = new ArrayList<>();

        for (Component message : newVersionMessage) {
            message = message.replaceText(Builder -> Builder.matchLiteral("%new_version%").replacement(version));
            replacedNewVersionMessage.add(message);
        }

        return replacedNewVersionMessage;
    }


    public static List<Component> HELP_MESSAGE() {
        return getMessage(HELP_MESSAGE, HELP_MESSAGE_RU);
    }

    public static List<Component> ENABLE_MESSAGE() {
        return getMessage(ENABLE_MESSAGE, ENABLE_MESSAGE_RU);
    }

    public static Component ONLY_PLAYER() {
        return getLanguage().equals("ru") ? ONLY_PLAYER_RU : ONLY_PLAYER;
    }

    public static Component GIVE_USAGE() {
        return getLanguage().equals("ru") ? GIVE_USAGE_RU : GIVE_USAGE;
    }

    public static Component SETSPAWN_USAGE() {
        return SETSPAWN_USAGE;
    }
    public static Component OPEN_USAGE() {
        return getLanguage().equals("ru") ? OPEN_USAGE_RU : OPEN_USAGE;
    }

    public static Component PLUGIN_RELOADED() {
        return getLanguage().equals("ru") ? PLUGIN_RELOADED_RU : PLUGIN_RELOADED;
    }

    public static Component ITEM_NOT_FOUND() {
        return getLanguage().equals("ru") ? ITEM_NOT_FOUND_RU : ITEM_NOT_FOUND;
    }

    public static Component SUCCESSFULLY_GIVE_ITEM() {
        return getLanguage().equals("ru") ? SUCCESSFULLY_GIVE_ITEM_RU : SUCCESSFULLY_GIVE_ITEM;
    }
    public static Component SUCCESSFULLY_OPEN_MENU() {
        return getLanguage().equals("ru") ? SUCCESSFULLY_OPEN_MENU_RU : SUCCESSFULLY_OPEN_MENU;
    }
    public static Component PLAYER_NOT_FOUND() {
        return getLanguage().equals("ru") ? PLAYER_NOT_FOUND_RU : PLAYER_NOT_FOUND;
    }

    public static Component SUCCESSFULLY_SETSPAWN() {
        return getLanguage().equals("ru") ? SUCCESSFULLY_SETSPAWN_RU : SUCCESSFULLY_SETSPAWN;
    }
}
