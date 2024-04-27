package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Format;
import me.baraban4ik.ecolobby.utils.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;


public class ActionManager {
    public static void execute(Player player, List<String> actions) {

        actions.forEach(action -> {
            if (actionType(action, "[PLAYER]")) {
                action = replaceVoid(action, player, "[PLAYER]");
                player.performCommand(action);
            }
            else if (actionType(action, "[CONSOLE]")) {
                action = replaceVoid(action, player, "[CONSOLE]");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action);
            }
            else if (actionType(action, "[CONNECT]")) {
                action = replaceVoid(action, player, "[CONNECT]");
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(data);
                try {
                    out.writeUTF("Connect");
                    out.writeUTF(action);
                } catch (Exception ignored) {}
                player.sendPluginMessage(EcoLobby.instance, "BungeeCord", data.toByteArray());
                try {
                    out.close();
                    data.close();
                } catch (Exception ignored) {}
            }
            else if (actionType(action, "[MSG]")) {
                action = replaceVoid(action, player, "[MSG]");
                player.sendMessage(Format.format(action, player));

            }
            else if (actionType(action, "[BROADCAST]")) {
                action = replaceVoid(action, player, "[BROADCAST]");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Format.format(action, player));
                }
            }
            else if (actionType(action, "[TELEPORT_TO_SPAWN]")) {
                Location spawn = Spawn.getSpawn("main");
                if (spawn != null) player.teleport(spawn);
            }
            else if (actionType(action, "[TITLE]")) {
                action = replaceVoid(action, player, "[TITLE]");
                String[] actionArgs = action.split(";");

                Chat.sendTitle(player, actionArgs[0], actionArgs[1]);
            }
            else if (actionType(action, "[ACTIONBAR]")) {
                action = replaceVoid(action, player, "[ACTIONBAR]");
                Chat.sendActionBar(player, action);
            }
            else if (actionType(action, "[SOUND]")) {
                action = replaceVoid(action, player, "[SOUND]");
                String[] actionArgs = action.split(";");

                Sound sound = Sound.valueOf(actionArgs[0].toUpperCase());

                float volume = 1;
                float pitch = 1;

                if (actionArgs.length == 3) {
                    volume = Float.parseFloat(actionArgs[1]);
                    pitch = Float.parseFloat(actionArgs[2]);
                }
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
            else player.sendMessage(Format.format(action, player));
        });
    }
    private static String replaceVoid(@NotNull String action, @NotNull Player player, String target) {
        action = action.replace(target + " ", "").replace(target, "");
        return Format.replacePlaceholders(action, player);
    }
    private static boolean actionType(@NotNull String sectionAction, String action) {
        return sectionAction.startsWith(action) || sectionAction.startsWith(action + " ");
    }
}
