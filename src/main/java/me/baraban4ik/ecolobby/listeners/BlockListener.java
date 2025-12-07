package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.MessagesConfig;
import me.baraban4ik.ecolobby.config.files.PlayerConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import me.baraban4ik.ecolobby.utils.DenyUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    private final PlayerConfig playerConfig = ConfigManager.getPlayerConfig();
    private final MessagesConfig messagesConfig = ConfigManager.getMessagesConfig();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (Permission.BYPASS_BLOCKS_BREAK.has(player)) return;

        if (playerConfig.isBlocksDisableBreak()) {
            notifyBlockDenied(messagesConfig.getDenyBreakBlocks(),
                    player, event.getBlock().getLocation());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.getPlayer() != null) cancelPlace(event.getPlayer(),
                event.getBlock().getLocation(), event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        cancelPlace(event.getPlayer(), event.getBlock().getLocation(), event);
    }

    private void cancelPlace(Player player, Location blockLocation, Cancellable event) {
        if (Permission.BYPASS_BLOCKS_PLACE.has(player)) return;

        if (playerConfig.isBlocksDisablePlace()) {
            notifyBlockDenied(messagesConfig.getDenyPlaceBlocks(),
                    player, blockLocation);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (Permission.BYPASS_BLOCKS_INTERACT.has(player)) return;
        if (!playerConfig.isBlocksDisableInteract()) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        boolean isRightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK;
        boolean isPhysical = event.getAction() == Action.PHYSICAL;

        if (isRightClick && block.getType().isInteractable() || (isPhysical && block.getType() == Material.FARMLAND)) {
            notifyBlockDenied(messagesConfig.getDenyInteractBlocks(),
                    player, block.getLocation());

            event.setCancelled(true);
        }
    }

    private void notifyBlockDenied(FormattedMessage message, Player player, Location blockLocation) {
        DenyUtils.spawnDenyParticleEffect(player, blockLocation);

        DenyUtils.sendDenyMessage(player, message);
        DenyUtils.playDenySound(player,
                playerConfig.isBlocksUseDenySound(),
                playerConfig.getBlocksDenySound());
    }
}
