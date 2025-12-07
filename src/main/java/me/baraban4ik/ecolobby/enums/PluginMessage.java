package me.baraban4ik.ecolobby.enums;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.message.MessageFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;


import java.util.Arrays;
import java.util.List;

public enum PluginMessage {

    PREFIX(Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .build()
    ),

    USAGE(Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" usage: ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .build()
    ),


    ONLY_PLAYER(
            MessageFactory.prefixed("This command is only available to players!"),
            MessageFactory.prefixed("Эта команда доступна только игрокам!")
    ),
    PLUGIN_RELOADED(
            MessageFactory.prefixed("Plugin successfully reloaded!"),
            MessageFactory.prefixed("Плагин успешно перезагружен!")
    ),


    GIVE_USAGE(
            MessageFactory.usage("/el give (player) <item>"),
            MessageFactory.usage("/el give (игрок) <предмет>")
    ),

    OPEN_USAGE(
            MessageFactory.usage("/el open <menu> (player)"),
            MessageFactory.usage("/el open <меню> (игрок)")
    ),
    SETSPAWN_USAGE(
            MessageFactory.usage("/el setspawn (main/first)")
    ),


    ITEM_NOT_FOUND(
            MessageFactory.prefixed("Item with this name not found!"),
            MessageFactory.prefixed("Предмет с таким названием не найден!")
    ),
    GUI_NOT_FOUND(
            MessageFactory.prefixed("GUI with this name not found!"),
            MessageFactory.prefixed("GUI с таким названием не найден!")
    ),
    PLAYER_NOT_FOUND(
            MessageFactory.prefixed("Player not found!"),
            MessageFactory.prefixed("Игрок не найден!")
    ),

    SUCCESSFULLY_OPEN_MENU(
            MessageFactory.prefixed("You have successfully opened the menu!"),
            MessageFactory.prefixed("Вы успешно открыли меню!")
    ),
    SUCCESSFULLY_GIVE_ITEM(
            MessageFactory.prefixed("You have successfully given an item!"),
            MessageFactory.prefixed("Вы успешно получили предмет!")
    ),
    SUCCESSFULLY_SETSPAWN(
            MessageFactory.prefixed("You have successfully set the spawn!"),
            MessageFactory.prefixed("Вы успешно установили спавн!")
    ),

    HELP_MESSAGE(
            Arrays.asList(
                    MessageFactory.space(),
                    Component.text()
                            .append(MessageFactory.plain(" EcoLobby", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" | ", NamedTextColor.GRAY))
                            .append(MessageFactory.plain("help page", TextColor.color(0xf2ede0)))
                            .build(),
                    MessageFactory.space(),
                    MessageFactory.helpLine("/el help", "- open this page"),
                    MessageFactory.helpLine("/el reload", "- reload plugin configuration"),
                    MessageFactory.helpLine("/el setspawn", "(main/first) - sets the main/first spawn"),
                    MessageFactory.helpLine("/el spawn", "teleport to the spawn"),
                    MessageFactory.helpLine("/el give", "(player) <item> - give out a custom item"),
                    MessageFactory.space()
            ),
            Arrays.asList(
                    MessageFactory.space(),
                    Component.text()
                            .append(MessageFactory.plain(" EcoLobby", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" | ", NamedTextColor.GRAY))
                            .append(MessageFactory.plain("help page", TextColor.color(0xf2ede0)))
                            .build(),
                    MessageFactory.space(),
                    MessageFactory.helpLine("/el help", "- открыть эту страницу"),
                    MessageFactory.helpLine("/el reload", "- перезагрузить конфигурацию плагина"),
                    MessageFactory.helpLine("/el setspawn", "(main/first) - установить основной/первый спавн"),
                    MessageFactory.helpLine("/el spawn", "телепорт на спавн"),
                    MessageFactory.helpLine("/el give", "(игрок) <предмет> - выдать кастомный предмет"),
                    MessageFactory.space()
            )
    ),

    ENABLE_MESSAGE(
            Arrays.asList(
                    MessageFactory.plain(" __       ", TextColor.color(0xAEE495)),
                    Component.text()
                            .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" |    ", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain("EcoLobby v" + EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xf2ede0)))
                            .append(MessageFactory.plain(" (release)", NamedTextColor.GRAY))
                            .build(),
                    Component.text()
                            .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" |__  ", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain("Developer", TextColor.color(0xf2ede0)))
                            .append(MessageFactory.plain(" — ", NamedTextColor.GRAY))
                            .append(MessageFactory.plain("Baraban4ik", TextColor.color(0xAEE495)))
                            .build(),
                    MessageFactory.space()
            ),
            Arrays.asList(
                    MessageFactory.plain(" __       ", TextColor.color(0xAEE495)),
                    Component.text()
                            .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" |    ", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain("EcoLobby v" + EcoLobby.getInstance().getDescription().getVersion(), TextColor.color(0xf2ede0)))
                            .append(MessageFactory.plain(" (релиз)", NamedTextColor.GRAY))
                            .build(),
                    Component.text()
                            .append(MessageFactory.plain("|__", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain(" |__  ", TextColor.color(0xAEE495)))
                            .append(MessageFactory.plain("Разработчик", TextColor.color(0xf2ede0)))
                            .append(MessageFactory.plain(" — ", NamedTextColor.GRAY))
                            .append(MessageFactory.plain("Baraban4ik", TextColor.color(0xAEE495)))
                            .build(),
                    MessageFactory.space()
            )
    );

    private final Component en;
    private final Component ru;
    private final List<Component> enList;
    private final List<Component> ruList;

    PluginMessage(@NotNull Component both) {
        this.en = both;
        this.ru = both;
        this.enList = null;
        this.ruList = null;
    }
    PluginMessage(@NotNull Component en, @NotNull Component ru) {
        this.en = en;
        this.ru = ru;
        this.enList = null;
        this.ruList = null;
    }

    PluginMessage(@NotNull List<Component> enList, @NotNull List<Component> ruList) {
        this.enList = enList;
        this.ruList = ruList;
        this.en = null;
        this.ru = null;
    }

    private Language getLanguage() {
        return ConfigManager.getConfig().getLanguage();
    }

    public Component get() {
        switch (getLanguage()) {
            case EN: return en;
            case RU: return ru;
        }
        return en;
    }

    public List<Component> getList() {
        switch (getLanguage()) {
            case EN: return enList;
            case RU: return ruList;
        }
        return enList;
    }

    public boolean isList() {
        return enList != null && ruList != null;
    }
}
