package me.baraban4ik.ecolobby.gui;

import me.baraban4ik.ecolobby.managers.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;



public class GUIListener implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!GUIData.containsInventory(event.getInventory())) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null) {

            if (ItemManager.isEcoItem(clickedItem)) {
                GUI gui = GUIData.getGUI(event.getClickedInventory());
                if (gui != null) gui.click(player, clickedItem);

            }
        }
    }

}
