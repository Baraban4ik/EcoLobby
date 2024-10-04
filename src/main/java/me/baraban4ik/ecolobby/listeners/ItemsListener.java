package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.itemsConfig;

public class ItemsListener implements Listener {
    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        ConfigurationSection items = itemsConfig.getConfigurationSection(Path.ITEMS.getPath());
        if (items == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            for (String itemName : items.getKeys(false)) {
                if (ItemManager.isEcoItem(event.getItem(), itemName)) {
                    List<String> actions = items.getStringList(itemName + ".actions");
                    ActionManager.execute(event.getPlayer(), actions);
                }
            }
        }
    }
}
