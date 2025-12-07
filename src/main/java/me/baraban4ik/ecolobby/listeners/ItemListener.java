package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.ItemConfig;
import me.baraban4ik.ecolobby.config.files.PlayerConfig;
import me.baraban4ik.ecolobby.enums.Permission;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.DenyUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class ItemListener implements Listener {

    private final PlayerConfig playerConfig = ConfigManager.getPlayerConfig();

    @EventHandler
    public void onMoveItems(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Permission.BYPASS_ITEMS_MOVE.has(player)) return;

            if (playerConfig.isItemsDisableMove()) {
                DenyUtils.playDenySound(player,
                        playerConfig.isItemsUseDenySound(),
                        playerConfig.getItemsDenySound()
                );
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (Permission.BYPASS_ITEMS_DROP.has(player)) return;

        if (playerConfig.isItemsDisableDrop()) {
            DenyUtils.playDenySound(player,
                    playerConfig.isItemsUseDenySound(),
                    playerConfig.getItemsDenySound());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItems(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Permission.BYPASS_ITEMS_PICKUP.has(player)) return;

            if (playerConfig.isItemsDisablePickup())
                event.setCancelled(true);

        }
    }

    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (ItemManager.isEcoItem(item)) {
                String itemId = ItemManager.getItemId(item);
                ItemConfig itemConfig = EcoLobby.getConfigManager().getItemConfig(itemId);

                ActionManager.execute(event.getPlayer(), itemConfig.getActions());
            }
        }
    }
}
