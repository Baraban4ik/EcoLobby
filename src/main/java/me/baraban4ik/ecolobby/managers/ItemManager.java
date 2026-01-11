package me.baraban4ik.ecolobby.managers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.map.ImageMapRenderer;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.ItemConfig;
import me.baraban4ik.ecolobby.enums.PluginMessage;
import me.baraban4ik.ecolobby.message.MessageFormatter;
import me.baraban4ik.ecolobby.message.PluginMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;

public class ItemManager {

    private static final NamespacedKey ECO_ITEM = new NamespacedKey(EcoLobby.getInstance(), "ECO_ITEM");

    public static ItemStack createItem(Player player, ItemConfig config, String itemID) {
        Material material = config.getMaterial();

        ItemStack item = new ItemStack(material, config.getAmount());
        ItemMeta itemMeta = item.getItemMeta();

        if (config.isHead() || config.isBase64()) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;

            if (config.isHead()) {
                String ownerName = MessageFormatter.format(player, config.getHeadName());
                OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerName);

                skullMeta.setOwningPlayer(owner);
            }
            if (config.isBase64()) {
                setBase64(skullMeta, config.getBase64Value());
            }
        }
        if (config.isCustomMap()) {
            MapMeta mapMeta = (MapMeta) itemMeta;

            MapView mapView = Bukkit.createMap(player.getWorld());
            mapView.getRenderers().forEach(mapView::removeRenderer);
            mapView.addRenderer(new ImageMapRenderer(config.getCustomMapPath()));

            mapMeta.setMapView(mapView);
        }

        itemMeta.setDisplayName(config.getName().toString(player));
        itemMeta.setLore(config.getLore().toList(player));
        itemMeta.setCustomModelData(config.getModelData());

        config.getEnchantment().forEach((e, l) ->
            itemMeta.addEnchant(e, l, true)
        );
        config.getFlags().forEach(itemMeta::addItemFlags);

        itemMeta.getPersistentDataContainer().set(ECO_ITEM, PersistentDataType.STRING, itemID);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static boolean giveItem(CommandSender sender, Player target, String itemID) {
        if (target == null) {
            PluginMessageSender.send(sender, PluginMessage.PLAYER_NOT_FOUND);
            return false;
        }
        ConfigManager configManager = EcoLobby.getConfigManager();

        if (!configManager.getItemsNames().contains(itemID)) {
            PluginMessageSender.send(sender, PluginMessage.ITEM_NOT_FOUND);
            return false;
        }

        ItemConfig itemConfig = configManager.getItemConfig(itemID);
        target.getInventory().addItem(createItem(target, itemConfig, itemID));
        return true;
    }

    public static void giveJoinItems(Player player) {
        ConfigManager configManager = EcoLobby.getConfigManager();

        for (String itemID : configManager.getItemsNames()) {
            ItemConfig itemConfig = configManager.getItemConfig(itemID);
            giveItemToSlot(player, itemConfig, itemID);
        }
    }

    public static boolean isEcoItem(ItemStack item) {
        if (item == null) return false;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.has(ECO_ITEM, PersistentDataType.STRING);
    }

    public static String getItemId(ItemStack item) {
        if (item == null) return null;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(ECO_ITEM, PersistentDataType.STRING);
    }

    private static void giveItemToSlot(Player player, ItemConfig config, String itemID) {
        if (!config.isJoinGive()) return;

        PlayerInventory playerInventory = player.getInventory();
        ItemStack item = createItem(player, config, itemID);

        config.getSlots().forEach(slot ->
                playerInventory.setItem(slot, item)
        );
        config.getEquipmentSlots().forEach(slot ->
                playerInventory.setItem(slot, item));
    }

    private static void setBase64(SkullMeta skullMeta, String base64) {
        try {
            if (isSupportsPlayerProfiles())
                skullMeta.setOwnerProfile(getProfileFromBase64(base64));
            else setLegacyProfile(skullMeta, base64);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PlayerProfile getProfileFromBase64(String base64) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(base64);
        String json = new String(decoded);

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        String url = obj.getAsJsonObject("textures")
                .getAsJsonObject("SKIN")
                .get("url")
                .getAsString();

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        textures.setSkin(URI.create(url).toURL());
        profile.setTextures(textures);

        return profile;
    }

    private static void setLegacyProfile(SkullMeta skullMeta, String base64) throws Exception {
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));

        Field profileField = skullMeta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(skullMeta, profile);
    }

    private static boolean isSupportsPlayerProfiles() {
        return EcoLobby.getInstance().getServerVersion() >= 1.181;
    }

}
