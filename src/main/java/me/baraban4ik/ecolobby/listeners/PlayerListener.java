package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.config;

public class PlayerListener implements Listener {
    @EventHandler
    public void setPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        double maxHealth = config.getDouble("Player.health", 20);
        List<String> effects = config.getStringList("Player.effects");

        player.setGameMode(GameMode.valueOf(config.getString("Player.gamemode", "SURVIVAL").toUpperCase()));

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(maxHealth);

        player.setFoodLevel(config.getInt("Player.food_level"));
        player.setLevel(config.getInt("Player.level_exp"));

        for (PotionEffect activeEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activeEffect.getType());
        }

        for (String effect : effects) {
            String[] effectSplit = effect.split(":");

            PotionEffectType type = PotionEffectType.getByName(effectSplit[0].toUpperCase());
            if (type == null) continue;
            int level = Integer.parseInt(effectSplit[1]);
            int time = Integer.parseInt(effectSplit[2]) * 20;

            boolean particles = Boolean.parseBoolean(effectSplit[3]);
            int duration = (time == 0) ? Integer.MAX_VALUE : time;

            player.addPotionEffect(new PotionEffect(type, duration, level-1, false, particles));
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setAllowFlight(config.getBoolean("Player.abilities.enable_fly"));
        }


    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.chat")) return;

        if (config.getBoolean("Player.abilities.disable_chat")) {
            Chat.sendPathMessage("deny_chat_message", player);
            event.setCancelled(true);
        }
    }
    @EventHandler()
    public void onCommands(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.commands")) return;

        if (config.getBoolean("Player.abilities.disable_commands.enabled")) {
            String[] command = event.getMessage().replace("/", "").split(" ");

            if (!config.getStringList("Player.abilities.disable_commands.allowed").contains(command[0])) {
                Chat.sendPathMessage("deny_commands_message", player);
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        event.setCancelled(config.getBoolean("Player.abilities.disable_damage"));
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        event.setCancelled(config.getBoolean("Player.abilities.disable_hunger"));
    }
    @EventHandler
    public void onMovements (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.movements")) return;

        if (config.getBoolean("Player.abilities.disable_movements")) {
            Location from = event.getFrom().clone();
            Location to = event.getTo();

            from.setYaw(to.getYaw());
            from.setPitch(to.getPitch());

            if (!from.equals(to)) event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.break")) return;

        if (config.getBoolean("Player.abilities.blocks.disable_break")) {

            if (config.getBoolean("Player.abilities.blocks.use_particle_effect"))
                player.playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);
            if (config.getBoolean("Player.abilities.blocks.use_deny_messages"))
                Chat.sendPathMessage("deny_break_blocks_message", player);

            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.place")) return;

        if (config.getBoolean("Player.abilities.blocks.disable_place")) {

            if (config.getBoolean("Player.abilities.blocks.use_particle_effect"))
                player.playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);
            if (config.getBoolean("Player.abilities.blocks.use_deny_messages"))
                Chat.sendPathMessage("deny_place_blocks_message", player);

            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlocksInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.interact")) return;

        if (config.getBoolean("Player.abilities.blocks.disable_interact")) {
            Block block = event.getClickedBlock();
            if (block == null) return;

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!block.getType().isInteractable()) return;

                if (config.getBoolean("Player.abilities.blocks.use_particle_effect"))
                    player.playEffect(event.getClickedBlock().getLocation(), Effect.SMOKE, BlockFace.UP);
                if (config.getBoolean("Player.abilities.blocks.use_deny_messages"))
                    Chat.sendPathMessage("deny_interact_blocks_message", player);

                event.setCancelled(true);
            }
            else if (event.getAction() == Action.PHYSICAL) {
                if (block.getType().equals(Material.FARMLAND))
                    event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onMoveItems(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission("ecolobby.bypass.items.move")) return;
        event.setCancelled(config.getBoolean("Player.abilities.items.disable_move"));
    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent event) {
        if (event.getPlayer().hasPermission("ecolobby.bypass.items.drop")) return;
        event.setCancelled(config.getBoolean("Player.abilities.items.disable_drop"));
    }

    @EventHandler
    public void onPickupItems(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (player.hasPermission("ecolobby.bypass.items.pickup")) return;

        event.setCancelled(config.getBoolean("Player.abilities.items.disable_pickup"));
    }
}


