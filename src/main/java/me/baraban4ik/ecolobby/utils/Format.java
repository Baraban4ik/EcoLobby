package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.EcoLobby;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.baraban4ik.ecolobby.EcoLobby.messages;

public class Format {
    public static String format(String text, CommandSender sender) {
        text = addHexSupport(text);
        text = replacePlaceholders(text, sender);

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String replacePlaceholders(String text, CommandSender sender) {
        text = text.replace("%prfx%", messages.getString("prefix", ""))
                .replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("%NL%", "\n");

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (EcoLobby.placeholderAPI) {
                text = PlaceholderAPI.setPlaceholders(player, text);
            }
            text = text.replace("%player%", player.getName());
        }
        return text;
    }

    private static String addHexSupport(String text) {
        if (EcoLobby.instance.getServerVersion() >= 1.16f) {
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
        return text;
    }
}
