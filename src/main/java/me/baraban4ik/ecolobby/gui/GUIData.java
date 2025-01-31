package me.baraban4ik.ecolobby.gui;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GUIData {

    private static final Map<Inventory, GUI>  inventories = new HashMap<>();


    public static void addInventory(Inventory inventory, GUI gui) {
        inventories.put(inventory, gui);
    }

    public static GUI getGUI(Inventory inventory) {
        return inventories.get(inventory);
    }

    public static boolean containsInventory(Inventory inventory) {
        return inventories.containsKey(inventory);
    }
}
