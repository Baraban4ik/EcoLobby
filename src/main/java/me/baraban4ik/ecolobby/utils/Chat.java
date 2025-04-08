package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.getInstance;
import static me.baraban4ik.ecolobby.utils.Configurations.messages;
import static me.baraban4ik.ecolobby.utils.Format.format;

public class Chat {


    public static void sendMessage(Component message, CommandSender sender) {
        Audience audience = BukkitAudiences.create(EcoLobby.getInstance()).sender(sender);
        audience.sendMessage(message);
    }

    public static void sendMessage(List<Component> message, CommandSender sender) {
        Audience audience = BukkitAudiences.create(getInstance()).sender(sender);
        message.forEach(audience::sendMessage);
    }

    public static void sendPathMessage(Path path, CommandSender sender) {
        String msg = messages.getString(path.getPath());
        if (msg == null || msg.isEmpty()) return;

        sender.sendMessage(format(msg, sender));
    }
}
