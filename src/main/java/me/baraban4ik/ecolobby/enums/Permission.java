package me.baraban4ik.ecolobby.enums;

import me.baraban4ik.ecolobby.config.ConfigManager;
import org.bukkit.command.CommandSender;

public enum Permission {

    UPDATE_NOTIFY("ecolobby.notify"),

    BYPASS_CHAT("ecolobby.bypass.chat"),
    BYPASS_COMMANDS("ecolobby.bypass.commands"),
    BYPASS_MOVEMENTS("ecolobby.bypass.movements"),
    BYPASS_BLOCKS_BREAK("ecolobby.bypass.blocks.break"),
    BYPASS_BLOCKS_PLACE("ecolobby.bypass.blocks.place"),
    BYPASS_BLOCKS_INTERACT("ecolobby.bypass.blocks.interact"),
    BYPASS_ITEMS_MOVE("ecolobby.bypass.items.move"),
    BYPASS_ITEMS_DROP("ecolobby.bypass.items.drop"),
    BYPASS_ITEMS_PICKUP("ecolobby.bypass.items.pickup"),

    COMMAND("ecolobby.command"),
    COMMAND_RELOAD("ecolobby.command.reload"),
    COMMAND_GIVE("ecolobby.command.give"),
    COMMAND_OPEN("ecolobby.command.open"),
    COMMAND_SETSPAWN("ecolobby.command.setspawn"),
    COMMAND_SPAWN("ecolobby.command.spawn");

    private final String perm;

    Permission(String perm) {
        this.perm = perm;
    }

    public boolean has(CommandSender sender) {
        return sender.hasPermission(perm);
    }

    public boolean has(CommandSender sender, boolean denyMessage) {
        if (has(sender)) return true;

        if (denyMessage)
            ConfigManager.getMessagesConfig().getNoPermission().send(sender);
        return false;
    }
}
