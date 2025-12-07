package me.baraban4ik.ecolobby.gui;


import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.GUIConfig;
import me.baraban4ik.ecolobby.config.files.ItemConfig;
import me.baraban4ik.ecolobby.managers.ActionManager;
import me.baraban4ik.ecolobby.managers.ItemManager;
import org.bukkit.Bukkit;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class GUI {
    private final ConfigManager configManager;
    private final String name;

    private GUIConfig guiConfig;
    private ConfigurationSection items;

    private Inventory gui;
    private BukkitTask refreshTask;

    public boolean isLoaded;

    public GUI(String name) {
        this.configManager = EcoLobby.getConfigManager();
        this.name = name;

        loadData();
    }

    private void loadData() {
        guiConfig = configManager.getGUIConfig(name);
        isLoaded = guiConfig != null;
    }

    public void open(Player player) {
        if (!isLoaded) return;

        String title = guiConfig.getTitle().toString(player);
        int size = guiConfig.getSize();

        items = guiConfig.getItems();
        gui = Bukkit.createInventory(null, size, title);

        fillItems(player, false);

        GUIData.addInventory(gui, this);
        player.openInventory(gui);

        refresh(player);
    }

    public void close() {
        if (refreshTask != null) refreshTask.cancel();
    }

    public void update(Player player) {
        loadData();
        open(player);
    }

    private void fillItems(Player player, boolean refreshOnly) {
        if (items == null) return;

        for (String itemID : items.getKeys(false)) {
            ConfigurationSection itemIDSection = items.getConfigurationSection(itemID);
            if (itemIDSection == null) continue;

            ItemConfig itemConfig = configManager.getItemConfig(itemIDSection);
            if (refreshOnly && !itemConfig.isRefresh()) continue;

            ItemStack item = ItemManager.createItem(player, itemConfig, itemID);

            List<Integer> slots = itemConfig.getSlots();
            slots.forEach(s -> gui.setItem(s, item));
        }
    }

    public void click(Player player, ItemStack item) {
        String itemID = ItemManager.getItemId(item);
        if (itemID == null) return;

        ConfigurationSection itemIDSection = items.getConfigurationSection(itemID);

        if (itemIDSection != null) {
            ItemConfig config = configManager.getItemConfig(itemIDSection);
            List<String> actions = config.getActions();

            ActionManager.execute(player, actions);
        }
    }

    private void refresh(Player player) {
        refreshTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (gui == null || !player.getOpenInventory().getTopInventory().equals(gui)) {
                    cancel();
                    return;
                }
                fillItems(player, true);

            }
        }.runTaskTimer(EcoLobby.getInstance(), 0L, 20L);
    }
}