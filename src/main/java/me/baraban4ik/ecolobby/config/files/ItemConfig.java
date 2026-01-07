package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.ItemPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;

import java.util.*;
import java.util.stream.Collectors;

public class ItemConfig extends AbstractConfig {

    private String materialName;

    @Getter private FormattedMessage name;
    @Getter private String headName;
    @Getter private String base64Value;
    @Getter private String customMapPath;

    @Getter private FormattedMessage lore;
    @Getter private List<String> actions;

    @Getter private boolean joinGive;
    @Getter private boolean refresh;
    @Getter private boolean head;
    @Getter private boolean base64;
    @Getter private boolean customMap;

    @Getter private int amount;
    @Getter private int modelData;

    public ItemConfig(ConfigurationSection yaml) {
        initialize(yaml);
    }

    @Override
    protected void loadValues() {
        materialName = getString(ItemPath.MATERIAL, Material.STONE.name());

        head = materialName.startsWith("head-");
        headName = head ? materialName.split("head-")[1] : "";

        base64 = materialName.startsWith("basehead-");
        base64Value =  base64 ? materialName.split("basehead-")[1] : "";

        customMap = materialName.startsWith("map-");
        customMapPath = customMap ? materialName.split("map-")[1] : "";

        name = getFormattedMessage(ItemPath.NAME);
        lore = getFormattedMessageList(ItemPath.LORE);

        joinGive = getBoolean(ItemPath.JOIN_GIVE, true);
        refresh = getBoolean(ItemPath.REFRESH);

        amount = getInt(ItemPath.AMOUNT, 1);;
        modelData = getInt(ItemPath.MODEL_DATA, 0);
        actions = getStringList(ItemPath.ACTIONS);
    }

    public Material getMaterial() {
        if (head || base64)
            return Material.PLAYER_HEAD;
        else if (customMap)
            return Material.FILLED_MAP;

        return getEnum(materialName, Material.class, Material.STONE);
    }

    public List<EquipmentSlot> getEquipmentSlots() {
        List<EquipmentSlot> slots = getStringList(ItemPath.SLOTS).stream()
                .filter(slot -> !isInteger(slot))
                .map(slot -> getEnum(slot, EquipmentSlot.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String slot = getString(ItemPath.SLOT);

        if (slots.isEmpty() && slot != null && !isInteger(slot)) {
            EquipmentSlot equipmentSlot = getEnum(slot, EquipmentSlot.class);

            if (equipmentSlot != null)
                slots.add(equipmentSlot);
        }
        return slots;
    }

    public List<Integer> getSlots() {
        int slot = getInt(ItemPath.SLOT, -1);

        List<Integer> slots = getStringList(ItemPath.SLOTS).stream()
                .filter(this::isInteger)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (slots.isEmpty() && slot != -1) slots.add(slot);

        return slots;
    }

    public Map<Enchantment, Integer> getEnchantment() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        List<String> enchantmentStringList = getStringList(ItemPath.ENCHANTMENTS);

        enchantmentStringList.forEach(e -> {
            String[] enchantSplit = e.split(":");

            Enchantment enchantment = Enchantment.getByName(enchantSplit[0].toUpperCase());
            int level = Integer.parseInt(enchantSplit[1]);

            if (enchantment != null) {
                enchantments.put(enchantment, level);
            }
        });
        return enchantments;
    }

    public List<ItemFlag> getFlags() {
        List<ItemFlag> flags = new ArrayList<>();
        List<String> flagStringList = getStringList(ItemPath.FLAGS);

        flagStringList.forEach(flag -> {
            ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());
            flags.add(itemFlag);
        });

        return flags;
    }

    private boolean isInteger(String str) {
        if (str == null) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
