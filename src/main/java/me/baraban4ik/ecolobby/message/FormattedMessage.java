package me.baraban4ik.ecolobby.message;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FormattedMessage {

    private final String raw;
    private final List<String> rawList;

    public FormattedMessage(String raw) {
        this.raw = raw;
        this.rawList = new ArrayList<>();
    }

    public FormattedMessage(List<String> rawList) {
        this.raw = null;
        this.rawList = rawList;
    }

    public void send(CommandSender sender) {
        if (raw != null) {
            sender.sendMessage(toString(sender));
            return;
        }

        if (rawList != null) {
            toList(sender).forEach(sender::sendMessage);
        }
    }

    public String toString(CommandSender sender) {
        if (raw != null) return MessageFormatter.format(sender, raw);
        return MessageFormatter.format(sender, convert());
    }

    public String toStringOffline(OfflinePlayer player) {
        if (raw == null) return "";
        return MessageFormatter.offlineFormat(player, raw);
    }

    public List<String> toList(CommandSender sender) {
        if (rawList == null) return Collections.emptyList();

        return rawList.stream()
                .map(line -> MessageFormatter.format(sender, line))
                .collect(Collectors.toList());
    }

    private String convert() {
        return String.join("\n", rawList);
    }

    @Override
    public String toString() {
        return raw != null ? raw : convert();
    }
}
