package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.gui.GUIData;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.*;
import static me.baraban4ik.ecolobby.utils.Configurations.config;

public class PlayerListener implements Listener {


    @EventHandler
    public void updatePlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        double maxHealth = config.getDouble(Path.PLAYER_HEALTH.getPath(), 20);
        List<String> effects = config.getStringList(Path.PLAYER_EFFECTS.getPath());

        GameMode gameMode = GameMode.valueOf(config.getString(Path.PLAYER_GAMEMODE.getPath(), "SURVIVAL").toUpperCase());

        player.setGameMode(gameMode);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(maxHealth);

        player.setFoodLevel(config.getInt(Path.PLAYER_FOOD_LEVEL.getPath()));
        player.setLevel(config.getInt(Path.PLAYER_LEVEL_EXP.getPath()));

        player.getActivePotionEffects().forEach(
                activeEffect -> player.removePotionEffect(activeEffect.getType())
        );


        for (String effect : effects) {
            String[] effectSplit = effect.split(":");

            PotionEffectType type = PotionEffectType.getByName(effectSplit[0].toUpperCase());
            if (type == null) continue;

            int level = Integer.parseInt(effectSplit[1]);
            int time = Integer.parseInt(effectSplit[2]) * 20;

            boolean particles = Boolean.parseBoolean(effectSplit[3]);

            int duration = (time == 0) ? -1 : time;

            if (getInstance().getServerVersion() < 1.194) {
                duration = (time == 0) ? Integer.MAX_VALUE : time;
            }

            player.addPotionEffect(new PotionEffect(type, duration, level-1, false, particles));
        }
        Bukkit.getOnlinePlayers().forEach(
                onlinePlayer -> onlinePlayer.setAllowFlight(config.getBoolean(Path.ABILITIES_FLY.getPath()))
        );
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.chat")) return;

        if (config.getBoolean(Path.ABILITIES_CHAT.getPath())) {
            Chat.sendPathMessage(Path.DENY_CHAT, player);
            event.setCancelled(true);
        }
    }
    @EventHandler()
    public void onCommands(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.commands")) return;

        if (config.getBoolean(Path.ABILITIES_COMMANDS.getPath())) {
            String[] command = event.getMessage().replace("/", "").split(" ");
            List<String> allowedCommands = config.getStringList(Path.ABILITIES_COMMANDS_ALLOWED.getPath());

            if (!allowedCommands.contains(command[0])) {
                Chat.sendPathMessage(Path.DENY_COMMANDS, player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerCommandTab(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.commands")) return;

        if (config.getBoolean(Path.ABILITIES_COMMANDS.getPath())) {
            List<String> allowedCommands = config.getStringList(Path.ABILITIES_COMMANDS_ALLOWED.getPath());

            if (!config.getBoolean(Path.ABILITIES_COMMANDS_TAB_COMPLETE.getPath())) {
                event.getCommands().removeIf(command -> !allowedCommands.contains(command));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(config.getBoolean(Path.ABILITIES_DAMAGE.getPath()));
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(config.getBoolean(Path.ABILITIES_HUNGER.getPath()));
    }
    @EventHandler
    public void onMovements(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.movements")) return;

        if (config.getBoolean(Path.ABILITIES_MOVEMENTS.getPath())) {
            Location from = event.getFrom().clone();
            Location to = event.getTo();

            from.setYaw(to.getYaw());
            from.setPitch(to.getPitch());

            if (!from.equals(to)) event.setCancelled(true);
        }
    }


    private void spawnParticleEffect(Player player, Location location) {
        System.out.println(location);
        if (config.getBoolean(Path.ABILITIES_BLOCKS_PARTICLE_EFFECT.getPath())) player.playEffect(location, Effect.SMOKE, BlockFace.UP);
    }
    private void sendDenyMessage(Player player, Path messagePath) {
        if (config.getBoolean(Path.ABILITIES_BLOCKS_DENY_MESSAGE.getPath()))
            Chat.sendPathMessage(messagePath, player);
    }
    private void playDenySound(Player player, Path path, Path soundPath) {
        if (config.getBoolean(path.getPath())) {
            Sound sound = Sound.valueOf(config.getString(soundPath.getPath()));
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.break")) return;

        if (config.getBoolean(Path.ABILITIES_BLOCKS_BREAK.getPath())) {
            spawnParticleEffect(player, event.getBlock().getLocation());
            sendDenyMessage(player, Path.DENY_BREAK_BLOCKS);

            playDenySound(player, Path.ABILITIES_BLOCKS_DENY_SOUND, Path.ABILITIES_ITEMS_DENY_SOUND_SOUND);

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.getPlayer() != null) cancelPlace(event.getPlayer(), event.getBlock().getLocation(), event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        cancelPlace(event.getPlayer(), event.getBlock().getLocation(), event);
    }

    private void cancelPlace(Player player, Location blockLocation, Cancellable event) {
        if (player.hasPermission("ecolobby.bypass.blocks.place")) return;

        if (config.getBoolean(Path.ABILITIES_BLOCKS_PLACE.getPath())) {
            spawnParticleEffect(player, blockLocation);
            sendDenyMessage(player, Path.DENY_PLACE_BLOCKS);

            playDenySound(player, Path.ABILITIES_BLOCKS_DENY_SOUND, Path.ABILITIES_BLOCKS_DENY_SOUND_SOUND);

            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlocksInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("ecolobby.bypass.blocks.interact")) return;
        if (!config.getBoolean(Path.ABILITIES_BLOCKS_INTERACT.getPath())) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        boolean isRightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK;
        boolean isPhysical = event.getAction() == Action.PHYSICAL;

        if (isRightClick && block.getType().isInteractable() || (isPhysical && block.getType() == Material.FARMLAND)) {
            spawnParticleEffect(player, block.getLocation());
            sendDenyMessage(player, Path.DENY_INTERACT_BLOCKS);

            playDenySound(player, Path.ABILITIES_BLOCKS_DENY_SOUND, Path.ABILITIES_BLOCKS_DENY_SOUND_SOUND);

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItems(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasPermission("ecolobby.bypass.items.move")) return;

            if (config.getBoolean(Path.ABILITIES_ITEMS_MOVE.getPath()) && !GUIData.containsInventory(event.getInventory())) {
                playDenySound(player, Path.ABILITIES_ITEMS_DENY_SOUND, Path.ABILITIES_BLOCKS_DENY_SOUND_SOUND);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.items.drop")) return;

        if (config.getBoolean(Path.ABILITIES_ITEMS_DROP.getPath())) {
            playDenySound(player, Path.ABILITIES_ITEMS_DENY_SOUND, Path.ABILITIES_BLOCKS_DENY_SOUND_SOUND);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItems(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.hasPermission("ecolobby.bypass.items.pickup")) return;

            if (config.getBoolean(Path.ABILITIES_ITEMS_PICKUP.getPath())) {
                playDenySound(player, Path.ABILITIES_ITEMS_DENY_SOUND, Path.ABILITIES_BLOCKS_DENY_SOUND_SOUND);
                event.setCancelled(true);
            }
        }
    }
}


