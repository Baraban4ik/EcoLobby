package me.baraban4ik.ecolobby.gui;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    private final List<Integer> refreshableSlots = new ArrayList<>();


    private int size = 54;
    private String title;

    private ConfigurationSection items;
    private Inventory gui;

    private final String name;

    public GUI(String name) {
        this.name = name;
        loadData();
    }
    private void loadData() {
        FileConfiguration guiConfig = EcoLobby.getConfigurations().getGui(name);
        if (guiConfig == null) return;

        title = guiConfig.getString(Path.GUI_TITLE.getPath());
        size = guiConfig.getInt(Path.GUI_SIZE.getPath());
        items = guiConfig.getConfigurationSection(Path.ITEMS.getPath());
    }


    public void open(Player player) {
        gui = Bukkit.createInventory(null, size, Format.format(title, player));
        addItems(player);

        GUIData.addInventory(gui, this);
        player.openInventory(gui);
        refresh(player);
    }


    public void update(Player player) {
        loadData();
        open(player);
    }


    private void addItems(Player player) {
        if (items != null) {
            for (String itemID : items.getKeys(false)) {
                ConfigurationSection itemIDSection = items.getConfigurationSection(itemID);

                if (itemIDSection != null) {
                    ItemStack item = ItemManager.createItem(player, itemID, itemIDSection);
                    addItemToSlots(item, itemIDSection);

                    if (itemIDSection.getBoolean(Path.ITEM_REFRESH.getPath(), false)) {
                        List<Integer> slots = itemIDSection.getIntegerList(Path.ITEM_SLOTS.getPath());

                        if (!slots.isEmpty()) refreshableSlots.addAll(slots);
                        else refreshableSlots.add(itemIDSection.getInt(Path.ITEM_SLOT.getPath()));

                    }
                }
            }
        }
    }

    private void addItemToSlots(ItemStack item, ConfigurationSection itemID) {
        List<Integer> itemSlots = itemID.getIntegerList(Path.ITEM_SLOTS.getPath());

        if (!itemSlots.isEmpty()) itemSlots.forEach(slot -> gui.setItem(slot, item));
        else gui.setItem(itemID.getInt(Path.ITEM_SLOT.getPath()), item);

    }


    public void click(Player player, ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");

        if (!pdc.has(ECO_ITEM, PersistentDataType.STRING)) return;

        String itemID = pdc.get(ECO_ITEM, PersistentDataType.STRING);
        ConfigurationSection itemSection = items.getConfigurationSection(itemID);
        if (itemSection == null) return;

        List<String> actions = itemSection.getStringList(Path.ITEM_ACTIONS.getPath());
        ActionManager.execute(player, actions);
    }

    private void refresh(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (gui == null || !player.getOpenInventory().getTopInventory().equals(gui)) {
                    cancel();
                    return;
                }
                if (items != null) {
                    for (String itemID : items.getKeys(false)) {
                        ConfigurationSection itemIDSection = items.getConfigurationSection(itemID);

                        if (itemIDSection != null && itemIDSection.getBoolean(Path.ITEM_REFRESH.getPath(), false)) {
                            ItemStack item = ItemManager.createItem(player, itemID, itemIDSection);
                            List<Integer> slots = itemIDSection.getIntegerList(Path.ITEM_SLOTS.getPath());

                            if (!slots.isEmpty()) slots.forEach(slot -> gui.setItem(slot, item));
                            else gui.setItem(itemIDSection.getInt(Path.ITEM_SLOT.getPath()), item);

                        }
                    }
                }
            }
        }.runTaskTimer(EcoLobby.getInstance(), 0L, 20L);
    }
}
