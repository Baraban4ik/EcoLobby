package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.EcoLobby;

import me.baraban4ik.ecolobby.enums.Path;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.baraban4ik.ecolobby.EcoLobby.messages;

public class Format {

    public static String format(String string, CommandSender sender) {
        string = addHexSupport(string);
        string = replacePlaceholders(string, sender);

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String replacePlaceholders(String text, CommandSender sender) {
        String prefix = messages.getString(Path.PREFIX.getPath(), "");
        prefix = addHexSupport(prefix);

        text = text.replace("%prfx%", prefix)
                .replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("%NL%", "\n");

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (EcoLobby.PLACEHOLDER_API)
                text = PlaceholderAPI.setPlaceholders(player, text);

            text = text.replace("%player%", player.getName());
        }
        return text;
    }

    private static String addHexSupport(String text) {
        String hexText = text;

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(hexText);

        while (match.find()) {
            String color = hexText.substring(match.start(), match.end());
            hexText = hexText.replace(color, String.valueOf(ChatColor.of(color)));
            match = pattern.matcher(hexText);
        }
        return hexText;
    }
}