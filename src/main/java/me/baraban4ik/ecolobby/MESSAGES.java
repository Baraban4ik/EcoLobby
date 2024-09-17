package me.baraban4ik.ecolobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;


import java.util.Arrays;
import java.util.List;


public class MESSAGES {

    public static List<Component> HELP_MESSAGE = Arrays.asList(
            Component.space(),
            Component.text(" EcoLobby", TextColor.color(0xAEE495))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("help page", TextColor.color(0xf2ede0))),
            Component.space(),
            Component.text(" /el help", TextColor.color(0xf2ede0))
                    .append(Component.text(" - open this page", NamedTextColor.GRAY)),
            Component.text(" /el reload", TextColor.color(0xf2ede0))
                    .append(Component.text(" - Reload plugin configuration", NamedTextColor.GRAY)),
            Component.text(" /el setspawn", TextColor.color(0xf2ede0))
                    .append(Component.text(" (main/first) - sets the main or first spawn", NamedTextColor.GRAY)),
            Component.text(" /el spawn", TextColor.color(0xf2ede0))
                    .append(Component.text(" - teleport to the spawn", NamedTextColor.GRAY)),
            Component.text(" /el give", TextColor.color(0xf2ede0))
                    .append(Component.text(" (player) <item> - give out a custom item", NamedTextColor.GRAY)),
            Component.space()
    );


    public static List<Component> ENABLE_MESSAGE = Arrays.asList(
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


    public static Component ONLY_PLAYER = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("This command is only available to players!", TextColor.color(0xf2ede0)))
            .build();


    public static Component GIVE_USAGE = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" usage: ", TextColor.color(0xAEE495)))
            .append(Component.text(") ", NamedTextColor.GRAY))
            .append(Component.space())
            .append(Component.text("/el give", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("(player) <item>", TextColor.color(0xf2ede0)))
            .build();

    public static Component SETSPAWN_USAGE = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" usage: ", TextColor.color(0xAEE495)))
            .append(Component.text(") ", NamedTextColor.GRAY))
            .append(Component.space())
            .append(Component.text("/el setspawn", TextColor.color(0xf2ede0)))
            .append(Component.space())
            .append(Component.text("(main/first)", TextColor.color(0xf2ede0)))
            .build();

    public static Component PLUGIN_RELOADED = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("Plugin successfully reloaded!", TextColor.color(0xf2ede0)))
            .build();

    public static Component ITEM_NOT_FOUND = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("Item with this name not found!", TextColor.color(0xf2ede0)))
            .build();

    public static Component SUCCESSFULLY_GIVE_ITEM = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("Item issued successfully!", TextColor.color(0xf2ede0)))
            .build();

    public static Component PLAYER_NOT_FOUND = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("Player is not present on the server!", TextColor.color(0xf2ede0)))
            .build();

    public static Component SUCCESSFULLY_SETSPAWN = Component.text()
            .append(Component.text(" (", NamedTextColor.GRAY))
            .append(Component.text(" EL ", TextColor.color(0xAEE495)))
            .append(Component.text(")  ", NamedTextColor.GRAY))
            .append(Component.text("Spawn successfully installed!", TextColor.color(0xf2ede0)))
            .build();
}
