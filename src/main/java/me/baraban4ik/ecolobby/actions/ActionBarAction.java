package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.message.MessageFormatter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageFormatter.format(player, action)));
    }
}