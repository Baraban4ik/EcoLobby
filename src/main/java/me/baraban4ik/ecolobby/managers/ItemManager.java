package me.baraban4ik.ecolobby.managers;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.enums.ItemsPath;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.baraban4ik.ecolobby.EcoLobby.*;

public class ItemManager {
    private static ItemStack createItem(@NotNull Player player, @NotNull String itemName)  {
        ConfigurationSection itemSection = itemsConfig.getConfigurationSection( "Items." + itemName);

        String material = itemSection.getString(ItemsPath.MATERIAL.getPath(), "STONE");
        Material itemMaterial = Material.getMaterial(material.toUpperCase());

        boolean isHead = false;
        boolean isBaseHead = false;

        int amount = itemSection.getInt(ItemsPath.AMOUNT.getPath());

        String displayName = Format.format(itemSection.getString(ItemsPath.NAME.getPath()), player);
        List<String> formatLore = itemSection.getStringList(ItemsPath.LORE.getPath()).stream()
                .map(line -> Format.format(line, player))
                .collect(Collectors.toList());

        if (material.startsWith("head-")) {
            itemMaterial = Material.PLAYER_HEAD;
            isHead = true;
        }
        else if (material.startsWith("basehead-")) {
            itemMaterial = Material.PLAYER_HEAD;
            isBaseHead = true;
        }

        if (itemMaterial != null) {
            ItemStack item = new ItemStack(itemMaterial, amount);
            ItemMeta itemMeta = item.getItemMeta();

            if (isHead) {
                String owner = material.replace("head-", "");
                owner = Format.format(owner, player);

                SkullMeta skullMeta = (SkullMeta) itemMeta;
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            }
            if (isBaseHead) {
                String base64 = material.replace("basehead-", "");
                SkullMeta skullMeta = (SkullMeta) itemMeta;

                GameProfile profile = new GameProfile(UUID.randomUUID(), "");
                profile.getProperties().put("textures", new Property("textures", base64));

                try {
                    Field profileField = skullMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(skullMeta, profile);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            itemMeta.setDisplayName(displayName);
            itemMeta.setLore(formatLore);

            NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");
            itemMeta.getPersistentDataContainer().set(ECO_ITEM, PersistentDataType.STRING, itemName);

            item.setItemMeta(itemMeta);

            return item;
        }
        return null;
    }

    public static boolean isEcoItem(ItemStack item, String itemName) {

        NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

        if (container.has(ECO_ITEM, PersistentDataType.STRING))
            return Objects.equals(container.get(ECO_ITEM, PersistentDataType.STRING), itemName);

        return false;
    }

    public static void setItems(Player player) {
        ConfigurationSection items = itemsConfig.getConfigurationSection(ItemsPath.ITEMS.getPath());
        if (items == null) return;

        for (String itemName : items.getKeys(false)) {
            int slot = items.getInt(itemName + ".slot");

            player.getInventory().setItem(slot, createItem(player, itemName));
        }
    }
    public static void giveItem(Player player, String itemName) {
        ConfigurationSection items = itemsConfig.getConfigurationSection(ItemsPath.ITEMS.getPath());
        ItemStack item = createItem(player, itemName);

        if (item == null || items == null) {
            Chat.sendMessage(MESSAGES.ITEM_NOT_FOUND, player);
            return;
        }
        player.getInventory().addItem(item);
    }
}

