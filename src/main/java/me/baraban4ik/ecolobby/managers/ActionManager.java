package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.SpawnType;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Format;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import static me.baraban4ik.ecolobby.utils.Format.format;
import static me.baraban4ik.ecolobby.utils.Format.replacePlaceholders;


public class ActionManager {
    public static void execute(Player player, List<String> actions) {
        actions.forEach(action -> {
            ActionType actionType = ActionType.getActionType(action);

            if (actionType == null) {
                player.sendMessage(Format.format(action, player));
                return;
            }
            action = replaceVoid(action, player, actionType.getTag());
            processAction(player, action, actionType);
        });
    }

    private static void processAction(Player player, String action, ActionType actionType) {
        switch (actionType) {
            case PLAYER:
                player.performCommand(action);
                break;
            case CONSOLE:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action);
                break;
            case CONNECT:
                connectPlayerToServer(player, action);
                break;
            case MSG:
                player.sendMessage(Format.format(action, player));
                break;
            case MM:
                sendMiniMessage(player, action);
                break;
            case BROADCAST:
                broadcastMessage(action, player);
                break;
            case TELEPORT_TO_SPAWN:
                teleportToSpawn(player);
                break;
            case TITLE:
                sendTitle(player, action);
                break;
            case ACTIONBAR:
                sendActionBar(player, action);
                break;
            case SOUND:
                playSound(player, action);
                break;
        }
    }

    private static void connectPlayerToServer(Player player, String serverName) {
        try (ByteArrayOutputStream data = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(data)) {

            out.writeUTF("Connect");
            out.writeUTF(serverName);

            player.sendPluginMessage(EcoLobby.getInstance(), "BungeeCord", data.toByteArray());
        } catch (Exception ignored) {}
    }

    private static void sendMiniMessage(Player player, String action) {
        action = replacePlaceholders(action, player);
        Component message = MiniMessage.miniMessage().deserialize(action);

        Chat.sendMessage(message, player);
    }

    private static void broadcastMessage(String action, Player player) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                onlinePlayer.sendMessage(Format.format(action, player))
        );
    }

    private static void teleportToSpawn(Player player) {
        Location spawn = SpawnManager.getSpawn(SpawnType.MAIN);
        if (spawn != null) player.teleport(spawn);
    }


    public static void sendActionBar(Player player, String actionMessage) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(format(actionMessage, player)));
    }

    private static void sendTitle(Player player, String action) {
        String[] actionArgs = action.split(";");
        player.sendTitle(format(actionArgs[0], player), format(actionArgs[1], player), 10, 20, 70);
    }

    private static void playSound(Player player, String action) {
        String[] actionArgs = action.split(";");
        Sound sound = Sound.valueOf(actionArgs[0].toUpperCase());

        float volume = actionArgs.length >= 2 ? Float.parseFloat(actionArgs[1]) : 1;
        float pitch = actionArgs.length == 3 ? Float.parseFloat(actionArgs[2]) : 1;

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private static String replaceVoid(@NotNull String action, @NotNull Player player, String target) {
        return replacePlaceholders(action.replace(target + " ", "").replace(target, ""), player);
    }

    private enum ActionType {
        PLAYER("[PLAYER]"),
        CONSOLE("[CONSOLE]"),
        CONNECT("[CONNECT]"),
        MSG("[MSG]"),
        MM("[MM]"),
        BROADCAST("[BROADCAST]"),
        TELEPORT_TO_SPAWN("[TELEPORT_TO_SPAWN]"),
        TITLE("[TITLE]"),
        ACTIONBAR("[ACTIONBAR]"),
        SOUND("[SOUND]");

        private final String tag;

        ActionType(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public static ActionType getActionType(String action) {
            for (ActionType type : values()) {
                if (action.startsWith(type.tag) || action.startsWith(type.tag + " ")) {
                    return type;
                }
            }
            return null;
        }
    }
}
