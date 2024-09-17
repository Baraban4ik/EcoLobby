package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Format;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class MiniMessageAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        action = Format.replacePlaceholders(action, player);
        Component message = MiniMessage.miniMessage().deserialize(action);

        Chat.sendMessage(message, player);
    }
}