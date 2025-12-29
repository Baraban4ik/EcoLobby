package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.PlayerConfig;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class DenyUtils {

    private static final PlayerConfig playerConfig = ConfigManager.getPlayerConfig();

    public static void spawnDenyParticleEffect(Player player, Location location) {
        if (playerConfig.isBlocksUseParticleEffect()) player.playEffect(location, Effect.SMOKE, BlockFace.UP);
    }
    public static void sendDenyMessage(Player player, FormattedMessage message) {
        if (playerConfig.isBlocksUseDenyMessage())
            message.send(player);
    }
    public static  void playDenySound(Player player, boolean isDenySound, Sound sound) {
        if (isDenySound) player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }
}
