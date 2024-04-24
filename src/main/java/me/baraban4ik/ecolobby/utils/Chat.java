package me.baraban4ik.ecolobby.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import static me.baraban4ik.ecolobby.EcoLobby.messages;
import static me.baraban4ik.ecolobby.utils.Format.format;

public class Chat {
    public static void sendPathMessage(String path, CommandSender sender) {
        String msg = messages.getString(path);
        if (msg == null || msg.isEmpty()) return;

        sender.sendMessage(format(msg, sender));
    }

    public static void sendPathMessage(String path, CommandSender sender, boolean list) {
        if (list) {
            List<String> message = messages.getStringList(path);
            if (message.isEmpty()) return;

            message.forEach(x -> sender.sendMessage(format(x, sender)));
            return;
        }
        String msg = messages.getString(path);
        if (msg == null || msg.isEmpty()) return;

        sender.sendMessage(format(msg, sender));
    }

    public static void sendMessage(List<String> message, CommandSender sender) {
        if (message == null ||  message.isEmpty()) return;
        message.forEach(x -> sender.sendMessage(format(x, sender)));
    }
    public static void sendMessage(String message, CommandSender sender) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(format(message, sender));
    }

    public static void sendTitle(@NotNull Player player, String title, String subTitle) {
        player.sendTitle(format(title, player), format(subTitle, player), 10, 20, 70);
    }
    public static void sendActionBar(Player player, String actionMessage) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(format(actionMessage, player)));
    }
}
