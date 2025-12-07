package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.message.MessageFormatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class MiniMessageAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        action = MessageFormatter.format(player, action);
        Component message = MiniMessage.miniMessage().deserialize(action);

        Audience audience = EcoLobby.getInstance().getAudience(player);
        audience.sendMessage(message);
    }
}