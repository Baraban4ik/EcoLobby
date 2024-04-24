package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.items;

public class ItemsListener implements Listener {
    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        ConfigurationSection itemsSection = items.getConfigurationSection("Items");

        if (event.getItem() == null) return;
        if (itemsSection == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            for (String itemName : itemsSection.getKeys(false)) {
                if (ItemManager.isEcoItem(event.getItem(), itemName)) {
                    List<String> actions = itemsSection.getStringList(itemName + ".actions");
                    ActionManager.execute(event.getPlayer(), actions);
                }
            }
        }
    }
}
