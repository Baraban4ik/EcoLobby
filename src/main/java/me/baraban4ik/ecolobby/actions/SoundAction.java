package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        String[] actionArgs = action.split(";");
        Sound sound = Sound.valueOf(actionArgs[0].toUpperCase());

        float volume = actionArgs.length >= 2 ? Float.parseFloat(actionArgs[1]) : 1;
        float pitch = actionArgs.length == 3 ? Float.parseFloat(actionArgs[2]) : 1;

        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}