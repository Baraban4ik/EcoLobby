package me.baraban4ik.ecolobby.managers;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.MESSAGES;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Chat;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static me.baraban4ik.ecolobby.EcoLobby.*;

public class ItemManager {

    private static ConfigurationSection itemSection;
    private static String materialSection;
    private static boolean isHead = false;
    private static boolean isBaseHead = false;

    private static ItemStack createItem(@NotNull Player player, @NotNull String itemName)  {
        itemSection = itemsConfig.getConfigurationSection( "Items." + itemName);

        if (itemSection == null)
            return new ItemStack(Material.STONE);

        int amount = itemSection.getInt(Path.ITEM_AMOUNT.getPath());
        materialSection = itemSection.getString(Path.ITEM_MATERIAL.getPath(), "STONE");

        String displayName = Format.format(itemSection.getString(Path.ITEM_NAME.getPath(), ""), player);
        List<String> formatLore = itemSection.getStringList(Path.ITEM_LORE.getPath()).stream()
                .map(line -> Format.format(line, player))
                .collect(Collectors.toList());

        List<String> enchantments = itemSection.getStringList(Path.ITEM_ENCHANTS.getPath());
        List<String> flags = itemSection.getStringList(Path.ITEM_FLAGS.getPath());

        isHead = false;
        isBaseHead = false;

        Material material = getMaterial();

        if (material != null) {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta itemMeta = item.getItemMeta();

            if (isHead) {
                String owner = materialSection.replace("head-", "");
                owner = Format.format(owner, player);

                SkullMeta skullMeta = (SkullMeta) itemMeta;
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            }
            if (isBaseHead) {
                String base64 = materialSection.replace("basehead-", "");
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

            enchantments.forEach(e -> {
                String[] enchantSplit = e.split(":");

                Enchantment enchantment = Enchantment.getByName(enchantSplit[0].toUpperCase());
                int level = Integer.parseInt(enchantSplit[1]);

                if (enchantment != null) {
                    itemMeta.addEnchant(enchantment, level, true);
                }
            });

            flags.forEach(flag -> {
                ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());
                itemMeta.addItemFlags(itemFlag);
            });

            itemMeta.setDisplayName(displayName);
            if (!formatLore.isEmpty()) {
                itemMeta.setLore(formatLore);
            }

            NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");
            itemMeta.getPersistentDataContainer().set(ECO_ITEM, PersistentDataType.STRING, itemName);

            item.setItemMeta(itemMeta);

            return item;
        }
        return null;
    }

    private static Material getMaterial() {
        Material material = Material.getMaterial(materialSection.toUpperCase());

        if (materialSection.startsWith("head-")) {
            material = Material.PLAYER_HEAD;
            isHead = true;
        }
        else if (materialSection.startsWith("basehead-")) {
            material = Material.PLAYER_HEAD;
            isBaseHead = true;
        }
        return material;
    }

    public static boolean isEcoItem(ItemStack item, String itemName) {

        NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

        if (container.has(ECO_ITEM, PersistentDataType.STRING))
            return Objects.equals(container.get(ECO_ITEM, PersistentDataType.STRING), itemName);

        return false;
    }

    public static void setItems(Player player) {
        ConfigurationSection items = itemsConfig.getConfigurationSection(Path.ITEMS.getPath());
        if (items == null) return;

        for (String itemName : items.getKeys(false)) {
            int slot = items.getInt(itemName + ".slot");

            player.getInventory().setItem(slot, createItem(player, itemName));
        }
    }
    public static void giveItem(Player player, String itemName) {
        ConfigurationSection items = itemsConfig.getConfigurationSection(Path.ITEMS.getPath());
        ItemStack item = createItem(player, itemName);

        if (item == null || items == null) {
            Chat.sendMessage(MESSAGES.ITEM_NOT_FOUND(), player);
            return;
        }
        player.getInventory().addItem(item);
    }
}

