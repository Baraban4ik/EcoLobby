package me.baraban4ik.ecolobby.message;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.MessagesConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {

    public static String format(CommandSender sender, String raw) {
        if (sender instanceof Player) {
            raw = replacePlayerPlaceholders((Player) sender, raw);
        }

        return applyColors(replaceCommon(raw));
    }

    public static String offlineFormat(OfflinePlayer player, String raw) {
        return applyColors(replacePlayerPlaceholders(player, replaceCommon(raw)));
    }

    private static String replacePlayerPlaceholders(OfflinePlayer player, String raw) {
        String playerName = player.getName();

        if (playerName != null) {
            raw = raw.replace("%player%", playerName);
        }

        if (EcoLobby.PLACEHOLDER_API)
            raw = PlaceholderAPI.setPlaceholders(player, raw);

        return raw;
    }

    private static String replaceCommon(String raw) {
        int countOnlinePlayers = Bukkit.getOnlinePlayers().size();
        MessagesConfig messagesConfig = ConfigManager.getMessagesConfig();

        raw = raw.replace("%prfx%", messagesConfig.getPrefix())
                .replace("%online%", String.valueOf(countOnlinePlayers))
                .replace("%NL%", "\n");

        return raw;
    }

    private static String applyColors(String raw) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(raw);

        while (matcher.find()) {
            String color = raw.substring(matcher.start(), matcher.end());
            raw = raw.replace(color, ChatColor.of(color).toString());
            matcher = pattern.matcher(raw);
        }
        return ChatColor.translateAlternateColorCodes('&', raw);
    }
}
