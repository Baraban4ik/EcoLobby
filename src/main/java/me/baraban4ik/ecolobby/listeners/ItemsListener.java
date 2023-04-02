package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.configurations.Configurations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemsListener implements Listener {
    private final Configurations config;

    public ItemsListener(Configurations configurations) {
        config = configurations;
    }

    // Items:
    @EventHandler
    public void MoveItems(InventoryClickEvent e) {
        if (!config.get("config.yml").getBoolean("Items.move-items")) {
            if (!e.getWhoClicked().hasPermission("ecolobby.bypass.moveitems") && !e.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void DropItems(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        if (!config.get("config.yml").getBoolean("Items.drop-items") && player.hasPermission("ecolobby.bypass.dropitems")) {
            e.setCancelled(true);
        }
    }
}
