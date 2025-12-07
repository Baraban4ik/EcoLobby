package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.ItemPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import me.baraban4ik.ecolobby.utils.EnumUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemConfig extends AbstractConfig {

    private String materialName;

    @Getter private FormattedMessage name;
    @Getter private String headName;
    @Getter private String base64Value;

    @Getter private FormattedMessage lore;
    @Getter private List<String> actions;

    @Getter private boolean joinGive;
    @Getter private boolean refresh;
    @Getter private boolean head;
    @Getter private boolean base64;

    @Getter private int amount;
    @Getter private int modelData;

    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

        materialName = getString(ItemPath.MATERIAL, Material.STONE.name());

        head = materialName.startsWith("head-");
        base64 = materialName.startsWith("basehead-");

        name = getFormattedMessage(ItemPath.NAME);
        lore = getFormattedMessageList(ItemPath.LORE);
        headName = head ? materialName.split("head-")[1] : "";
        base64Value =  base64 ? materialName.split("basehead-")[1] : "";
        joinGive = getBoolean(ItemPath.JOIN_GIVE, true);
        refresh = getBoolean(ItemPath.REFRESH);
        amount = getInt(ItemPath.AMOUNT, 1);;
        modelData = getInt(ItemPath.MODEL_DATA, 0);
        actions = getStringList(ItemPath.ACTIONS);

    }

    public Material getMaterial() {
        if (head || base64) {
            return Material.PLAYER_HEAD;
        }

        return EnumUtils.parseEnum(materialName, Material.class, Material.STONE);
    }

    public List<Integer> getSlots() {
        int slot = getInt(ItemPath.SLOT, 0);

        List<Integer> slots = getIntList(ItemPath.SLOTS);
        if (slots.isEmpty()) slots.add(slot);

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
}
